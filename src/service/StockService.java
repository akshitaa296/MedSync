package service;

import comparators.BatchExpiryComparator;
import enums.MedicineType;
import exceptions.*;
import io.FileLogger;
import model.*;

import java.time.LocalDate;
import java.util.*;

public class StockService {
    private Map<Integer, Medicine> medicines = new HashMap<>();
    private Map<Integer, TreeSet<Batch>> batchesByMed = new HashMap<>();
    private int nextId = 101;

    // Medicine management
    public Medicine addMedicine(MedicineType type, String name, double price,
                                int minStock, boolean rx) {
        int id = nextId++;
        Medicine m;

        switch (type) {
            case TABLET:
                m = new Tablet(id, name, price, minStock, rx);
                break;
            case SYRUP:
                m = new Syrup(id, name, price, minStock, rx);
                break;
            default:
                m = new Injection(id, name, price, minStock, rx);
        }

        medicines.put(id, m);
        batchesByMed.put(id, new TreeSet<>(new BatchExpiryComparator()));
        FileLogger.log("Medicine added: " + name + " (ID " + id + ")");
        return m;
    }

    public Medicine findById(int id) throws MedicineNotFoundException {
        Medicine m = medicines.get(id);

        if (m == null) {
            throw new MedicineNotFoundException("No medicine found with ID " + id);
        }

        return m;
    }

    public List<Medicine> searchByName(String text) {
        List<Medicine> result = new ArrayList<>();

        for (Medicine m : medicines.values()) {
            if (m.getName().toLowerCase().contains(text.toLowerCase())) {
                result.add(m);
            }
        }

        Collections.sort(result);
        return result;
    }

    public List<Medicine> getAllMedicines() {
        List<Medicine> list = new ArrayList<>(medicines.values());
        Collections.sort(list);
        return list;
    }

    public void deleteMedicine(int id) throws MedicineNotFoundException {
        Medicine m = findById(id);
        medicines.remove(id);
        batchesByMed.remove(id);
        FileLogger.log("Medicine deleted: " + m.getName());
    }

    // Batch management
    public void addBatch(int medId, String batchNo, LocalDate expiry, int qty)
            throws MedicineNotFoundException, ExpiredBatchException, InvalidInputException {

        findById(medId);

        if (qty <= 0) {
            throw new InvalidInputException("Quantity must be greater than 0.");
        }

        if (batchNo == null || batchNo.isBlank()) {
            throw new InvalidInputException("Batch number cannot be empty.");
        }

        if (expiry.isBefore(LocalDate.now())) {
            throw new ExpiredBatchException("Batch is already expired (" + expiry + "). Cannot add.");
        }

        TreeSet<Batch> batches = batchesByMed.get(medId);

        for (Batch existing : batches) {
            if (existing.getBatchNo().equalsIgnoreCase(batchNo)) {
                throw new InvalidInputException("This batch number already exists for this medicine.");
            }
        }

        Batch b = new Batch(medId, batchNo, expiry, qty);

        batches.add(b);
        FileLogger.log("Batch added: " + batchNo + " for medicine ID " + medId + ", qty " + qty);
    }

    public Set<Batch> getBatches(int medId) throws MedicineNotFoundException {
        findById(medId);
        return Collections.unmodifiableSet(batchesByMed.get(medId));
    }

    // Sellable stock excludes expired batches
    public int getSellableStock(int medId) throws MedicineNotFoundException {
        findById(medId);
        int total = 0;

        for (Batch b : batchesByMed.get(medId)) {
            if (!b.isExpired()) total += b.getQuantity();
        }

        return total;
    }

    // FEFO dispensing
    public List<String> dispense(int medId, int qty)
            throws OutOfStockException, MedicineNotFoundException, InvalidInputException {

        findById(medId);

        if (qty <= 0) {
            throw new InvalidInputException("Dispensing quantity must be greater than 0.");
        }

        int available = getSellableStock(medId);

        if (available < qty) {
            throw new OutOfStockException("Only " + available + " units available, requested " + qty);
        }

        List<String> used = new ArrayList<>();
        int remaining = qty;

        for (Batch b : batchesByMed.get(medId)) {
            if (remaining == 0) break;
            if (b.isExpired() || b.getQuantity() == 0) continue;

            int take = Math.min(b.getQuantity(), remaining);
            b.setQuantity(b.getQuantity() - take);
            remaining -= take;

            used.add(take + " from Batch " + b.getBatchNo()
                    + " (exp " + b.getExpiryDate() + ")");
        }

        FileLogger.log("Dispensed " + qty + " units of medicine ID " + medId);
        return used;
    }

    // Reports
    public List<Medicine> getLowStockMedicines() throws MedicineNotFoundException {
        List<Medicine> low = new ArrayList<>();

        for (Medicine m : getAllMedicines()) {
            if (getSellableStock(m.getId()) < m.getMinStock()) {
                low.add(m);
            }
        }

        return low;
    }

    public List<String> getExpiringBatchLines(int days) {
        List<String> lines = new ArrayList<>();

        for (Medicine m : getAllMedicines()) {
            for (Batch b : batchesByMed.get(m.getId())) {
                if (b.isExpiringSoon(days) && b.getQuantity() > 0) {
                    lines.add(m.getName() + " -> " + b);
                }
            }
        }

        return lines;
    }

    public List<String> getExpiredBatchLines() {
        List<String> lines = new ArrayList<>();

        for (Medicine m : getAllMedicines()) {
            for (Batch b : batchesByMed.get(m.getId())) {
                if (b.isExpired()) {
                    lines.add(m.getName() + " -> " + b);
                }
            }
        }

        return lines;
    }
}
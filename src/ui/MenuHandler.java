package ui;

import enums.MedicineType;
import exceptions.*;
import io.FileLogger;
import model.Batch;
import model.Medicine;
import service.BillingService;
import service.DispenseService;
import service.StockService;
import util.InputValidator;

import java.time.LocalDate;
import java.util.List;

public class MenuHandler {
    private final StockService stock;
    private final BillingService billing = new BillingService();
    private final DispenseService dispenseService;

    public MenuHandler(StockService stock) {
        this.stock = stock;
        this.dispenseService = new DispenseService(stock, billing);
    }

    public void start() {
        FileLogger.log("Application started");

        while (true) {
            printMainMenu();
            int choice = InputValidator.readInt("Enter choice: ");

            switch (choice) {
                case 1: medicineMenu(); break;
                case 2: batchMenu(); break;
                case 3: dispenseMenu(); break;
                case 4: reportsMenu(); break;
                case 5: case 6: case 7: case 8:
                    System.out.println("\n[INFO] This feature is Coming Soon.");
                    break;
                case 9:
                    FileLogger.log("Application closed");
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("[ERROR] Invalid choice. Enter 1-9.");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n==============================================");
        System.out.println("       MedSync - Hospital Pharmacy");
        System.out.println("==============================================");
        System.out.println(" 1. Medicine Management");
        System.out.println(" 2. Batch & Stock");
        System.out.println(" 3. Dispense Medicine & Billing");
        System.out.println(" 4. Reports");
        System.out.println(" 5. Supplier Management      [Coming Soon]");
        System.out.println(" 6. Purchase Orders          [Coming Soon]");
        System.out.println(" 7. Database (MySQL/JDBC)   [Coming Soon]");
        System.out.println(" 8. CSV Export / Import     [Coming Soon]");
        System.out.println(" 9. Exit");
        System.out.println("----------------------------------------------");
    }

    // Medicine management
    private void medicineMenu() {
        while (true) {
            System.out.println("\n--- Medicine Management ---");
            System.out.println("1. Add Medicine  2. View All  3. Search  4. Delete  0. Back");

            int c = InputValidator.readInt("Choice: ");

            if (c == 0) return;

            switch (c) {
                case 1: addMedicine(); break;
                case 2: viewAllMedicines(); break;
                case 3: searchMedicine(); break;
                case 4: deleteMedicine(); break;
                default: System.out.println("[ERROR] Invalid choice.");
            }
        }
    }

    private void addMedicine() {
        System.out.println("Type: 1. Tablet  2. Syrup  3. Injection");

        int t = InputValidator.readIntInRange("Choose type (1-3): ", 1, 3);
        MedicineType type = MedicineType.values()[t - 1];

        String name = InputValidator.readNonEmpty("Medicine name: ");
        double price = InputValidator.readPositiveDouble("Price per unit (Rs): ");
        int min = InputValidator.readPositiveInt("Minimum stock level: ");
        boolean rx = InputValidator.readYesNo("Prescription required? (y/n): ");

        Medicine m = stock.addMedicine(type, name, price, min, rx);

        System.out.println("[OK] Added with ID " + m.getId()
                + ". Storage: " + m.getStorageInfo());
    }

    private void viewAllMedicines() {
        List<Medicine> list = stock.getAllMedicines();

        if (list.isEmpty()) {
            System.out.println("[INFO] No medicines yet.");
            return;
        }

        for (Medicine m : list) {
            System.out.println(m);
        }
    }

    private void searchMedicine() {
        String text = InputValidator.readNonEmpty("Enter name (or part of it): ");
        List<Medicine> result = stock.searchByName(text);

        if (result.isEmpty()) {
            System.out.println("[INFO] No match found.");
        }

        for (Medicine m : result) {
            System.out.println(m);
        }
    }

    private void deleteMedicine() {
        int id = InputValidator.readInt("Medicine ID to delete: ");

        try {
            stock.deleteMedicine(id);
            System.out.println("[OK] Deleted.");
        } catch (MedicineNotFoundException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // Batch management
    private void batchMenu() {
        while (true) {
            System.out.println("\n--- Batch & Stock ---");
            System.out.println("1. Add Batch  2. View Stock  0. Back");

            int c = InputValidator.readInt("Choice: ");

            if (c == 0) return;

            switch (c) {
                case 1: addBatch(); break;
                case 2: viewStock(); break;
                default: System.out.println("[ERROR] Invalid choice.");
            }
        }
    }

    private void addBatch() {
        int medId = InputValidator.readInt("Medicine ID: ");
        String batchNo = InputValidator.readNonEmpty("Batch number: ");
        LocalDate expiry = InputValidator.readDate("Expiry date (yyyy-MM-dd): ");
        int qty = InputValidator.readPositiveInt("Quantity: ");

        try {
            stock.addBatch(medId, batchNo, expiry, qty);
            System.out.println("[OK] Batch added.");
        } catch (MedicineNotFoundException | ExpiredBatchException
                 | InvalidInputException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private void viewStock() {
        for (Medicine m : stock.getAllMedicines()) {
            try {
                System.out.println("\n" + m.getName() + " (ID " + m.getId()
                        + ") | Sellable stock: "
                        + stock.getSellableStock(m.getId()));

                for (Batch b : stock.getBatches(m.getId())) {
                    String tag = b.isExpired()
                            ? " [EXPIRED]"
                            : (b.isExpiringSoon(30) ? " [EXPIRING SOON]" : "");

                    System.out.println("   " + b + tag);
                }

            } catch (MedicineNotFoundException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }

    // Dispensing
    private void dispenseMenu() {
        System.out.println("\n--- Dispense Medicine ---");

        while (true) {
            int id = InputValidator.readInt("Medicine ID (0 = finish and print bill): ");

            if (id == 0) break;

            try {
                Medicine m = stock.findById(id);
                int qty = InputValidator.readPositiveInt("Quantity: ");

                String rx = null;

                if (m.isRequiresPrescription()) {
                    rx = InputValidator.readLine("Prescription ID: ");
                }

                dispenseService.addToCart(m, qty, rx);
                System.out.println("[OK] Added to bill.");

            } catch (MedicineNotFoundException | OutOfStockException
                     | PrescriptionRequiredException | InvalidInputException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        String receipt = billing.generateReceipt();

        if (receipt == null) {
            System.out.println("[INFO] Nothing was added. No bill generated.");
        } else {
            System.out.println(receipt);
        }
    }

    // Reports
    private void reportsMenu() {
        while (true) {
            System.out.println("\n--- Reports ---");
            System.out.println("1. Low Stock  2. Expiring in 30 days  3. Expired Batches  0. Back");

            int c = InputValidator.readInt("Choice: ");

            if (c == 0) return;

            switch (c) {
                case 1:
                    try {
                        List<Medicine> low = stock.getLowStockMedicines();

                        if (low.isEmpty()) {
                            System.out.println("[INFO] No low stock medicines.");
                        }

                        for (Medicine m : low) {
                            System.out.println(m.getName() + " | Stock: "
                                    + stock.getSellableStock(m.getId())
                                    + " | Minimum: " + m.getMinStock());
                        }

                    } catch (MedicineNotFoundException e) {
                        System.out.println("[ERROR] " + e.getMessage());
                    }
                    break;

                case 2:
                    printLines(stock.getExpiringBatchLines(30),
                            "No batches expiring in 30 days.");
                    break;

                case 3:
                    printLines(stock.getExpiredBatchLines(),
                            "No expired batches.");
                    break;

                default:
                    System.out.println("[ERROR] Invalid choice.");
            }
        }
    }

    private void printLines(List<String> lines, String emptyMsg) {
        if (lines.isEmpty()) {
            System.out.println("[INFO] " + emptyMsg);
        }

        for (String s : lines) {
            System.out.println(s);
        }
    }
}
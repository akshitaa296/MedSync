package model;

import java.util.List;
import java.util.ArrayList;

public class InventoryReport {
    private int totalMedicines;
    private int totalStock;
    private int lowStockCount;
    private List<String> expiringBatches;
    private List<String> expiredBatches;

    public InventoryReport(int totalMedicines, int totalStock,
                           int lowStockCount,
                           List<String> expiringBatches,
                           List<String> expiredBatches) {

        this.totalMedicines = totalMedicines;
        this.totalStock = totalStock;
        this.lowStockCount = lowStockCount;
        this.expiringBatches = new ArrayList<>(expiringBatches);
        this.expiredBatches = new ArrayList<>(expiredBatches);
    }

    public int getTotalMedicines() {
        return totalMedicines;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public int getLowStockCount() {
        return lowStockCount;
    }

    public List<String> getExpiringBatches() {
        return new ArrayList<>(expiringBatches);
    }

    public List<String> getExpiredBatches() {
        return new ArrayList<>(expiredBatches);
    }

    @Override
    public String toString() {
        return "Total Medicines: " + totalMedicines
                + "\nTotal Stock: " + totalStock
                + "\nLow Stock Medicines: " + lowStockCount
                + "\nExpiring Batches: " + expiringBatches.size()
                + "\nExpired Batches: " + expiredBatches.size();
    }
}
package service;

import model.Medicine;
import model.InventoryReport;
import exceptions.MedicineNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class InventoryReportService {

    private StockService stockService;

    public InventoryReportService(StockService stockService) {
        this.stockService = stockService;
    }

    public InventoryReport generateReport()
            throws MedicineNotFoundException {

        List<Medicine> medicines =
                stockService.getAllMedicines();

        int totalStock = 0;

        for (Medicine medicine : medicines) {
            totalStock += stockService.getSellableStock(
                    medicine.getId());
        }

        int lowStockCount =
                stockService.getLowStockMedicines().size();

        List<String> expiring =
                new ArrayList<>(
                        stockService.getExpiringBatchLines(30));

        List<String> expired =
                new ArrayList<>(
                        stockService.getExpiredBatchLines());

        return new InventoryReport(
                medicines.size(),
                totalStock,
                lowStockCount,
                expiring,
                expired
        );
    }

    public void displayReport() {
        try {
            InventoryReport report = generateReport();

            System.out.println("========== INVENTORY REPORT ==========");
            System.out.println(report);
            System.out.println("======================================");

        } catch (MedicineNotFoundException e) {
            System.out.println("Error generating report: "
                    + e.getMessage());
        }
    }
}
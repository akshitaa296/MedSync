package service;

import model.Sale;
import java.util.List;

public class SalesReportService {
    private SalesHistoryService historyService;

    public SalesReportService(SalesHistoryService historyService) {
        this.historyService = historyService;
    }

    public double calculateTotalRevenue() {
        double revenue = 0;

        List<Sale> sales = historyService.getAllSales();

        for (Sale sale : sales) {
            revenue += sale.getTotal();
        }

        return revenue;
    }

    public double calculateTotalGST() {
        double totalGST = 0;

        for (Sale sale : historyService.getAllSales()) {
            totalGST += sale.getGst();
        }

        return totalGST;
    }

    public void displaySalesReport() {
        System.out.println("========== SALES REPORT ==========");
        System.out.println("Total Transactions: "
                + historyService.getTotalSales());

        System.out.printf("Total Revenue: Rs. %.2f%n",
                calculateTotalRevenue());

        System.out.printf("Total GST: Rs. %.2f%n",
                calculateTotalGST());

        System.out.println("==================================");
    }
}
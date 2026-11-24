package service;

import io.FileLogger;
import model.SaleItem;
import java.util.LinkedList;

public class BillingService {
    public static final double GST_RATE = 0.12;

    private LinkedList<SaleItem> cart = new LinkedList<>();
    private int billCounter = 1;

    public void addItem(SaleItem item) {
        cart.add(item);
    }

    public String generateReceipt() {
        if (cart.isEmpty()) return null;

        StringBuilder sb = new StringBuilder();
        double subtotal = 0;

        sb.append("\n=========== RECEIPT #").append(billCounter).append(" ===========\n");
        sb.append(String.format("%-20s %4s %10s%n", "Item", "Qty", "Amount"));

        for (SaleItem item : cart) {
            sb.append(String.format("%-20s %4d %10.2f%n",
                    item.getMedicineName(), item.getQuantity(), item.getAmount()));
            subtotal += item.getAmount();
        }

        double gst = subtotal * GST_RATE;
        double total = subtotal + gst;

        sb.append("----------------------------------------\n");
        sb.append(String.format("%-25s %10.2f%n", "Subtotal", subtotal));
        sb.append(String.format("%-25s %10.2f%n", "GST (12%)", gst));
        sb.append(String.format("%-25s %10.2f%n", "TOTAL", total));
        sb.append("========================================\n");

        FileLogger.log("Bill #" + billCounter + " generated, total Rs."
                + String.format("%.2f", total));

        cart.clear();
        billCounter++;

        return sb.toString();
    }
}
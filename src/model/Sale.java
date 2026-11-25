package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private int saleId;
    private LocalDateTime saleDate;
    private List<SaleItem> items;
    private double subtotal;
    private double gst;
    private double total;

    public Sale(int saleId, List<SaleItem> items, double gstRate) {
        this.saleId = saleId;
        this.saleDate = LocalDateTime.now();
        this.items = new ArrayList<>(items);

        for (SaleItem item : items) {
            subtotal += item.getAmount();
        }

        gst = subtotal * gstRate;
        total = subtotal + gst;
    }

    public int getSaleId() {
        return saleId;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public List<SaleItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getGst() {
        return gst;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Sale ID: " + saleId
                + " | Date: " + saleDate
                + " | Total: Rs." + total;
    }
}
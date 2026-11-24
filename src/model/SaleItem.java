package model;

public class SaleItem {
    private String medicineName;
    private int quantity;
    private double unitPrice;

    public SaleItem(String medicineName, int quantity, double unitPrice) {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getMedicineName() { return medicineName; }
    public int getQuantity() { return quantity; }
    public double getAmount() { return quantity * unitPrice; }
}
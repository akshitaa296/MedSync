package model;

import interfaces.Expirable;
import java.time.LocalDate;

public class Batch implements Expirable {
    private int medId;
    private String batchNo;
    private LocalDate expiryDate;
    private int quantity;

    public Batch(int medId, String batchNo, LocalDate expiryDate, int quantity) {
        this.medId = medId;
        this.batchNo = batchNo;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }

    public int getMedId() { return medId; }
    public String getBatchNo() { return batchNo; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }

    @Override
    public boolean isExpiringSoon(int days) {
        return !isExpired() &&
                !expiryDate.isAfter(LocalDate.now().plusDays(days));
    }

    @Override
    public String toString() {
        return "Batch " + batchNo + " | Exp: " + expiryDate + " | Qty: " + quantity;
    }
}
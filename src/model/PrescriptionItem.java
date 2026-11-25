package model;

public class PrescriptionItem {
    private int medicineId;
    private int quantity;
    private String dosage;

    public PrescriptionItem(int medicineId, int quantity, String dosage) {
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.dosage = dosage;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDosage() {
        return dosage;
    }

    @Override
    public String toString() {
        return "Medicine ID: " + medicineId
                + " | Quantity: " + quantity
                + " | Dosage: " + dosage;
    }
}
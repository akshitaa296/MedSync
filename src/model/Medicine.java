package model;

import enums.MedicineType;

public abstract class Medicine implements Comparable<Medicine> {
    private int id;
    private String name;
    private double price;
    private int minStock;
    private boolean requiresPrescription;

    public Medicine(int id, String name, double price, int minStock, boolean requiresPrescription) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.minStock = minStock;
        this.requiresPrescription = requiresPrescription;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getMinStock() { return minStock; }
    public boolean isRequiresPrescription() { return requiresPrescription; }

    public abstract MedicineType getType();
    public abstract String getStorageInfo();

    @Override
    public int compareTo(Medicine other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString() {
        return String.format("%-5d %-20s %-10s Rs.%-8.2f MinStock:%-4d Rx:%s",
                id, name, getType(), price, minStock,
                requiresPrescription ? "Yes" : "No");
    }
}
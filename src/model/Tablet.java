package model;

import enums.MedicineType;

public class Tablet extends Medicine {
    public Tablet(int id, String name, double price, int minStock, boolean rx) {
        super(id, name, price, minStock, rx);
    }

    @Override
    public MedicineType getType() {
        return MedicineType.TABLET;
    }

    @Override
    public String getStorageInfo() {
        return "Store below 25C, keep dry";
    }
}
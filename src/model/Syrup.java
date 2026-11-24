package model;

import enums.MedicineType;

public class Syrup extends Medicine {
    public Syrup(int id, String name, double price, int minStock, boolean rx) {
        super(id, name, price, minStock, rx);
    }

    @Override
    public MedicineType getType() {
        return MedicineType.SYRUP;
    }

    @Override
    public String getStorageInfo() {
        return "Shake well, use within 28 days of opening";
    }
}
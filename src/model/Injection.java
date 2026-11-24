package model;

import enums.MedicineType;

public class Injection extends Medicine {
    public Injection(int id, String name, double price, int minStock, boolean rx) {
        super(id, name, price, minStock, rx);
    }

    @Override
    public MedicineType getType() {
        return MedicineType.INJECTION;
    }

    @Override
    public String getStorageInfo() {
        return "Store in refrigerator at 2-8C";
    }
}
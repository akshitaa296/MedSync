package util;

import enums.MedicineType;
import exceptions.*;
import model.Medicine;
import service.StockService;
import java.time.LocalDate;

public class SampleData {
    public static void load(StockService s) {
        try {
            Medicine para = s.addMedicine(
                    MedicineType.TABLET, "Paracetamol 500mg", 2.0, 50, false);

            Medicine amox = s.addMedicine(
                    MedicineType.TABLET, "Amoxicillin 250mg", 12.0, 30, true);

            Medicine cough = s.addMedicine(
                    MedicineType.SYRUP, "Cough Syrup", 85.0, 20, false);

            Medicine insulin = s.addMedicine(
                    MedicineType.INJECTION, "Insulin Injection", 450.0, 10, true);

            Medicine cet = s.addMedicine(
                    MedicineType.TABLET, "Cetirizine 10mg", 3.5, 40, false);

            LocalDate today = LocalDate.now();

            s.addBatch(para.getId(), "P-101", today.plusDays(200), 100);
            s.addBatch(para.getId(), "P-102", today.plusDays(60), 40);

            s.addBatch(amox.getId(), "A-201", today.plusDays(120), 6);
            s.addBatch(amox.getId(), "A-202", today.plusDays(300), 20);

            s.addBatch(cough.getId(), "C-301", today.plusDays(10), 15);

            s.addBatch(insulin.getId(), "I-401", today.plusDays(90), 8);

            s.addBatch(cet.getId(), "Z-501", today.plusDays(365), 80);

        } catch (MedicineNotFoundException | ExpiredBatchException
                 | InvalidInputException e) {
            System.out.println("Sample data error: " + e.getMessage());
        }
    }
}
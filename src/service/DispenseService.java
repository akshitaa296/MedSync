package service;

import exceptions.OutOfStockException;
import exceptions.PrescriptionRequiredException;
import exceptions.InvalidInputException;
import exceptions.MedicineNotFoundException;
import io.FileLogger;
import model.Medicine;
import model.SaleItem;
import java.util.List;

public class DispenseService {
    private StockService stock;
    private BillingService billing;

    public DispenseService(StockService stock, BillingService billing) {
        this.stock = stock;
        this.billing = billing;
    }

    public void addToCart(Medicine m, int qty, String rxId)
            throws OutOfStockException, PrescriptionRequiredException,
                   InvalidInputException, MedicineNotFoundException {

        if (m.isRequiresPrescription() && (rxId == null || rxId.isBlank())) {
            throw new PrescriptionRequiredException(
                    m.getName() + " needs a prescription ID.");
        }

        List<String> used = stock.dispense(m.getId(), qty);

        billing.addItem(new SaleItem(m.getName(), qty, m.getPrice()));

        for (String line : used) {
            System.out.println("   FEFO: " + line);
        }

        FileLogger.log("Dispensed " + qty + " x " + m.getName());
    }
}
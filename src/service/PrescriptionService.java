package service;

import model.Prescription;
import exceptions.PrescriptionRequiredException;

import java.util.HashMap;
import java.util.Map;

public class PrescriptionService {
    private Map<String, Prescription> prescriptions = new HashMap<>();

    public void addPrescription(Prescription prescription) {
        prescriptions.put(
                prescription.getPrescriptionId(), prescription);
    }

    public Prescription findPrescription(String prescriptionId) {
        return prescriptions.get(prescriptionId);
    }

    public boolean validatePrescription(String prescriptionId,
                                        int medicineId)
            throws PrescriptionRequiredException {

        Prescription prescription = prescriptions.get(prescriptionId);

        if (prescription == null) {
            throw new PrescriptionRequiredException(
                    "Prescription not found.");
        }

        if (!prescription.isValid()) {
            throw new PrescriptionRequiredException(
                    "Prescription has expired or is not yet valid.");
        }

        if (!prescription.containsMedicine(medicineId)) {
            throw new PrescriptionRequiredException(
                    "Medicine is not included in this prescription.");
        }

        return true;
    }

    public int getPrescriptionCount() {
        return prescriptions.size();
    }
}
package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private String prescriptionId;
    private Patient patient;
    private String doctorName;
    private LocalDate issueDate;
    private LocalDate validUntil;
    private List<PrescriptionItem> items;

    public Prescription(String prescriptionId, Patient patient,
                        String doctorName, LocalDate issueDate,
                        LocalDate validUntil) {

        this.prescriptionId = prescriptionId;
        this.patient = patient;
        this.doctorName = doctorName;
        this.issueDate = issueDate;
        this.validUntil = validUntil;
        this.items = new ArrayList<>();
    }

    public void addItem(PrescriptionItem item) {
        items.add(item);
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public List<PrescriptionItem> getItems() {
        return new ArrayList<>(items);
    }

    public boolean isValid() {
        LocalDate today = LocalDate.now();

        return !today.isBefore(issueDate)
                && !today.isAfter(validUntil);
    }

    public boolean containsMedicine(int medicineId) {
        for (PrescriptionItem item : items) {
            if (item.getMedicineId() == medicineId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Prescription ID: " + prescriptionId
                + " | Patient: " + patient.getName()
                + " | Doctor: " + doctorName
                + " | Valid Until: " + validUntil;
    }
}
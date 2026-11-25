package service;

import model.Patient;
import java.util.HashMap;
import java.util.Map;

public class PatientService {
    private Map<Integer, Patient> patients = new HashMap<>();

    public void addPatient(Patient patient) {
        patients.put(patient.getPatientId(), patient);
    }

    public Patient findPatient(int patientId) {
        return patients.get(patientId);
    }

    public boolean patientExists(int patientId) {
        return patients.containsKey(patientId);
    }

    public void displayAllPatients() {
        for (Patient patient : patients.values()) {
            System.out.println(patient);
        }
    }

    public int getPatientCount() {
        return patients.size();
    }
}
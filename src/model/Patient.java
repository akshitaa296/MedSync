package model;

public class Patient {
    private int patientId;
    private String name;
    private String phone;

    public Patient(int patientId, String name, String phone) {
        this.patientId = patientId;
        this.name = name;
        this.phone = phone;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId
                + " | Name: " + name
                + " | Phone: " + phone;
    }
}
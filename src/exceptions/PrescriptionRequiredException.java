package exceptions;

public class PrescriptionRequiredException extends Exception {
    public PrescriptionRequiredException(String message) {
        super(message);
    }
}
package exceptions;

public class ExpiredBatchException extends Exception {
    public ExpiredBatchException(String message) {
        super(message);
    }
}
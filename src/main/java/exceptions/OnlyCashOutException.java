package exceptions;

public class OnlyCashOutException extends RuntimeException {
    public OnlyCashOutException(String message) {
        super(String.format("Error: %s. You can only cash-out.", message));
    }
}
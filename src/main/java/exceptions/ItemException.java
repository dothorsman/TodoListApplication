package exceptions;

public class ItemException extends RuntimeException {
    public ItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
package exceptions;

public class ItemIdExistsException extends RuntimeException {
    public ItemIdExistsException(int id) {
        super("This id [" + id + "] already exists!");
    }
}
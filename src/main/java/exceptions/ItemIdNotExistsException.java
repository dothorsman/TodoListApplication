package exceptions;

public class ItemIdNotExistsException extends RuntimeException {
    public ItemIdNotExistsException(int id) {
        super("This id [" + id + "] doesn't exist in the database!");
    }
}
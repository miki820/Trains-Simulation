package Exceptions;

public class DuplicateItemException extends Exception{
    public DuplicateItemException() {
        super("You can't add duplicates");
    }
}

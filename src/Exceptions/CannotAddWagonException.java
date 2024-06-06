package Exceptions;

public class CannotAddWagonException extends Exception {
    public CannotAddWagonException(){
        super("This wagon cannot be added");
    }
}

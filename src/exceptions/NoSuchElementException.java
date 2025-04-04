package exceptions;

public class NoSuchElementException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public NoSuchElementException() {
        super();
    }
    
    public NoSuchElementException(String message) {
        super(message);
    }
}
package exceptions;

public class EmptyStackException extends RuntimeException {
<<<<<<< Updated upstream
    private static final long serialVersionUID = 1L;
    
    public EmptyStackException() {
        super("Stack is empty");
=======
	private static final long serialVersionUID = 1L;

	public EmptyStackException() {
        super("Stack is empty.");
>>>>>>> Stashed changes
    }
    
    public EmptyStackException(String message) {
        super(message);
    }
}
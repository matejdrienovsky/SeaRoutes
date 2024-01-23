package OwnException;

/**
 * OwnException class
 */
public class OwnException extends RuntimeException{
    /**
     * Constructor for OwnException
     * own exception for the program
     * @param message
     */
    public OwnException(String message) {
        super(message);
    }
}

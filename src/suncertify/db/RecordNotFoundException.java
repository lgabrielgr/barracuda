package suncertify.db;

/**
 * Thrown by the server to indicate that a record does not exist or is 
 * marked as deleted in the database. 
 * 
 * @author Leo Gutierrez
 */
public class RecordNotFoundException extends Exception {

    /**
	 * Serial version.
	 */
	private static final long serialVersionUID = 9172845648588845215L;

	/**
     * Constructs a <code>RecordNotFoundException</code> with no detail
     * message.
     */
    public RecordNotFoundException() {
    	super();
    }

    /**
     * Constructs a <code>RecordNotFoundException</code> with the specified
     * detail message.
     * 
     * @param msg The detail message.
     */
    public RecordNotFoundException(final String msg) {
        super(msg);
    }

}

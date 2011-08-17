package suncertify.db;

/**
 * Thrown by the server to indicate that a record already exists in the 
 * database.
 * 
 * @author Leo Gutierrez.
 */
public class DuplicateKeyException extends Exception {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 4906931810062308615L;

	/**
	 * Constructs a <code>DuplicateKeyException</code> with no detail
	 * message.
	 */
	public DuplicateKeyException() {
		super();
	}
	
	/**
	 * Constructs a <code>DuplicateKeyException</code> with the specified
     * detail message.
	 * 
	 * @param msg The detail message.
	 */
	public DuplicateKeyException(final String msg) {
		super(msg);
	}
	
}

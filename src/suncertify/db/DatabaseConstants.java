package suncertify.db;

/**
 * Defines all the constants to use into the <code>suncertify.db</code> 
 * package.
 * 
 * @author Leo Gutierrez
 */
public class DatabaseConstants {

	/**
	 * database.path property name.
	 */
	public static final String DB_PATH_PROP = "db.path";
	
	/**
	 * "rw" file access mode.
	 */
	public static final String READ_WRITE_ACCESS_MODE = "rw";
	
	/**
	 * Deleted record indicator, '1'.
	 */
	public static final int DELETED_RECORD = 1;
	
	/**
	 * Not deleted record indicator, '0'.
	 */
	public static final int NOT_DELETED_RECORD = 0;
	
	/**
	 * Empty string space.
	 */
	public static final String EMPTY_SPACE = " ";
	
	/**
	 * Smoking room indicator, 'Y'.
	 */
	public static final String SMOKING_ROOM = "Y";
	
	/**
	 * Non-smoking room indicator, 'N'.
	 */
	public static final String NON_SMOKING_ROOM = "N";
	
	/**
	 * 'owner' field name.
	 */
	public static final String OWNER_FIELD = "owner";
	
	/**
	 * Maximum rate's value accepted by database.
	 */
	public static final double MAX_RATE_VALUE = 9999.99;
	
	/**
	 * Maximum owner id value accepted by database.
	 */
	public static final int MAX_OWNER_ID_VALUE = 99999999;
	
}

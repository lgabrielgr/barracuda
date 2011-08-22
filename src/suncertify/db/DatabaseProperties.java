package suncertify.db;

import suncertify.properties.AppProperties;

/**
 * Reads the defined properties values to use into the 
 * <code>suncertify.db</code> package.
 * 
 * @author Leo Gutierrez
 */
public class DatabaseProperties extends AppProperties {

	/**
	 * Constructs a <code>DatabaseProperties</code> object.
	 */
	public DatabaseProperties() {
		super();
	}
	
	/**
	 * Reads the database path.
	 * 
	 * @return Database path.
	 */
	public String readDatabasePath() {
		return readPropertyValue(DatabaseConstants.DB_PATH_PROP, null);
	}
	
}

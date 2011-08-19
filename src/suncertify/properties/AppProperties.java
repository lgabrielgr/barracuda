package suncertify.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads the suncertify.properties file.
 * 
 * @author Leo Gutierrez
 */
public class AppProperties {

	/**
	 * Properties file path.
	 */
	private static final String PROPERTIES_FILE = "suncertify.properties";
	
	/**
	 * Reference to the properties.
	 */
	private final Properties properties = new Properties();
	
	/**
	 * Constructs a <code>AppProperties</code> object.
	 */
	public AppProperties() {
		loadProperties();
	}

	/**
	 * Loads the properties file.
	 */
	private void loadProperties() {
		
		try {
		
			properties.load(new FileInputStream(PROPERTIES_FILE));
			
		} catch (FileNotFoundException e) {

			System.out.println("Unable to load the properties file due to: " +
					e.getMessage());
			
		} catch (IOException e) {
			
			System.out.println("Unable to load the properties file due to: " +
					e.getMessage());
			
		}
	}
	
	/**
	 * Searches for the property with the specified name in the properties 
	 * file. If the property is not found, then the specified default value
	 * is returned.
	 * 
	 * @param propertyName Property to search.
	 * @param defaultValue Default value to set if property is not found.
	 * @return Property value if found, otherwise the default value.
	 */
	public String readPropertyValue(final String propertyName,
			final String defaultValue) {
		return properties.getProperty(propertyName, defaultValue);
	}
}

package suncertify.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads the suncertify.properties file.
 * 
 * @author Leo Gutierrez
 */
public class AppProperties {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = AppProperties.class.getName();
	
	/**
	 * Properties file path.
	 */
	private static final String PROPERTIES_FILE = "suncertify.properties";
	
	/**
	 * Reference to the properties.
	 */
	private static final Properties properties = new Properties();
	
	/**
	 * Loads the properties file when the class is loaded.
	 */
	static {
		loadProperties();
	}
	
	/**
	 * Constructs a <code>AppProperties</code> object.
	 */
	public AppProperties() {
		
	}

	/**
	 * Loads the properties file. The file should have the proper 
	 * +rw (read and write) permissions to perform the operations in the file. 
	 */
	private static void loadProperties() {
		
		FileInputStream propertiesFIS = null;
		try {
		
			propertiesFIS = new FileInputStream(PROPERTIES_FILE);
			
			properties.load(propertiesFIS);
			
		} catch (FileNotFoundException e) {

			System.out.println("Unable to load the properties file due to: "
					+ e.getMessage());
			
		} catch (IOException e) {
			
			System.out.println("Unable to load the properties file due to: "
					+ e.getMessage());
			
		} finally {
			if (propertiesFIS != null) {
				try {
					propertiesFIS.close();
				} catch (IOException e) {
					System.out.println("Unable to close the stream where "
							+ "properties file were loaded: " + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Stores in the properties file any update made with the save methods.
	 */
	private void refreshProperties() {
		
		final String methodName = "refreshProperties";
		AppPropertiesLogger.entering(CLASS_NAME, methodName);
		
		FileOutputStream propertiesFOS = null;
		try {
			
			propertiesFOS = new FileOutputStream(PROPERTIES_FILE);
		
			properties.store(propertiesFOS, null);
			
		} catch (FileNotFoundException e) {
			
			AppPropertiesLogger.warning(CLASS_NAME, methodName, 
					"Unable to load the properties file due to: "
							+ e.getMessage());
			
		} catch (IOException e) {

			AppPropertiesLogger.warning(CLASS_NAME, methodName, 
					"Unable to load the properties file due to: "
							+ e.getMessage());
			
		} finally {
			
			if (propertiesFOS != null) {
				try {
					propertiesFOS.close();
				} catch (IOException e) {

					AppPropertiesLogger.warning(CLASS_NAME, methodName, 
							"Unable to close stream used to refresh the "
							+ "properties file: " + e.getMessage());
				}
			}
			
		}
		
		AppPropertiesLogger.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * Saves the specified property name and value into the properties file.
	 * 
	 * @param propertyName Property name to set. 
	 * @param propertyValue Property vale to save.
	 */
	public void savePropertyValue(final String propertyName,
			final String propertyValue) {
		
		properties.setProperty(propertyName, propertyValue);
		
		refreshProperties();
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

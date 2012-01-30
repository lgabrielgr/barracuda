package suncertify.log;

import suncertify.properties.AppProperties;

/**
 * Reads the defined properties values to use into the 
 * <code>suncertify.log</code> package.
 * 
 * @author Leo Gutierrez
 */
public class AppLoggerProperties extends AppProperties {

	/**
	 * logging.level property name.
	 */
	private static final String LOGGING_LEVEL_PROP = "logging.level";
	
	/**
	 * Default logging level.
	 */
	private static final String DEFAULT_LOG_LEVEL = "INFO";

	/**
	 * Constructs a <code>AppLoggerProperties</code> object.
	 */
	public AppLoggerProperties() {
		super();
	}
	
	/**
	 * Retrieves the logging level to use into the application.
	 * 
	 * @return Logging level.
	 */
	public final String readLoggingLevel() {
		return readPropertyValue(LOGGING_LEVEL_PROP, DEFAULT_LOG_LEVEL);
	}
	
}

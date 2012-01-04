package suncertify.log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Creates <code>java.util.logging.Logger</code> to use into the application.
 * 
 * @author Leo Gutierrez
 */
public class AppLogger {

	/**
	 * Log directory.
	 */
	private static final String LOG_DIRECTORY = "log";
	
	/**
	 * Log file name.
	 */
	private static final String LOG_FILE = "app.log";
	
	/**
	 * Log file path.
	 */
	private static final String LOG_FILE_PATH = LOG_DIRECTORY
			+ File.separator + LOG_FILE;
	
	/**
	 * Reference to the <code>FileHandler</code> to add
	 * to logger.
	 */
	private static FileHandler handler;
	
	static {
		createFileHandler();	
	}

	/**
	 * Creates a valid <code>FileHandler</code> to add to any 
	 * <code>java.util.logging.Logger</code> created.
	 */
	private static void createFileHandler() {
		
		try {
			
			handler = new FileHandler(LOG_FILE_PATH);
			handler.setFormatter(new SimpleFormatter());
			
		} catch (SecurityException e) {
			
			System.out.println("Unable to create the file handler for logger "
					+ "due to a security violation: " + e.getMessage());
			
		} catch (IOException e) {
			
			createFileHandlerManually();
			
		}
	}

	/**
	 * Creates the log file manually to create a <code>FileHandler</code>.
	 */
	private static void createFileHandlerManually() {
		
		final File logDirectory = new File(LOG_DIRECTORY);
		
		try {
			if (logDirectory.mkdir()) {
				
				final File logFile = new File(LOG_FILE);
				
				if (logFile.createNewFile()) {
				
					handler = new FileHandler(LOG_FILE_PATH);
					handler.setFormatter(new SimpleFormatter());
					
				} else {
					
					System.out.println("Unable to create the log file, check "
							+ "directory permissions");
					
				}
			} else {

				System.out.println("Unable to create the log file, check "
						+ "directory permissions");
				
			}
		} catch (IOException e) {
			
			System.out.println("Unable to create the file handler for the "
					+ "logger due to an I/O error: " + e.getMessage());
			
		}
	}
	
	/**
	 * Creates a <code>java.util.logging.Logger</code> with the given input
	 * parameter name, adds a valid <code>FileHandler</code> and sets a
	 * <code>Level</code> defined in the suncertify.properties file.
	 * 
	 * @param name Name to set to Logger.
	 * @return A <code>java.util.logging.Logger</code>.
	 */
	public static Logger getLogger(final String name) {
		
		final Logger logger = Logger.getLogger(name);
		
		final Level logLevel = readLogLevel();
		logger.setLevel(logLevel);
		
		if (handler != null) {
			logger.addHandler(handler);
		}
		
		return logger;
	}

	/**
	 * Reads the log level defined in the suncertify.properties file.
	 * @return
	 */
	private static Level readLogLevel() {
		
		final AppLoggerProperties loggerProps = new AppLoggerProperties();
		
		Level logLevel;
		try {
			logLevel = Level.parse(loggerProps.readLoggingLevel());
		} catch (IllegalArgumentException e) {
			logLevel = Level.WARNING;
		}
		
		return logLevel;
	}
	
}

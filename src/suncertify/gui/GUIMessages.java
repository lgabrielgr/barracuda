package suncertify.gui;

/**
 * Contains the messages to display to the user into the application.
 * <br />For a future version, this class's messages should be considered for 
 * internationalization.
 * 
 * @author Leo Gutierrez
 *
 */
public class GUIMessages {

	/**
	 * Text to display as the Server window title.
	 */
	public static final String SERVER_TITLE_TEXT = "Server";
	
	/**
	 * Text to display in the primary button to start the server.
	 */
	public static final String START_SERVER_TEXT = "Start server";
	
	/**
	 * Text to display in the primary button to connect to server.
	 */
	public static final String CONNECT_TO_SERVER_TEXT = "Connect to server";
	
	/**
	 * Text to display in the secondary button to exit the application.
	 */
	public static final String EXIT_TEXT = "Exit";
	
	/**
	 * Text to display for the database file location label.
	 */
	public static final String DATABASE_LOCATION_LABEL_TEXT = 
			"Database location:";
	
	/**
	 * Text to display for the server location label.
	 */
	public static final String HOSTNAME_LABEL_TEXT = "Hostname: ";
	
	/**
	 * Text to display for the server port label.
	 */
	public static final String SERVER_PORT_LABEL_TEXT = "Port: ";
	
	/**
	 * Text to display for the exit dialog window.
	 */
	public static final String EXIT_MESSAGE_DIALOG_TEXT = 
			"Please, confirm to quit the application.";
	
	/**
	 * Text to display for the exit dialog window title.
	 */
	public static final String EXIT_MESSAGE_TITLE_TEXT = "Exit";
	
	
	/**
	 * Text to display when the server is running.  
	 */
	public static final String SERVER_RUNNING_STATUS_MESSAGE = 
			"Server is running.";
	
	/**
	 * Text to display for the initial server status.
	 */
	public static final String INITIAL_SERVER_STATUS_MESSAGE = 
			"Please, enter the required information and start server";
	
	/**
	 * Text to display for the initial connect to server status.
	 */
	public static final String INITIAL_CONNECT_STATUS_MESSAGE =
			"Please, enter the required information and connect to server";
	
	/**
	 * Text to display in the filtering when user selects the database 
	 * file.
	 */
	public static final String DATABASE_FILE_CHOOSER_DESCRIPTION = 
			"Database file (*.db)";
	
	/**
	 * Text to display to user when it is unable to start the server.
	 */
	public static final String UNABLE_TO_START_SERVER_MESSAGE = 
			"Unable to start the server, please see logs for details";
	
	/**
	 * Text to display to user when it is unable to connect to user.
	 */
	public static final String UNABLE_TO_CONNECT_MESSAGE = 
			"Connection refused, please see logs for details.";
	
	/**
	 * Text to display to user when it is attemping to connect to server.
	 */
	public static final String CONNECTING_TO_SERVER_MESSAGE = 
			"Connecting to server ...";
	
	/**
	 * Text to display to user when an invalid database file path is entered.
	 */
	public static final String INVALID_DATABASE_MESSAGE = 
			"The database file is not a valid file (*.db) or a non-existing " +
			"file. Please, enter a valid database file.";
	
	/**
	 * Text to display to user when the database file path is not readable and 
	 * not writable. 
	 */
	public static final String DATABASE_NOT_EDITABLE_MESSAGE = 
			"The database file is not readable and/or writable. Please, verify " +
			"the file's permissions.";
	
	/**
	 * Text to display to user when an invalid port number is entered.
	 */
	public static final String INVALID_PORT_NUMBER_MESSAGE = 
			"Please, enter a valid port number.";
	
	/**
	 * Text to display to the user when an invalid hostname is entered.
	 */
	public static final String INVALID_HOSTNAME_MESSAGE = 
			"Please, enter a valid hostname";
	
	/**
	 * Text to set as title for the warning message dialogs.
	 */
	public static final String WARNING_TEXT = "Warning";
	
	/**
	 * Text to set as title for the error message dialogs.
	 */
	public static final String ERROR_TEXT = "Error";
	
}

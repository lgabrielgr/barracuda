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
	 * Text to display as the Client (StandAlone) window title.
	 */
	public static final String CLIENT_TITLE_TEXT =  "Client";
	
	/**
	 * Text to display as the Connect to Server window title.
	 */
	public static final String CONNECT_TO_SERVER_TITLE_TEXT = 
			"Connect to Server";
	
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
	 * Text to display to let know user that he/she needs to connect
	 * to database.
	 */
	public static final String CONNECT_TO_DATABASE_TEXT = 
			"Connect to Database";
	
	/**
	 * Text to display for the server location label.
	 */
	public static final String HOSTNAME_LABEL_TEXT = "Hostname: ";
	
	/**
	 * Text to display for the hotel name label.
	 */
	public static final String HOTEL_NAME_LABEL_TEXT = "Hotel name: ";
	
	/**
	 * Text to display for the location name label.
	 */
	public static final String LOCATION_LABEL_TEXT = "Location: ";
	
	/**
	 * Text to display in the search button.
	 */
	public static final String SEARCH_BUTTON_NAME = "Search";
	
	/**
	 * Text to display in the book room button name.
	 */
	public static final String BOOK_ROOM_BUTTON_NAME = "Book Room";
	
	/**
	 * Text to display for the server port label.
	 */
	public static final String SERVER_PORT_LABEL_TEXT = "Port: ";
	
	/**
	 * Browse button text as 'Choose File'.
	 */
	public static final String BROWSE_BUTTON_SIMPLE_TEXT = "Choose File";
	
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
	 * Text to display as title for a question dialog with the user. 
	 */
	public static final String CONFIRM_MESSAGE_TITLE_TEXT = "Confirm";
	
	/**
	 * Text to display when the server is unable to retrieve the host name.
	 */
	public static final String UNKNOWN_HOST = "Unknown host";
	
	/**
	 * Text to display to user when the server is unable to start.
	 */
	public static final String CANT_START_THE_SERVER_MESSAGE = 
			"Unable to start the server, please see logs for details.";
	
	/**
	 * Text to display to user when the server is running but the system 
	 * wasn't unable to retrieve the host name. 
	 */
	public static final String CANT_RETRIEVE_SERVER_HOST_NAME = 
			"The server is running, but the System is unable to retrieve " +
					"the server's host name. Please contact the " +
					"System Administrator to get the host name.";
	
	/**
	 * Text to display for the initial status to connect to database.
	 */
	public static final String CONNECT_TO_DATABASE_STATUS_MESSAGE = 
			"Please, select the database file location and press connect " 
					+ "button.";
	
	/**
	 * Text to display when the server is running, adding the hostname.
	 */
	public static final String SERVER_RUNNING_STATUS_MESSAGE = 
			"Server is running on host {0}";
	
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
	 * Text to display when can't connect to server.
	 */
	public static final String ERROR_CONNECT_STATUS_MESSAGE = 
			"Unable to connect to server, please verify the information and " 
					+ "try again.";
	
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
			"Unable to start the server, please see logs for details.";
	
	/**
	 * Text to display to user when it is unable to connect to user.
	 */
	public static final String UNABLE_TO_CONNECT_MESSAGE = 
			"Connection refused, please see logs for details.";
	
	/**
	 * Text to display to user when it is unable to perform the search action.
	 */
	public static final String UNABLE_TO_SEARCH_MESSAGE = 
			"Unable to perform the search action, please see logs for details.";
	
	/**
	 * Text to display to user when it is tried to update a row with an 
	 * invalid value.
	 */
	public static final String INVALID_VALUE_TO_SET_MESSAGE = 
			"The specified value is not valid";
	
	/**
	 * Text to display to user when the application failed to book the room
	 * in the database.
	 */
	public static final String BOOK_ROOM_FAILED_MESSAGE =
			"Unable to book the room. Please logs for details.";
	
	/**
	 * Text to display to user when the application failed to update a record
	 * because the record was not found in the database.
	 */
	public static final String FAILED_BOOK_ROOM_RECORD_NOT_FOUND_MESSAGE =
			"Unable to update the record, the record selected was not found "
			+ "in the database.";
	
	/**
	 * Text to display to user when He/She tries to book a room already booked.
	 */
	public static final String ROOM_ALREADY_BOOKED_MESSAGE = 
			"Unable to book the selected room, it was previously booked by "
			+ "another instance.";
	
	/**
	 * Text to display in the status bar, when it is unable to update a record
	 * because the server is not responding.
	 */
	public static final String FAILED_BOOK_ROOM_DB_MESSAGE = 
			"Failed to update the record, database server is not responding.";
	
	/**
	 * Text to display when it is asked to user to enter the owner id.
	 */
	public static final String ENTER_OWNER_ID_MESSAGE = 
			"Please, enter the owner ID";
	
	/**
	 * Title to set when it is asked to user to enter the owner id.
	 */
	public static final String BOOK_ROOM_TITLE = "Book Room";
	
	/**
	 * Text to display to user when it is unable to contact
	 * the database server.
	 */
	public static final String CANT_CONTACT_DB_MESSAGE = 
			"Unable to connect to the database server. " +
					"Please, contact the System Administrator.";
	
	/**
	 * Text to display to user in the status bar when the client is not 
	 * connected to a server.
	 */
	public static final String NOT_CONNECTED_TO_SERVER_MESSAGE = 
			"Not connected to a Server.";
	
	/**
	 * Text to display to user in client's status bar to show to which 
	 * server the client is connected.
	 */
	public static final String CONNECTED_TO_SERVER_MESSAGE = 
			"Connected to Server running on host {0}";
	
	/**
	 * Text to display to user in client's status bar when it is started
	 * in "alone" mode.
	 */
	public static final String CONNECTED_TO_LOCAL_DB_MESSAGE = 
			"Connected to a local database (not connected to a Server)";
	
	/**
	 * Text to display to user when it is attempting to connect to server.
	 */
	public static final String CONNECTING_TO_SERVER_MESSAGE = 
			"Connecting to server ...";
	
	/**
	 * Text to display to user when an invalid database file path is entered.
	 */
	public static final String INVALID_DATABASE_MESSAGE = 
			"The database file is not a valid file (*.db) or is a non-existing" + 
					" file. Please, enter a valid database file.";
	
	/**
	 * Text to display to user when the database file path is not readable and 
	 * not writable. 
	 */
	public static final String DATABASE_NOT_EDITABLE_MESSAGE = 
			"The database file is not readable and/or writable. Please, verify" + 
					" the file's permissions.";

	/**
	 * Text to display to user when it was an error during the initial startup.
	 */
	public static final String INITIAL_STARTUP_ERROR_MESSAGE = 
			"Unable to load all records ot display as the initial startup, " + 
					"please see log for details";
	
	/**
	 * Text to display to user when a room was successfully booked.
	 */
	public static final String ROOM_BOOKED_MESSAGE = 
			"Room booked successfully:\n\nHotel: {0}\nLocation: {1}\n" +
					"Owner ID: {2}\n\nThank You!";
	
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
	
	/**
	 * Text to set as title for the information message dialogs. 
	 */
	public static final String INFORMATION_TEXT = "Information";  
	
	/**
	 * Constructor.
	 */
	protected GUIMessages() {
		
	}
	
}

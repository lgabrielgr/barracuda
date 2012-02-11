package suncertify.gui;

import suncertify.controller.ConnectToDatabaseListener;
import suncertify.controller.ExitServerListener;

/**
 * Provides the graphical user interface to connect to the database and start
 * the client window in a locally mode (no networking interaction with the
 * database).
 * 
 * @author Leo Gutierrez
 */
public class ConnectToDatabaseWindow extends ServerWindow {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 448302L;

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = ConnectToDatabaseWindow.class.getName();
	
	/**
	 * Constructs a <code>ConnectToDatabaseWindow</code> object.
	 */
	public ConnectToDatabaseWindow() {
		
	}

	
	/**
	 * Adds the proper text to display in the different frame's components.
	 */
	protected final void addProperTextOnComponents() {
		
		final String methodName = "addProperTextOnComponents";
		GUILogger.entering(CLASS_NAME, methodName);
		
		setTitle(GUIMessages.CONNECT_TO_DATABASE_TEXT);
		
		setServerLocationLabelText(GUIMessages.DATABASE_LOCATION_LABEL_TEXT);
		
		setServerLocationFieldText(readDatabasePath());
		
		removePortSection();
		
		setStatusLabelText(GUIMessages.CONNECT_TO_DATABASE_STATUS_MESSAGE);
		
		setPrimaryServerButtonText(GUIMessages.CONNECT_TO_DATABASE_TEXT);
		addListenerToPrimaryServerButton(new ConnectToDatabaseListener(this));
		
		final ExitServerListener exitWindowListener = new ExitServerListener();
		
		setSecondaryServerButtonText(GUIMessages.EXIT_TEXT);
		addListenerToSecondaryServerButton(exitWindowListener);
		
		addWindowListener(exitWindowListener);
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
	}
	
}

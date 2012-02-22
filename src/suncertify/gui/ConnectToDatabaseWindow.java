package suncertify.gui;

import java.awt.event.KeyEvent;

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
	 * Adds the proper configuration for the Connect to Database window.
	 */
	protected final void addConfigurationOnComponents() {
		
		final String methodName = "addConfigurationOnComponents";
		GUILogger.entering(CLASS_NAME, methodName);
		
		final ConnectToDatabaseListener connectToDBListener =
				new ConnectToDatabaseListener(this);
		
		setTitle(GUIMessages.CONNECT_TO_DATABASE_TEXT);
		
		setServerLocationLabelText(GUIMessages.DATABASE_LOCATION_LABEL_TEXT);
		
		setServerLocationTextField(readDatabasePath());
		addDocumentListenerToDBServerTextField(connectToDBListener);
		
		removePortSection();
		
		setStatusLabelText(GUIMessages.CONNECT_TO_DATABASE_STATUS_MESSAGE);
		
		setPrimaryServerButtonText(GUIMessages.CONNECT_TO_DATABASE_TEXT, 
				KeyEvent.VK_D);
		addListenerToPrimaryServerButton(connectToDBListener);
		
		final ExitServerListener exitWindowListener = new ExitServerListener();
		
		setSecondaryServerButtonText(GUIMessages.EXIT_TEXT, KeyEvent.VK_E);
		addListenerToSecondaryServerButton(exitWindowListener);
		
		addWindowListener(exitWindowListener);
		
		connectToDBListener.insertUpdate(null);
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
	}
	
}

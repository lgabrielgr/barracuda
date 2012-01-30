package suncertify.gui;

import suncertify.controller.ExitServerListener;
import suncertify.controller.StartServerListener;

/**
 * Provides the graphical user interface for the Server window to start/stop 
 * the server.
 * 
 * @author Leo Gutierrez
 */
public class ServerWindow extends AbstractServerWindow {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 590350L;
	
	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = ServerWindow.class.getName();
	
	/**
	 * Constructs a <code>ServerWindow</code> object and displays the Server 
	 * window.
	 */
	public ServerWindow() {
		
		displayWindow();
		
	}
	
	/**
	 * Adds the proper text to display in the different frame's components.
	 */
	protected final void addProperTextOnComponents() {
		
		final String methodName = "addProperTextOnComponents";
		GUILogger.entering(CLASS_NAME, methodName);
		
		setServerLocationLabelText(GUIMessages.DATABASE_LOCATION_LABEL_TEXT);
		
		setServerLocationFieldText(readDatabasePath());
		
		setPortNumberFieldText(readRMIPort());
		
		setStatusLabelText(GUIMessages.INITIAL_SERVER_STATUS_MESSAGE);
		
		setPrimaryServerButtonText(GUIMessages.START_SERVER_TEXT);
		addListenerToPrimaryServerButton();
		
		final ExitServerListener exitServerListener = new ExitServerListener();
		
		setSecondaryServerButtonText(GUIMessages.EXIT_TEXT);
		addListenerToSecondaryServerButton(exitServerListener);
		
		addWindowListener(exitServerListener);
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
	}

	/**
	 * Adds the proper listener to the primary button on Server window frame.
	 */
	protected final void addListenerToPrimaryServerButton() {
		addListenerToPrimaryServerButton(new StartServerListener(this));
	}
	
	/**
	 * Displays the server window.
	 */
	private void displayWindow() {
		
		final String methodName = "displayWindow";
		GUILogger.entering(CLASS_NAME, methodName);
		
		pack();
        GUIUtils.centerOnScreen(this);
        setVisible(true);
        
        GUILogger.exiting(CLASS_NAME, methodName);
        
	}
	
}

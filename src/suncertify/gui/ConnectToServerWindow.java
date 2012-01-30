package suncertify.gui;

import suncertify.controller.ConnectToServerListener;
import suncertify.controller.ExitConnectToServerListener;

/**
 * Provides the graphical user interface for the Connect to Server window.
 * 
 * @author Leo Gutierrez
 */
public class ConnectToServerWindow extends AbstractServerWindow {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 19715L;

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = 
			ConnectToServerWindow.class.getName(); 
	
	/**
	 * Constructs a <code>ConnectToServerWindow</code> and displays the
	 * window to connect to the server.
	 */
	public ConnectToServerWindow() {
		
		displayWindow();
		
	}
	
	/**
	 * Adds the proper text to display in the different frame's components.
	 */
	protected final void addProperTextOnComponents() {
		
		final String methodName = "addProperTextOnComponents";
		GUILogger.entering(CLASS_NAME, methodName);
		
		setTitle(GUIMessages.CONNECT_TO_SERVER_TITLE_TEXT);
		
		setServerLocationLabelText(GUIMessages.HOSTNAME_LABEL_TEXT);

		setServerLocationFieldText(readRMIHost());

		setPortNumberFieldText(readRMIPort());

		setStatusLabelText(GUIMessages.INITIAL_CONNECT_STATUS_MESSAGE);

		setPrimaryServerButtonText(GUIMessages.CONNECT_TO_SERVER_TEXT);
		addListenerToPrimaryServerButton(new ConnectToServerListener(this));

		setSecondaryServerButtonText(GUIMessages.EXIT_TEXT);
		addListenerToSecondaryServerButton(new ExitConnectToServerListener());

		addWindowListener(new ExitConnectToServerListener());
		
		removeBrowseButton();
		
		GUILogger.exiting(CLASS_NAME, methodName);

	}

	/**
	 * Displays the window.
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

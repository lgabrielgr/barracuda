package suncertify.gui;

import java.awt.event.KeyEvent;

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
	 * Adds the proper configuration for Server window.
	 */
	protected void addConfigurationOnComponents() {
		
		final String methodName = "addConfigurationOnComponents";
		GUILogger.entering(CLASS_NAME, methodName);
		
		final StartServerListener startServerListener = 
				new StartServerListener(this);
		
		setServerLocationLabelText(GUIMessages.DATABASE_LOCATION_LABEL_TEXT);
		
		setServerLocationTextField(readDatabasePath());
		addDocumentListenerToDBServerTextField(startServerListener);
		
		setPortNumberTextField(readRMIPort());
		addDocumentListenerToPortTextField(startServerListener);
		
		setStatusLabelText(GUIMessages.INITIAL_SERVER_STATUS_MESSAGE);
		
		setPrimaryServerButtonText(GUIMessages.START_SERVER_TEXT, 
				KeyEvent.VK_S);
		addListenerToPrimaryServerButton(startServerListener);
		
		final ExitServerListener exitServerListener = new ExitServerListener();
		
		setSecondaryServerButtonText(GUIMessages.EXIT_TEXT, KeyEvent.VK_E);
		addListenerToSecondaryServerButton(exitServerListener);
		
		addWindowListener(exitServerListener);
		
		startServerListener.insertUpdate(null);
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
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

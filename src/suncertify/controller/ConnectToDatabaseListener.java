package suncertify.controller;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import suncertify.db.Database;
import suncertify.gui.AbstractServerWindow;
import suncertify.gui.GUIMessages;
import suncertify.gui.StandAloneWindow;

/**
 * Provides the functionality when user clicks on 'Start server' button on 
 * the Server Window in the mode when user wants to starts the server and run 
 * the Client window. 
 * <br />Starts the server if the input user data is valid, and then start the
 * Client window.
 * 
 * @author Leo Gutierrez
 */
public class ConnectToDatabaseListener extends StartServerListener {

	/**
	 * Reference to the Server window frame.
	 */
	private AbstractServerWindow serverWindow;
	
	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = 
			ConnectToDatabaseListener.class.getName();
	
	/**
	 * Constructs a <code>ConnectToDatabaseListener</code> object.
	 * 
	 * @param abstractServerWindow A reference to the Server window frame.
	 */
	public ConnectToDatabaseListener(
			final AbstractServerWindow abstractServerWindow) {
		
		super(abstractServerWindow);
		
		serverWindow = abstractServerWindow;
		
	}
	
	/**
	 * Invoked when user clicks on Connect to Database button.
	 * <br />Starts the server and runs the Client window.
	 *
	 * @param actionEvent The action event.
	 */
	public final void actionPerformed(final ActionEvent actionEvent) {

		final String methodName = "actionPerformed";
		ControllerLogger.entering(CLASS_NAME, methodName);

		try {

			if (serverWindow == null) {

				throw new RemoteException("A reference to the Server window "
						+ "does not exist");

			}

			if (isValidUserInput(false)) {
			
				updatePropertiesWithUserInput(false);
				
				serverWindow.closeWindow();
				
				new StandAloneWindow(new Database());
				
			}

		} catch (RemoteException e) {

			ControllerLogger.severe(CLASS_NAME, methodName,
					"Unable to start the server: " + e.getMessage());

			JOptionPane.showMessageDialog(serverWindow,
                    GUIMessages.UNABLE_TO_START_SERVER_MESSAGE,
                    GUIMessages.ERROR_TEXT,
                    JOptionPane.ERROR_MESSAGE);
			
		} 
		
		ControllerLogger.exiting(CLASS_NAME, methodName);
		
	}
	
}
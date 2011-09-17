package suncertify.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import suncertify.gui.AbstractServerWindow;
import suncertify.gui.GUIMessages;
import suncertify.gui.GUIUtils;
import suncertify.remote.RemoteDatabaseConnector;

/**
 * Provides the functionality when user clicks on connect to server button.
 * <br />Connects to server if the input data is valid, and starts the main 
 * window.
 * 
 * @author Leo Gutierrez
 */
public class ConnectToServerListener implements ActionListener {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = 
			ConnectToServerListener.class.getName(); 
	
	/**
	 * Reference to the connect to server window frame.
	 */
	private AbstractServerWindow connectToServerWindow;
	
	/**
	 * Constructs a <code>ConnectToServerListener</code> object.
	 * 
	 * @param connectToServerWindow Reference to the connect to server window
	 *                              frame.
	 */
	public ConnectToServerListener(
			final AbstractServerWindow connectToServerWindow) {
		
		this.connectToServerWindow = connectToServerWindow;
		
	}
	
	/**
	 * Invoked when user clicks on Connect to server button, and does:
	 * <br />- Validate the user input.
	 * <br />- Updates the properties file with the user input.
	 * <br />- Connects to server.
	 * <br />- If successful in connecting to server, starts the main window.
	 * 
	 * @param actionEvent The action event.
	 */
	public void actionPerformed(final ActionEvent actionEvent) {
		
		final String methodName = "actionPerformed";
		ControllerLogger.entering(CLASS_NAME, methodName);
				
		try {

			final String hostname = 
					connectToServerWindow.getServerLocationFieldText();
			final String port = 
					connectToServerWindow.getPortNumberFieldText();
			
			if (isValidUserInput(hostname, port)) {
				
				updatePropertiesWithUserInput(hostname, port);

				connectToServerWindow.setStatusLabelText(
						GUIMessages.CONNECTING_TO_SERVER_MESSAGE);
				
				RemoteDatabaseConnector.getConnection(hostname, 
						Integer.valueOf(port));
				
				ControllerLogger.info(CLASS_NAME, methodName, 
						"Connected to server");
				
				// TODO: Close this window and starts main appliaction
				connectToServerWindow.closeWindow();
				
			} 

		} catch (RemoteException e) {
			
			ControllerLogger.severe(CLASS_NAME, methodName, 
					"Can't connect to server: " + e.getMessage());
			
			JOptionPane.showMessageDialog(connectToServerWindow,
                    GUIMessages.UNABLE_TO_CONNECT_MESSAGE,
                    GUIMessages.ERROR_TEXT,
                    JOptionPane.ERROR_MESSAGE);
			
			connectToServerWindow.setStatusLabelText(
					GUIMessages.INITIAL_CONNECT_STATUS_MESSAGE);
			
		} finally {

			ControllerLogger.exiting(CLASS_NAME, methodName);

		}
				
	}

	/**
	 * 
	 * @param hostname
	 * @param port
	 */
	private void updatePropertiesWithUserInput(final String hostname,
			final String port) {
		
		final String methodName = "updatePropertiesWithUserInput";
		ControllerLogger.entering(CLASS_NAME, methodName, hostname, port);
		
		connectToServerWindow.updateRMIHost(hostname);
		connectToServerWindow.updateRMIPort(port);
		
		ControllerLogger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * Verifies if the hostname and port number are valid.
	 * <br />If either the hostname or the port number is not valid, 
	 * a warning message is shown to the user.
	 * 
	 * @param hostname Hostname to verify.
	 * @param port Port number to verify.
	 * @return True if the hostname and port number are valid; False otherwise.
	 */
	private boolean isValidUserInput(final String hostname, 
			final String port) {
		
		final String methodName = "isValidUserInput";
		ControllerLogger.entering(CLASS_NAME, methodName, hostname, port);
		
		try {
			
			if (!isValidHostname(hostname)) {

				JOptionPane.showMessageDialog(connectToServerWindow,
						GUIMessages.INVALID_HOSTNAME_MESSAGE,
						GUIMessages.WARNING_TEXT,
						JOptionPane.WARNING_MESSAGE);

				return false;

			}

			if (!GUIUtils.isPortNumberValid(port)) {

				JOptionPane.showMessageDialog(connectToServerWindow,
						GUIMessages.INVALID_PORT_NUMBER_MESSAGE,
						GUIMessages.WARNING_TEXT,
						JOptionPane.WARNING_MESSAGE);

				return false;

			}

			return true;

		} finally {
			
			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}
		
	}
	
	/**
	 * Verifies if the hostname entered by the user is not empty.
	 * 
	 * @param hostname Hostname to verify.
	 * @return True is the hostname is valid; False otherwise.
	 */
	private boolean isValidHostname(final String hostname) {
		
		final String methodName = "isValidHostname";
		ControllerLogger.entering(CLASS_NAME, methodName, hostname);
		
		try {
			
			if ((hostname == null) || ("".equals(hostname.trim()))) {

				ControllerLogger.warning(CLASS_NAME, methodName, 
						"Hostname value empty");
				
				return false;

			}

			return true;

		} finally {
			
			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}
	}
	
}

package suncertify.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import suncertify.db.IDatabase;
import suncertify.gui.AbstractServerWindow;
import suncertify.gui.GUIMessages;
import suncertify.gui.GUIUtils;
import suncertify.gui.ClientWindow;
import suncertify.remote.RemoteDatabaseConnector;

/**
 * Provides the functionality when user clicks on connect to server button.
 * <br />Connects to server if the input data is valid and complete, then 
 * starts the Client window.
 * 
 * @author Leo Gutierrez
 */
public class ConnectToServerListener 
implements ActionListener, DocumentListener {

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
	 * @param abstractServerWindow Reference to 'Connect to Server' window
	 *                              frame.
	 */
	public ConnectToServerListener(
			final AbstractServerWindow abstractServerWindow) {
		
		connectToServerWindow = abstractServerWindow;
		
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
	public final void actionPerformed(final ActionEvent actionEvent) {
		
		final String methodName = "actionPerformed";
		ControllerLogger.entering(CLASS_NAME, methodName);

		if (connectToServerWindow == null) {

			ControllerLogger.severe(CLASS_NAME, methodName,
					"User clicked on Connect To Server button but a "
							+ "reference to the connect to server frame "
							+ "does not exist");

			GUIUtils.showErrorMessageDialog(null,
					GUIMessages.UNABLE_TO_CONNECT_MESSAGE);

			return;

		}

		connectToServer(); 

		ControllerLogger.exiting(CLASS_NAME, methodName);
				
	}
	
	/**
	 * Invoked when user updates text on hostname/port text field.
	 * 
	 * @param documentEvent The document event.
	 */
	public void insertUpdate(final DocumentEvent documentEvent) {
		verifyIfUserCanConnectToServer();
	}

	/**
	 * Invoked when user removes text on hostname/port text field.
	 * 
	 * @param documentEvent The document event.
	 */
	public void removeUpdate(final DocumentEvent documentEvent) {
		verifyIfUserCanConnectToServer();
	}

	/**
	 * Invoked when user changes text on hostname/port text field.
	 * 
	 * @param documentEvent The document event.
	 */
	public void changedUpdate(final DocumentEvent documentEvent) {
		verifyIfUserCanConnectToServer();
	}

	/**
	 * Verifies if the user can connect to the server. Verifies both text 
	 * fields hostname and port, if one of those are empty, the Connect To
	 * Server button is disabled. It is only enabled if both fields are 
	 * completed.
	 */
	private void verifyIfUserCanConnectToServer() {
		
		final String methodName = "verifyIfUserCanConnectToServer";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		final String hostname =
				connectToServerWindow.getDBServerLocationFieldText();
		final String port =
				connectToServerWindow.getPortNumberFieldText();
		
		if ((GUIUtils.isEmptyValue(hostname))
				|| (GUIUtils.isEmptyValue(port))) {
			
			connectToServerWindow.setEnabledPrimaryServerButton(false);
			
		} else {
			
			connectToServerWindow.setEnabledPrimaryServerButton(true);
			
		}
		
		ControllerLogger.exiting(CLASS_NAME, methodName);
		
	}
	
	
	/**
	 * Starts a connection with the server.
	 */
	private void connectToServer() {
		
		final String methodName = "connectToServer";
		ControllerLogger.entering(CLASS_NAME, methodName);

		try {

			final String hostname =
					connectToServerWindow.getDBServerLocationFieldText();
			final String port =
					connectToServerWindow.getPortNumberFieldText();

			if (isValidUserInput(hostname, port)) {

				updatePropertiesWithUserInput(hostname, port);

				connectToServerWindow.setStatusLabelText(
						GUIMessages.CONNECTING_TO_SERVER_MESSAGE);

				final IDatabase database =
						RemoteDatabaseConnector.getConnection(hostname,
								Integer.valueOf(port));

				ControllerLogger.info(CLASS_NAME, methodName, 
						"Connected to server (" + hostname + ")");

				connectToServerWindow.closeWindow();

				final ClientWindow client = new ClientWindow(database);
				
				final String statusMessage = GUIUtils.formatMessage(
						GUIMessages.CONNECTED_TO_SERVER_MESSAGE, hostname);
				
				client.setStatusLabelText(statusMessage);

			}

		} catch (RemoteException e) {

			ControllerLogger.severe(CLASS_NAME, methodName, 
					"Can't connect to server: " + e.getMessage());

			GUIUtils.showErrorMessageDialog(connectToServerWindow, 
					GUIMessages.UNABLE_TO_CONNECT_MESSAGE);

			connectToServerWindow.setStatusLabelText(
					GUIMessages.ERROR_CONNECT_STATUS_MESSAGE);

		} finally {
			ControllerLogger.exiting(CLASS_NAME, methodName);
		}
	}

	/**
	 * Updates the suncertify.properties file with user inputs.
	 * 
	 * @param hostname Hostname value entered by user.
	 * @param port Port value entered by user.
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

			if (!GUIUtils.isEmptyValue(hostname)) {

				GUIUtils.showWarningMessage(connectToServerWindow,
						GUIMessages.INVALID_HOSTNAME_MESSAGE);

				return false;

			}
			
			if (!GUIUtils.isPortNumberValid(port)) {

				GUIUtils.showWarningMessage(connectToServerWindow,
						GUIMessages.INVALID_PORT_NUMBER_MESSAGE);

				return false;

			}

			return true;

		} finally {

			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}
		
	}
	

}

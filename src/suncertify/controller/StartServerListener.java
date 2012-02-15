package suncertify.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.text.MessageFormat;

import javax.swing.JOptionPane;

import suncertify.gui.AbstractServerWindow;
import suncertify.gui.GUIConstants;
import suncertify.gui.GUIMessages;
import suncertify.gui.GUIUtils;
import suncertify.remote.RegisterDatabase;

/**
 * Provides the functionality when user clicks on 'Start server' button on 
 * the Server Window. 
 * <br />Starts the server if the input user data is valid.
 * 
 * @author Leo Gutierrez
 */
public class StartServerListener implements ActionListener {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = 
			StartServerListener.class.getName();
	
	/**
	 * Reference to the Server window frame.
	 */
	private AbstractServerWindow serverWindow;
	
	/**
	 * Constructs a <code>StartServerListener</code> object.
	 * 
	 * @param abstractServerWindow Reference of the Server window frame.
	 */
	public StartServerListener(
			final AbstractServerWindow abstractServerWindow) {

		serverWindow = abstractServerWindow;

	}
	
	/**
	 * Invoked when user clicks on Start Server button.
	 *
	 * @param actionEvent The action event.
	 */
	public void actionPerformed(final ActionEvent actionEvent) {

		final String methodName = "actionPerformed";
		ControllerLogger.entering(CLASS_NAME, methodName);

		try {

			if (serverWindow == null) {

				throw new RemoteException("A reference to the Server window "
						+ "does not exist");

			}

			startServer();

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

	/**
	 * Starts the server doing the following:
	 * <br />- Validates user inputs.
	 * <br />- Updates the properties file with the user input.
	 * <br />- Starts the server.
	 * <br />- Disable any editable option in the server window.
	 * 
	 * @throws RemoteException If any networking problem occurs.
	 */
	private void startServer() throws RemoteException {
		
		final String methodName = "startServer";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		try {
		
			if (isValidUserInput(true)) {

				updatePropertiesWithUserInput(true);

				RegisterDatabase.bind();

				disableComponentsOnStart();

				updateStatusSuccessfulStart();
			}

		} catch (RemoteException e) {
			
			ControllerLogger.severe(CLASS_NAME, methodName, 
					"Unable to start the server due to remote exception: " + 
							e.getMessage());
			
			GUIUtils.showErrorMessageDialog(serverWindow, 
					GUIMessages.UNABLE_TO_START_SERVER_MESSAGE);
			
		} finally {
			
			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}
	}
	
	/**
	 * Updates the server windows status that the server is running.
	 */
	private void updateStatusSuccessfulStart() {
		
		final String methodName = "updateServerStatus";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		final String serverHostName = retrieveServerHostName();
		
		serverWindow.setStatusLabelText(
				MessageFormat.format(GUIMessages.SERVER_RUNNING_STATUS_MESSAGE, 
						serverHostName));
		
		ControllerLogger.info(CLASS_NAME, methodName, 
				"Server is running on host " + serverHostName);
		
		ControllerLogger.exiting(CLASS_NAME, methodName);
		
		
	}
	
	/**
	 * Retrieves the host name where the server is running. If server is 
	 * unable to read the host name, a message is displayed to user to let
	 * him/her know about this.
	 * 
	 * @return Host name as <code>String</code>.
	 */
	private String retrieveServerHostName() {
		
		final String methodName = "retrieveServerHostName";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		String serverIPAddress = GUIMessages.UNKNOWN_HOST;
		
		try {
			
			serverIPAddress = InetAddress.getLocalHost().getHostName();
			
		} catch (UnknownHostException e) {
			
			ControllerLogger.warning(CLASS_NAME, methodName, 
					"Can't retrieve server's host name: " + e.getMessage());
			
			GUIUtils.showWarningMessage(serverWindow, 
					GUIMessages.CANT_RETRIEVE_SERVER_HOST_NAME);
			
		}
		
		ControllerLogger.exiting(CLASS_NAME, methodName, serverIPAddress);
		
		return serverIPAddress;
		
	}
	
	/**
	 * Verifies if the database location and port number provided by the user
	 * are valid or not.
	 * <br />If either the database location or the port number is not valid,
	 * a warning message is shown to the user.
	 *
	 * @param serverWindowInput Flag that indicates if the user input comes
	 *                          comes from Connect To Server window.
	 *                          <code>True</code> if yes; <code>False</code> 
	 *                          otherwise.
	 *
	 * @return True if the database location and port number provided by the
	 *         user are valid; False otherwise.
	 */
	protected final boolean isValidUserInput(final boolean serverWindowInput) {

		final String methodName = "isValidUserInput";
		ControllerLogger.entering(CLASS_NAME, methodName);

		try {

			final String databasePath = 
					serverWindow.getServerLocationFieldText();

			if (!isValidDatabasePath(databasePath)) {

				GUIUtils.showWarningMessage(serverWindow, 
						GUIMessages.INVALID_DATABASE_MESSAGE);

				return false;
			}

			if (!isDatabaseFileEditable(databasePath)) {

				GUIUtils.showWarningMessage(serverWindow, 
						GUIMessages.DATABASE_NOT_EDITABLE_MESSAGE);
				
				return false;

			}

			if ((serverWindowInput)
					&& (!GUIUtils.isPortNumberValid(
							serverWindow.getPortNumberFieldText()))) {

				GUIUtils.showWarningMessage(serverWindow, 
						GUIMessages.INVALID_PORT_NUMBER_MESSAGE);
				
				return false;

			}

			return true;
		
		} finally {
			
			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}
	}
	
	/**
	 * Verifies if the database file is readable and writable.
	 *
	 * @param databasePath Database file path to verify.
	 * @return True if the database file has the proper permissions (+rw);
	 *         False, otherwise.
	 */
	private boolean isDatabaseFileEditable(final String databasePath) {

		final String methodName = "isDatabaseFileEditable";
		ControllerLogger.entering(CLASS_NAME, methodName, databasePath);

		boolean validDatabase = true; 
		
		try {

			final File databaseFile = new File(databasePath);

			if (!databaseFile.canRead()) {

				ControllerLogger.warning(CLASS_NAME, methodName,
						"Database file " + databasePath + " is not readable");

				validDatabase = false;

			}

			if (!databaseFile.canWrite()) {

				ControllerLogger.warning(CLASS_NAME, methodName,
						"Database file " + databasePath + " is not writable");

				validDatabase = false;
				
			}
			
			return validDatabase;
		
		} finally {
			
			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}
	}
	
	/**
	 * Verifies if the database location entered by the user is valid. 
	 * Verifies if it exists, is a file and has the .db file extension.
	 * 
	 * @param databasePath Database location to verify.
	 * @return True if it is a valid database path; False otherwise.
	 */
	private boolean isValidDatabasePath(final String databasePath) {

		final String methodName = "isValidDatabasePath";
		ControllerLogger.entering(CLASS_NAME, methodName, databasePath);
		
		try {
		
			if ((databasePath == null) 
					|| ("".equals(databasePath.trim()))) {

				ControllerLogger.warning(CLASS_NAME, methodName,
						"Database file path empty");

				return false;

			}

			final File databaseFile = new File(databasePath);

			if ((databaseFile.exists()) && (databaseFile.isFile())) {

				return databaseFile.getName().endsWith(
						GUIConstants.DATABASE_FILE_EXTENSION);

			} else {

				ControllerLogger.warning(CLASS_NAME, methodName,
						"Database file path is not a file or a non-existing file: "
								+ databasePath);
				
				return false;

			}

		} finally {
			
			ControllerLogger.exiting(CLASS_NAME, methodName);
		}
		
	}
	
	/**
	 * Disable any editable component on server window once the server started
	 * successfully.
	 */
	private void disableComponentsOnStart() {
		
		final String methodName = "disableComponentsOnStart";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		serverWindow.setEnabledPrimaryServerButton(false);
		serverWindow.setEnabledServerLocationField(false);
		serverWindow.setEnabledPortNumberField(false);
		serverWindow.setEnabledBrowseButton(false);

		ControllerLogger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * Updates the properties file with the user input.
	 * 
	 * @param serverWindowInput Flag that indicates if the user input comes
	 *                          comes from Connect To Server window.
	 *                          <code>True</code> if yes; <code>False</code> 
	 *                          otherwise. 
	 */
	protected final void updatePropertiesWithUserInput(
			final boolean serverWindowInput) {

		final String methodName = "updatePropertiesWithUserInput";
		ControllerLogger.entering(CLASS_NAME, methodName);

		serverWindow.updateDatabasePath(
				serverWindow.getServerLocationFieldText());
		
		if (serverWindowInput) {
		
			serverWindow.updateRMIPort(serverWindow.getPortNumberFieldText());

		}
		
		ControllerLogger.exiting(CLASS_NAME, methodName);
	}

}

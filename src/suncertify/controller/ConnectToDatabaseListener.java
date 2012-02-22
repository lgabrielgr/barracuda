package suncertify.controller;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;

import suncertify.db.Database;
import suncertify.gui.AbstractServerWindow;
import suncertify.gui.GUIMessages;
import suncertify.gui.ClientWindow;
import suncertify.gui.GUIUtils;

/**
 * Provides the functionality when user wants to connect to the database 
 * in non-networking mode. Verifies the database file provided by the users, 
 * and starts the Client window in a non-networking mode.
 * <br />The user is able to connect to the database only if user's database
 * location input is complete and valid. 
 * 
 * @author Leo Gutierrez
 */
public class ConnectToDatabaseListener extends StartServerListener {

	/**
	 * Reference to the Server window frame.
	 */
	private AbstractServerWindow connectToDbWindow;
	
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
		
		connectToDbWindow = abstractServerWindow;
		
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

			if (connectToDbWindow == null) {

				throw new RemoteException("A reference to the Server window "
						+ "does not exist");

			}

			if (isValidUserInput(false)) {
			
				updatePropertiesWithUserInput(false);
				
				connectToDbWindow.closeWindow();
				
				new ClientWindow(new Database());
				
			}

		} catch (RemoteException e) {

			ControllerLogger.severe(CLASS_NAME, methodName,
					"Unable to start the server: " + e.getMessage());

			JOptionPane.showMessageDialog(connectToDbWindow,
                    GUIMessages.UNABLE_TO_START_SERVER_MESSAGE,
                    GUIMessages.ERROR_TEXT,
                    JOptionPane.ERROR_MESSAGE);
			
		} 
		
		ControllerLogger.exiting(CLASS_NAME, methodName);
		
	}
	
	/**
	 * Invoked when user updates text on database text field.
	 * 
	 * @param documentEvent The document event.
	 */
	public void insertUpdate(final DocumentEvent documentEvent) {
		verifyIfUserCanConnectToDb();
	}

	/**
	 * Invoked when user removes text on database text field.
	 * 
	 * @param documentEvent The document event.
	 */
	public void removeUpdate(final DocumentEvent documentEvent) {
		verifyIfUserCanConnectToDb();
	}

	/**
	 * Invoked when user changes text on database text field.
	 * 
	 * @param documentEvent The document event.
	 */
	public void changedUpdate(final DocumentEvent documentEvent) {
		verifyIfUserCanConnectToDb();
	}
	
	/**
	 * Verifies if user can connect to the database. Verifies the text field
	 * for the database location, if it is empty, the button to connect to the 
	 * database is disabled, if not the button is enable and user can connect
	 * to the database. 
	 */
	private void verifyIfUserCanConnectToDb() {
		
		final String methodName = "verifyIfUserCanConnectToDb";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		final String dbText = connectToDbWindow.getDBServerLocationFieldText();
		
		if (GUIUtils.isEmptyValue(dbText)) {
			
			connectToDbWindow.setEnabledPrimaryServerButton(false);
			
		} else {
			
			connectToDbWindow.setEnabledPrimaryServerButton(true);
			
		}
		
		ControllerLogger.exiting(CLASS_NAME, methodName);
		
	}
	
}

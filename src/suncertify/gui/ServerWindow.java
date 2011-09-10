package suncertify.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import suncertify.db.DatabaseProperties;
import suncertify.remote.RemoteProperties;

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
	 * Reference to the properties to access to the database.
	 */
	private final DatabaseProperties dbProperties = new DatabaseProperties();
	
	/**
	 * Reference to the properties to start/connect to server.
	 */
	private final RemoteProperties remoteProperties = new RemoteProperties();
	
	public ServerWindow() {
		init();
	}
	
	private void init() {
		
		final String methodName = "init";
		GUILogger.entering(CLASS_NAME, methodName);
		
		setDBServerLabelText(GUIConstants.DATABASE_LOCATION_LABEL);
		
		setDBServerFieldText(dbProperties.readDatabasePath());
		
		setPortText(remoteProperties.readRMIPort());
		
		setStatusLabelText(GUIConstants.INITIAL_SERVER_STATUS);
		
		setPrimaryButtonText(GUIConstants.START_SERVER_TEXT);
		
		setSecondaryButtonText(GUIConstants.STOP_SERVER_TEXT);
		
		//enableSecondaryButton(false);
		
		displayServerWindow();
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
	}

	private void displayServerWindow() {
		pack();
        GUIUtils.centerOnScreen(this);
        setVisible(true);
	}
	
	public static void main(String [] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException uex) {
			System.out.println("Unsupported look and feel specified");
		} catch (ClassNotFoundException cex) {
			System.out.println("Look and feel could not be located");
		} catch (InstantiationException iex) {
			System.out.println("Look and feel could not be instanciated");
		} catch (IllegalAccessException iaex) {
			System.out.println("Look and feel cannot be used on this platform");
		}

		new ServerWindow();
		
	}
}

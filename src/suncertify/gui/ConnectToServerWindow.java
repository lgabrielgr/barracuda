package suncertify.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
	protected void addProperTextOnComponents() {
		
		final String methodName = "addProperTextOnComponents";
		GUILogger.entering(CLASS_NAME, methodName);
		
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

		new ConnectToServerWindow();
//		ServerWindow.getInstance().displayWindow();
		
	}
}

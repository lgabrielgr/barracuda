package suncertify.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
	 * Constructs a <code>ServerWindow</code> and displays the Server window.
	 */
	public ServerWindow() {
		
		displayWindow();
		
	}
	
	/**
	 * Adds the proper text to display in the different frame's components.
	 */
	protected void addProperTextOnComponents() {
		
		final String methodName = "addProperTextOnComponents";
		GUILogger.entering(CLASS_NAME, methodName);
		
		setServerLocationLabelText(GUIMessages.DATABASE_LOCATION_LABEL_TEXT);
		
		setServerLocationFieldText(readDatabasePath());
		
		setPortNumberFieldText(readRMIPort());
		
		setStatusLabelText(GUIMessages.INITIAL_SERVER_STATUS_TEXT);
		
		setPrimaryServerButtonText(GUIMessages.START_SERVER_TEXT);
		addListenerToPrimaryServerButton(new StartServerListener(this));
		
		setSecondaryServerButtonText(GUIMessages.EXIT_TEXT);
		addListenerToSecondaryServerButton(new ExitServerListener());
		
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
//		ServerWindow.getInstance().displayWindow();
		
	}
}

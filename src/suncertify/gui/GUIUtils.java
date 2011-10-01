package suncertify.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import suncertify.remote.RegisterDatabase;

/**
 * Contains utilities methods for the packages <code>suncertify.gui</code> and
 * <code>suncertify.controller</code>.
 * 
 * @author Leo Gutierrez
 */
public class GUIUtils {

	/**
	 * Class name.
	 */
	private final static String CLASS_NAME = GUIUtils.class.getName();
	
	/**
     * Centers on screen the specified frame.
     * 
     * @param frame Frame to center on screen.
     */
    public static void centerOnScreen(final JFrame frame) {
        
    	final String sourceMethod = "centerFrame";
        GUILogger.entering(CLASS_NAME, sourceMethod);

        final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        final int positionX =
            (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        final int positionY =
            (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(positionX, positionY);

        GUILogger.exiting(CLASS_NAME, sourceMethod);
    }
    
    /**
     * Displays a dialog message to the user to exit a window.
     * 
     * @return An integer indicating the option selected by the user
     */
    public static int askUserToExit() {
    	
    	final String methodName = "askUserToExit";
    	GUILogger.entering(CLASS_NAME, methodName);
    	
    	final int userSelection = JOptionPane.showConfirmDialog(null, 
    			GUIMessages.EXIT_MESSAGE_DIALOG_TEXT, 
    			GUIMessages.EXIT_MESSAGE_TITLE_TEXT, 
    			JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    
    	GUILogger.exiting(CLASS_NAME, methodName, userSelection);
    	
    	return userSelection;
    
    }
	
    /**
     * Asks to user if really want to quit the Server window.
     * <br />If user confirm the quit, the server is unbind and the window is
     * closed.
     */
    public static void exitServerWindow() {
    
    	final String methodName = "exitServerWindow";
    	GUILogger.entering(CLASS_NAME, methodName);
    	
    	try {
    		
    		final int userSelection = askUserToExit();

    		if (userSelection == JOptionPane.OK_OPTION) {

    			RegisterDatabase.unbind();

    			System.exit(0);

    		}
    		
    	} finally{
    		
    		GUILogger.exiting(CLASS_NAME, methodName);
    		
    	}
    	
    }
    
    /**
	 * Verifies if the port number entered by the user is valid or not. 
	 * The port number must be a non-zero positive number.
	 * 
	 * @param port Port number to verify.
	 * @return True if it is a valid port number; False otherwise.
	 */
	public static boolean isPortNumberValid(final String port) {
		
		final String methodName = "isPortNumberValid";
		GUILogger.entering(CLASS_NAME, methodName, port);
		
		try {

			if ((port == null) || ("".equals(port.trim()))) {

				GUILogger.warning(CLASS_NAME, methodName, 
						"Port number empty");
				
				return false;

			}

			try {

				final int portNumber = Integer.valueOf(port);
				
				if (portNumber > 0) {
					
					return true;
					
				} else {
					
					GUILogger.warning(CLASS_NAME, methodName, 
							"Port number invalid: " + portNumber);
					
					return false;
				}

			} catch (NumberFormatException e) {
				
				GUILogger.warning(CLASS_NAME, methodName, 
						"Port number invalid: " + e.getMessage());
				
				return false;
			}

		} finally {
			
			GUILogger.exiting(CLASS_NAME, methodName);
			
		}
	}
	
}

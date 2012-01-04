package suncertify.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.MessageFormat;

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
	private static final String CLASS_NAME = GUIUtils.class.getName();
	
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
    	
    	final int userSelection = showConfirmDialog(
    			GUIMessages.EXIT_MESSAGE_DIALOG_TEXT, 
    			GUIMessages.EXIT_MESSAGE_TITLE_TEXT); 
    
    	GUILogger.exiting(CLASS_NAME, methodName, userSelection);
    	
    	return userSelection;
    
    }
	
    /**
     * Shows to user a "Yes or No" question, displaying the specified title 
     * and question.
     * 
     * @param title Title to display in dialog.
     * @param question Question to display in dialog.
     * @return User's response. If user selects 'Yes', returns 
     *         {@link JOptionPane.OK_OPTION}; Otherwise,
     *         if user selects 'No', returns {@link JOptionPane.OK_CANCEL_OPTION}.
     */
    public static int showConfirmDialog(final String question, 
    		final String title) {
    	
    	final int userSelection = JOptionPane.showConfirmDialog(null, question, 
    			title, JOptionPane.YES_NO_OPTION, 
    			JOptionPane.QUESTION_MESSAGE);
    	
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
    		
    	} finally {
    		
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
	
	/**
	 * Shows an error message dialog to the user over the component specified 
	 * and displaying the specified error message.
	 * 
	 * @param component Component to which display the message over.
	 * @param errorMessage Error message to print.
	 */
	public static void showErrorMessageDialog(final Component component, 
			final String errorMessage) {
				
		JOptionPane.showMessageDialog(component, errorMessage,
				GUIMessages.ERROR_TEXT, JOptionPane.ERROR_MESSAGE);

	}
	
	/**
	 * Shows a warning message dialog to the user over the component specified 
	 * and displaying the specified warning message.
	 * 
	 * @param component Component to which display the message over.
	 * @param warningMessage Warning message to print.
	 */
	public static void showWarningMessage(final Component component, 
			final String warningMessage) {
				
		JOptionPane.showMessageDialog(component, warningMessage,
				GUIMessages.WARNING_TEXT, JOptionPane.WARNING_MESSAGE);

	}
	
	/**
	 * Formats the given message with the given arguments.
	 * 
	 * @param message Message to format.
	 * @param arguments Argument to use in message.
	 * @return Message formatted with arguments specified.
	 */
	public static String formatMessage(final String message, final Object ... arguments) {
		return MessageFormat.format(message, arguments);
	}
}

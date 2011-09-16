package suncertify.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import suncertify.remote.RegisterDatabase;

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

    		if (userSelection == GUIConstants.EXIT_OPERATION) {

    			RegisterDatabase.unbind();

    			System.exit(0);

    		}
    		
    	} finally{
    		
    		GUILogger.exiting(CLASS_NAME, methodName);
    		
    	}
    	
    }
}

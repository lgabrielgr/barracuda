package suncertify.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
     * Displays a dialog message on to exit the application.
     */
    public static void windowClosing() {
    	
    	final String methodName = "windowClosing";
    	GUILogger.entering(CLASS_NAME, methodName);
    	
    	final int userSelection = JOptionPane.showConfirmDialog(null, 
    			GUIConstants.EXIT_MESSAGE_DIALOG, 
    			GUIConstants.EXIT_MESSAGE_TITLE, 
    			JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    	
    	if (userSelection == GUIConstants.EXIT_OPERATION) {
    		
    		System.exit(0);
    	}
    	
    	GUILogger.exiting(CLASS_NAME, methodName);
    }
	
}

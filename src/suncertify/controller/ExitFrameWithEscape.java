package suncertify.controller;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import suncertify.gui.GUIUtils;

/**
 * Provides the capability user press 'Escape' to exit the Frame.
 * This class should be added as <code>Action</code> to the Root Pane
 * to exit the Frame.
 * 
 * @author Leo Gutierrez
 */
public class ExitFrameWithEscape extends AbstractAction {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -2845368947609265845L;

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = 
			ExitFrameWithEscape.class.getName();
	
	/**
	 * Reference to the window to close.
	 */
	private Window windowToExit = null;
	
	/**
	 * Flag reference to know if need to ask to user first or not before close.
	 */
	private boolean askUserToExit = false;
	
	/**
	 * Constructs a <code>ExitFrameWithEscape</code> object.
	 * 
	 * @param window <code>Window</code> to close.
	 * @param askUser <code>True</code> if want to ask user first or not 
	 *                before close.
	 */
	public ExitFrameWithEscape(final Window window, 
			final boolean askUser) {
		
		windowToExit = window;
		
		askUserToExit = askUser;
		
	}
	
	/**
	 * Invoked when user press 'Escape' to exit the frame.
	 * 
	 * @param actionEvent The action event.
	 */
	public void actionPerformed(final ActionEvent actionEvent) {
		
		final String methodName = "actionPerformed";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			int userSelection = JOptionPane.OK_OPTION;
			
			if (askUserToExit) {
			
				userSelection = GUIUtils.askUserToExit();
				
			}

			if (userSelection == JOptionPane.OK_OPTION) {
				
				windowToExit.setVisible(false);
				
			}

		} finally {
			ControllerLogger.exiting(CLASS_NAME, methodName);
		}
		
	}

}

package suncertify.controller;

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
	 * Invoked when user press 'Escape' to exit the frame.
	 * 
	 * @param actionEvent The action event.
	 */
	public void actionPerformed(final ActionEvent actionEvent) {
		
		final String methodName = "actionPerformed";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			final int userSelection = GUIUtils.askUserToExit();

			if (userSelection == JOptionPane.OK_OPTION) {
				System.exit(0);
			}

		} finally {
			ControllerLogger.exiting(CLASS_NAME, methodName);
		}
		
	}

}

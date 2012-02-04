package suncertify.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import suncertify.gui.GUIUtils;

/**
 * Provides the functionality when user wants to quit the StandAlone window.
 * 
 * @author Leo Gutierrez
 */
public class ExitStandAloneWindow extends WindowAdapter {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = 
			ExitStandAloneWindow.class.getName();
	
	/**
	 * Invoked when a window is in the process of being closed.
	 * 
	 * @param windowEvent The window event.
	 */
	public final void windowClosing(final WindowEvent windowEvent) {
		
		final String methodName = "windowClosing";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		try {

			if (JOptionPane.OK_OPTION == GUIUtils.askUserToExit()) {
				System.exit(0);
			}
		
		} finally {
			
			ControllerLogger.info(CLASS_NAME, methodName, 
					"User quits client window");
			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}
	}
	
}

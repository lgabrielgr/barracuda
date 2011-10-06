package suncertify.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import suncertify.gui.GUIUtils;

/**
 * Provides the functionality when user wants to quit the Server window whether
 * by clicking the Exit button or by closing the window.
 * <br />If user confirm the exit, the server is unbind and the window is 
 * closed.
 * 
 * @author Leo Gutierrez
 */
public class ExitServerListener 
	extends WindowAdapter implements ActionListener {

	/**
	 * Class name.
	 */
	static final private String CLASS_NAME = 
			ExitServerListener.class.getName();
	
	/**
	 * Invoked when a window is in the process of being closed.
	 * 
	 * @param windowEvent The window event.
	 */
	public void windowClosing(final WindowEvent windowEvent) {
		exitServerWindow();
	}
	
	/**
	 * Invoked when user clicks on the Exit button on the Server window.
	 * <br />If the user confirm to quit the Server window, the server is 
	 * unbind.
	 * 
	 * @param actionEvent The action event.
	 */
	public void actionPerformed(final ActionEvent actionEvent) {
		exitServerWindow();
	}
	
	/**
	 * Asks to user if want to really quit the application. If yes, it closes
	 * the window.
	 */
	private void exitServerWindow() {
		
		final String methodName = "exitServerWindow";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			GUIUtils.exitServerWindow();
		
		} finally {
			
			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}
		
	}

}

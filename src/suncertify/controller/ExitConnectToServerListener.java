package suncertify.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import suncertify.gui.GUIUtils;

/**
 * Provides the functionality when user wants to quit the Connection to Server 
 * window whether by clicking the Exit button or by closing the window.
 * 
 * @author Leo Gutierrez
 */
public class ExitConnectToServerListener extends WindowAdapter implements
		ActionListener {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = 
			ExitConnectToServerListener.class.getName(); 
	
	
	/**
	 * Invoked when a window is in the process of being closed.
	 * 
	 * @param windowEvent The window event.
	 */
	public void windowClosing(final WindowEvent windowEvent) {
		
		final String methodName = "windowClosing";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			if (GUIUtils.askUserToExit() == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		
		} finally {
			
			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}
		
		
	}
	
	/**
	 * Invoked when the user clicks on the Exit button on the connect to server
	 * window.
	 * 
	 * @param actionEvent The action event.
	 */
	public void actionPerformed(final ActionEvent actionEvent) {
		
		final String methodName = "actionPerformed";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			if (GUIUtils.askUserToExit() == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		
		} finally {
			
			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}

	}

}

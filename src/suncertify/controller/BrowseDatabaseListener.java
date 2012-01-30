package suncertify.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import suncertify.gui.AbstractServerWindow;

/**
 * Provides the functionality when user click on the browse button.
 * <br />Lets the user to select the database file (*.db).
 * 
 * @author Leo Gutierrez
 */
public class BrowseDatabaseListener implements ActionListener {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = 
			BrowseDatabaseListener.class.getName(); 
	
	/**
	 * Reference to the Server window.
	 */
	private AbstractServerWindow serverWindow;
	
	/**
	 * Constructs a <code>BrowseDatabaseListener</code> object.
	 * 
	 * @param abstractServerWindow The Server window frame.
	 */
	public BrowseDatabaseListener(
			final AbstractServerWindow abstractServerWindow) {
		serverWindow = abstractServerWindow;
	}
	
	/**
	 * Invoked when the user clicks on the browse button.
	 * <br />Shows a file choose dialog that lets to user to select the
	 * database file.
	 * @param actionEvent Listener's action event reference.
	 */
	public final void actionPerformed(final ActionEvent actionEvent) {

		final String methodName = "BrowseDatabaseListener";
		ControllerLogger.entering(CLASS_NAME, methodName);

		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new DatabaseFileFilter());

		final int userSelection = fileChooser.showOpenDialog(serverWindow);

		if (JFileChooser.APPROVE_OPTION == userSelection) {

			serverWindow.setServerLocationFieldText(
					fileChooser.getSelectedFile().getAbsolutePath());

		}

		ControllerLogger.exiting(CLASS_NAME, methodName);
	}

}

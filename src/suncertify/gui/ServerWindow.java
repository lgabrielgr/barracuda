package suncertify.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import suncertify.remote.RegisterDatabase;

public class ServerWindow extends AbstractServerWindow {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 590350L;
	
	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = ServerWindow.class.getName();
	
	public ServerWindow() {
		displayWindow();
	}
	
	protected void addProperTextOnComponents() {
		
		final String methodName = "drawWindow";
		GUILogger.entering(CLASS_NAME, methodName);
		
		final JLabel dbServerLocationLaber = getDbServerLocationLabel();
		dbServerLocationLaber.setText(GUIConstants.DATABASE_LOCATION_LABEL);
		
		final JTextField dbServerLocationField = getDbServerLocationField();
		dbServerLocationField.setText(dbProperties.readDatabasePath());
		
		final JTextField portNumberField = getPortNumberField();
		portNumberField.setText(remoteProperties.readRMIPort());
		
		final JLabel statusLabel = getStatusLabel();
		statusLabel.setText(GUIConstants.INITIAL_SERVER_STATUS);
		
		final JButton startServerButton = getPrimaryServerButton();
		startServerButton.setText(GUIConstants.START_SERVER_TEXT);
		startServerButton.addActionListener(new StartServerListener());
		
		final JButton exitServerButton = getSecondaryServerButton();
		exitServerButton.setText(GUIConstants.EXIT_TEXT);
		exitServerButton.addActionListener(new ExitServerListener());
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
	}

	private void displayWindow() {
		pack();
        GUIUtils.centerOnScreen(this);
        setVisible(true);
	}
	
	private class StartServerListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent actionEvent) {
			
			try {
				
				RegisterDatabase.bind();
				
				getPrimaryServerButton().setEnabled(false);
				
				getStatusLabel().setText("Server is running.");
				
			} catch (RemoteException e) {
				
				System.out.println(e.getMessage());
				
			}
			
		}
		
	}
	
	public static void main(String [] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException uex) {
			System.out.println("Unsupported look and feel specified");
		} catch (ClassNotFoundException cex) {
			System.out.println("Look and feel could not be located");
		} catch (InstantiationException iex) {
			System.out.println("Look and feel could not be instanciated");
		} catch (IllegalAccessException iaex) {
			System.out.println("Look and feel cannot be used on this platform");
		}

		new ServerWindow();
//		ServerWindow.getInstance().displayWindow();
		
	}
}

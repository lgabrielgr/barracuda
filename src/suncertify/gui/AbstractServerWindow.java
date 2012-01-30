package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import suncertify.controller.BrowseDatabaseListener;
import suncertify.db.DatabaseProperties;
import suncertify.remote.RemoteProperties;

/**
 * Provides the abstract graphical user interface for the Server window,
 * drawing the components and delegating to any class that extends this  
 * to set the proper messages to the components.
 * 
 * @author Leo Gutierrez
 */
public abstract class AbstractServerWindow extends JFrame {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 90350L;
	
	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = 
			AbstractServerWindow.class.getName();
	
	/**
	 * Reference to the properties to access to the database.
	 */
	private final DatabaseProperties dbProperties = new DatabaseProperties();
	
	/**
	 * Reference to the properties to start/connect to server.
	 */
	private final RemoteProperties remoteProperties = new RemoteProperties();
	
	/**
	 * Reference to the database location server label.
	 */
	private final JLabel serverLocationLabel = new JLabel();
	
	/**
	 * Reference to the database location server field.
	 */
	private final JTextField serverLocationTextField = new JTextField(40);
	
	/**
	 * Reference to the port text field.
	 */
	private final JTextField portNumberTextField = new JTextField(5);
	
	/**
	 * Reference to the status label.
	 */
	private final JLabel statusLabel = new JLabel();
	
	/**
	 * Reference to the primary button on the server frame.
	 */
	private final JButton primaryServerButton = new JButton();
	
	/**
	 * Reference to the secondary button on the server frame.
	 */
    private final JButton secondaryServerButton = new JButton();
	
    /**
     * Reference to the browse button.
     */
    private final JButton browseDatabase = 
    		new JButton(GUIConstants.BROWSE_BUTTON_SIMPLE_TEXT);
    
    /**
     * Reference to the database/server configuration panel.
     */
    private final JPanel dbConfigPanel = new JPanel();
	
    /**
     * Reference to the grid bag layout to use into the database/server 
     * configuration panel.
     */
    private final GridBagLayout gridbag = new GridBagLayout();
    
    /**
     * Reference to the grid bag constraints to use into the database/server 
     * configuration panel. 
     */
    private final GridBagConstraints constraints = new GridBagConstraints();
    
	/**
	 * Constructs the Server's frame to display to user.
	 */
	public AbstractServerWindow() {
		init();
	}

	/**
	 * Initializes the Server GUI's components.
	 */
	private void init() {
		
		final String methodName = "init";
		GUILogger.entering(CLASS_NAME, methodName);
		
		addFrameConfiguration();
		
        addServerConfigSection();
        
        addButtonsSection();
        
        addStatusSection();
        
        addProperTextOnComponents();
        
        GUILogger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * Sets the Server GUI's frame general configuration.
	 */
	private void addFrameConfiguration() {
		
		final String methodName = "setFrameConfiguration";
		GUILogger.entering(CLASS_NAME, methodName);
		
		setTitle(GUIMessages.SERVER_TITLE_TEXT);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
		setResizable(false);
        
        GUILogger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * Adds the status panel to the server frame.
	 */
	private void addStatusSection() {
		
		final String methodName = "addStatusSection";
		GUILogger.entering(CLASS_NAME, methodName);

        statusLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        
        final JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        
        GUILogger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * Adds the panel which contains the buttons to the server frame.
	 */
	private void addButtonsSection() {
		
		final String methodName = "addButtonsSection";
		GUILogger.entering(CLASS_NAME, methodName);
		
		final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        buttonsPanel.add(primaryServerButton);
        buttonsPanel.add(secondaryServerButton);
        
        add(buttonsPanel, BorderLayout.CENTER);
        
        GUILogger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * Adds the panel which contains the database location and port in the 
	 * server frame. 
	 */
	private void addServerConfigSection() {
		
		final String methodName = "addServerConfigSection";
		GUILogger.entering(CLASS_NAME, methodName);
		
        dbConfigPanel.setLayout(gridbag);
     
        constraints.insets = new Insets(2, 2, 2, 2);

        gridbag.setConstraints(serverLocationLabel, constraints);
        dbConfigPanel.add(serverLocationLabel);
        
        constraints.gridwidth = GridBagConstraints.RELATIVE; 
        
        gridbag.setConstraints(serverLocationTextField, constraints);
        dbConfigPanel.add(serverLocationTextField);
        
        browseDatabase.addActionListener(new BrowseDatabaseListener(this));
        
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        
        gridbag.setConstraints(browseDatabase, constraints);
        dbConfigPanel.add(browseDatabase);
        
        constraints.weightx = 0.0;
        
        final JLabel serverPortLabel = 
        		new JLabel(GUIMessages.SERVER_PORT_LABEL_TEXT);
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.EAST;
        gridbag.setConstraints(serverPortLabel, constraints);
        dbConfigPanel.add(serverPortLabel);
        
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(portNumberTextField, constraints);
        dbConfigPanel.add(portNumberTextField);
        
        add(dbConfigPanel, BorderLayout.NORTH);
        
        GUILogger.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * Reads the database path from properties file.
	 * 
	 * @return The database path.
	 */
	public final String readDatabasePath() {
		return dbProperties.readDatabasePath();
	}
	
	/**
	 * Updates the database path in the properties file according what user 
	 * enters as the database location.
	 * 
	 * @param dbPath Database path.
	 */
	public final void updateDatabasePath(final String dbPath) {
		dbProperties.updateDatabasePath(dbPath);
	}
	
	/**
	 * Reads the RMI port from the properties file.
	 * 
	 * @return The RMI port.
	 */
	public final String readRMIPort() {
		return remoteProperties.readRMIPort();
	}
	
	/**
	 * Updates the RMI port in the properties file according what user enters
	 * as RMI port.
	 * 
	 * @param rmiPort The RMI port.
	 */
	public final void updateRMIPort(final String rmiPort) {
		remoteProperties.updateRMIPort(rmiPort);
	}
	
	/**
	 * Reads the RMI host from the properties file.
	 * 
	 * @return The RMI port.
	 */
	public final String readRMIHost() {
		return remoteProperties.readRMIHost();
	}
	
	/**
	 * Updates the RMI host in the properties file according what user enters
	 * as RMI host.
	 * 
	 * @param rmiHost The RMI host.
	 */
	public final void updateRMIHost(final String rmiHost) {
		remoteProperties.updateRMIHost(rmiHost);
	}
	
	/**
	 * Sets the text to display in the server location label. Indicates to the 
	 * user to points whether to the database location or the server address.
	 * 
	 * @param text Text to set in the server location label.
	 */
	public final void setServerLocationLabelText(final String text) {
		serverLocationLabel.setText(text);
	}
	
	/**
	 * Sets the text to display in the server location field.
	 * 
	 * @param text Text to set in the server location field.
	 */
	public final void setServerLocationFieldText(final String text) {
		serverLocationTextField.setText(text);
	}
	
	/**
	 * Retrieves the text introduced in the server location field.
	 * 
	 * @return The text introduced in the server location field.
	 */
	public final String getServerLocationFieldText() {
		return serverLocationTextField.getText();
	}
	
	/**
	 * Sets the text to display in the port number field.
	 * 
	 * @param text The text to display in the port number field.
	 */
	public final void setPortNumberFieldText(final String text) {
		portNumberTextField.setText(text);
	}
	
	/**
	 * Retrieves the text introduced in the port number field.
	 * 
	 * @return The text introduced in the port number field.
	 */
	public final String getPortNumberFieldText() {
		return portNumberTextField.getText();
	}
	
	/**
	 * Sets the text to display in the window's status label.
	 * 
	 * @param text The text to display in the window's status label.
	 */
	public final void setStatusLabelText(final String text) {
		statusLabel.setText(text);
	}

	/**
	 * Sets the text to display in the window's primary button.
	 * 
	 * @param text The text to display in the window's primary button.
	 */
	public final void setPrimaryServerButtonText(final String text) {
		primaryServerButton.setText(text);
	}
	
	/**
	 * Sets enabled or not enabled the window's primary button.
	 * 
	 * @param enable True to enable; False otherwise.
	 */
	public final void setEnabledPrimaryServerButton(final boolean enable) {
		primaryServerButton.setEnabled(enable);
	}
	
	/**
	 * Adds the specified listener to the window's primary button.
	 * 
	 * @param listener Listener to add to the window's primary button.
	 */
	public final void addListenerToPrimaryServerButton(
			final ActionListener listener) {
		primaryServerButton.addActionListener(listener);
	}
	
	/**
	 * Sets the text to display in the window's secondary button.
	 * 
	 * @param text The text to display in the window's secondary button.
	 */
	public final void setSecondaryServerButtonText(final String text) {
		secondaryServerButton.setText(text);
	}
	
	/**
	 * Adds the specified listener to the window's secondary button.
	 * 
	 * @param listener Listener to add to the window's secondary button.
	 */
	public final void addListenerToSecondaryServerButton(
			final ActionListener listener) {
		secondaryServerButton.addActionListener(listener);
	}

	/**
	 * Sets enabled or not enabled the server location field.
	 * 
	 * @param enable True to enable; False otherwise.
	 */
	public final void setEnabledServerLocationField(final boolean enable) {
		serverLocationTextField.setEnabled(enable);
	}
	
	/**
	 * Sets enabled or not enabled the port number field.
	 * 
	 * @param enable True to enable; False otherwise.
	 */
	public final void setEnabledPortNumberField(final boolean enable) {
		portNumberTextField.setEnabled(enable);
	}
	
	/**
	 * Sets enabled or not enabled the browse database button.
	 * 
	 * @param enable True to enable; False, otherwise.
	 */
	public final void setEnabledBrowseButton(final boolean enable) {
		browseDatabase.setEnabled(enable);
	}
	
	/**
	 * Closes the frame.
	 */
	public final void closeWindow() {
		
		final String methodName = "dispose";
		GUILogger.entering(CLASS_NAME, methodName);
		
		setVisible(false);
		
		dispose();
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
	}
	
	/**
	 * Removes the button that browses the database file from the frame.
	 * <br />It is called when the user only needs to specify the server IP 
	 * address.
	 */
	protected final void removeBrowseButton() {
		
		final String methodName = "removeBrowseButton";
		GUILogger.entering(CLASS_NAME, methodName);
		
		dbConfigPanel.remove(browseDatabase);

		final GridBagConstraints serverLocationConstraint = 
				gridbag.getConstraints(serverLocationTextField);
		
		serverLocationConstraint.gridwidth = GridBagConstraints.REMAINDER;
		
		gridbag.setConstraints(serverLocationTextField, 
				serverLocationConstraint);
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
	}
	
	/**
	 * Adds the proper text to display in the different frame's components.
	 */
	protected abstract void addProperTextOnComponents();
	
}


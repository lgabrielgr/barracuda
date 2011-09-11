package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import suncertify.db.DatabaseProperties;
import suncertify.remote.RemoteProperties;

/**
 * Provides the graphical user interface to start/stop server.
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
	private static final String CLASS_NAME = AbstractServerWindow.class.getName();
	
	/**
	 * Reference to the properties to access to the database.
	 */
	protected final DatabaseProperties dbProperties = new DatabaseProperties();
	
	/**
	 * Reference to the properties to start/connect to server.
	 */
	protected final RemoteProperties remoteProperties = new RemoteProperties();
	
	/**
	 * Reference to the database location server label.
	 */
	private final JLabel dbServerLocationLabel = new JLabel();
	
	/**
	 * Reference to the database location server field.
	 */
	private final JTextField dbServerLocationField = new JTextField(40);
	
	/**
	 * Reference to the port text field.
	 */
	private final JTextField portNumberField = new JTextField(5);
	
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
	 * Constructs the Server's frame to display to user.
	 */
	public AbstractServerWindow() {
		init();
	}

	/**
	 * Initializes the Server GUI.
	 */
	private void init() {
		
		final String methodName = "init";
		GUILogger.entering(CLASS_NAME, methodName);
		
		setFrameConfiguration();
		
        addServerConfigSection();
        
        addButtonsSection();
        
        addStatusSection();
        
        addProperTextOnComponents();
        
        GUILogger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * Sets the Server GUI's frame general configuration.
	 */
	private void setFrameConfiguration() {
		
		final String methodName = "setFrameConfiguration";
		GUILogger.entering(CLASS_NAME, methodName);
		
		setTitle(GUIConstants.SERVER_TITLE);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			
			/**
			 * Invoked when a window is in the process of being closed.
			 */
			public void windowClosing(final WindowEvent e) {
				GUIUtils.windowClosing();
			}
			
		});
        
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
		
		final JPanel dbConfigPanel = new JPanel();
		
        final GridBagLayout gridbag = new GridBagLayout();
        dbConfigPanel.setLayout(gridbag);
        
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 2, 2, 2);

        gridbag.setConstraints(dbServerLocationLabel, constraints);
        dbConfigPanel.add(dbServerLocationLabel);
        
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        
        gridbag.setConstraints(dbServerLocationField, constraints);
        dbConfigPanel.add(dbServerLocationField);
        
        constraints.weightx = 0.0;
        
        final JLabel serverPortLabel = 
        		new JLabel(GUIConstants.SERVER_PORT_LABEL);
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.EAST;
        gridbag.setConstraints(serverPortLabel, constraints);
        dbConfigPanel.add(serverPortLabel);
        
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(portNumberField, constraints);
        dbConfigPanel.add(portNumberField);
        
        add(dbConfigPanel, BorderLayout.NORTH);
        
        GUILogger.exiting(CLASS_NAME, methodName);
	}
	
	protected JLabel getDbServerLocationLabel() {
		return dbServerLocationLabel;
	}

	protected JTextField getDbServerLocationField() {
		return dbServerLocationField;
	}

	protected JTextField getPortNumberField() {
		return portNumberField;
	}

	protected JLabel getStatusLabel() {
		return statusLabel;
	}

	protected JButton getPrimaryServerButton() {
		return primaryServerButton;
	}

	protected JButton getSecondaryServerButton() {
		return secondaryServerButton;
	}

	protected abstract void addProperTextOnComponents();
	
	protected class ExitServerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			GUIUtils.windowClosing();
		}
		
	}
	
}


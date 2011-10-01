package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;

import suncertify.controller.ExitMainWindow;
import suncertify.db.Database;
import suncertify.db.IDatabase;
import suncertify.db.Record;

/**
 * Provides the graphical user interface for the Client window.
 * 
 * @author Leo Gutierrez
 */
public class MainWindow extends JFrame {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 899029L;
	
	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = MainWindow.class.getName();
	
	/**
	 * Reference to the frame's main panel.
	 */
	private final JPanel mainPanel = new JPanel(new BorderLayout());
	
	/**
	 * Reference to the bottom panel of the main panel.
	 */
	private final JPanel bottomPanel = new JPanel(new BorderLayout());
	
	/**
	 * Reference to the table that displays the records.
	 */
	private final JTable mainTable = new JTable();
	
	/**
	 * Reference to the search's name field.
	 */
	private final JTextField nameField = new JTextField(10);
	
	/**
	 * Reference to the search's location field.
	 */
	private final JTextField locationField = new JTextField(10);
	
	/**
	 * Reference to the status label.
	 */
	private final JLabel statusLabel = new JLabel();
	
	/**
	 * Reference to the table model which will display data to user.
	 */
	private AbstractTableModel tableModel;
	
	/**
	 * Reference to the database.
	 */
	private IDatabase database;

	/**
	 * Constructs a <code>MainWindow</code> object and displays the Main
	 * window.
	 * 
	 * @param database Database from where perform the operations.
	 */
	public MainWindow(final IDatabase database) throws RuntimeException {
		
		this.database = database;
		
		init();
		
		displayWindow();
		
	}
	
	/**
	 * Initializes the Client GUI's components.
	 */
	private void init() {
		
		addFrameConfiguration();
		
		addDisplayDataSection();
		
		addBottomPanelSection();
		        
	}

	/**
	 * Builds and adds the bottom panel to the main panel.
	 */
	private void addBottomPanelSection() {
		
		final String methodName = "addBottomPanelSection";
		GUILogger.entering(CLASS_NAME, methodName);
		
		addSearchSection();
		
        addBookRoomSection();
        
        addStatusSection();
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        GUILogger.exiting(CLASS_NAME, methodName);
        
	}

	/**
	 * Builds and adds the status section to the bottom label.
	 */
	private void addStatusSection() {
		
		final String method = "addStatusSection";
		GUILogger.entering(CLASS_NAME, method);
		
		statusLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        final JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.CENTER);
        
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);
        
        GUILogger.exiting(CLASS_NAME, method);
	}

	/**
	 * Builds and adds book room section to the bottom panel.
	 */
	private void addBookRoomSection() {
		
		final String methodName = "addBookRoomSection";
		GUILogger.entering(CLASS_NAME, methodName);
		
		final JButton bookRoomButton = 
        		new JButton(GUIMessages.BOOK_ROOM_BUTTON_NAME);
        
        final JPanel bookRoomPanel = new JPanel(
        		new FlowLayout(FlowLayout.RIGHT));
        bookRoomPanel.add(bookRoomButton);

        bottomPanel.add(bookRoomPanel, BorderLayout.CENTER);
        
        GUILogger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * Builds and adds the search section to the bottom panel.
	 */
	private void addSearchSection() {
		
		final String methodName = "addSearchSection";
		GUILogger.entering(CLASS_NAME, methodName);
		
		final GridBagLayout gridbag = new GridBagLayout();
		final GridBagConstraints constraints = new GridBagConstraints();
		
		final JPanel searchPanel = new JPanel();
		searchPanel.setLayout(gridbag);
		
		constraints.insets = new Insets(2, 2, 2, 2);
		
		final JLabel nameLabel = new JLabel(GUIMessages.HOTEL_NAME_LABEL_TEXT);
		
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(nameLabel, constraints);
		searchPanel.add(nameLabel);
		
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(nameField, constraints);
		searchPanel.add(nameField);
		
		constraints.weightx = 0.0;
		
		JLabel locationLabel = new JLabel(GUIMessages.LOCATION_LABEL_TEXT);
		
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(locationLabel, constraints);
		searchPanel.add(locationLabel);
		
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(locationField, constraints);
		searchPanel.add(locationField);
		
		JButton searchButton = new JButton(GUIMessages.SEARCH_BUTTON_NAME);
		
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(searchButton, constraints);
		
		searchPanel.add(searchButton);
		
		bottomPanel.add(searchPanel, BorderLayout.NORTH);
		
		GUILogger.exiting(CLASS_NAME, methodName);
	}

	/**
	 * Builds and adds the panel which contains the data section to the main 
	 * panel. This section displays the data to user.
	 */
	private void addDisplayDataSection() {
		
		final String methodName = "addDisplayDataSection";
		GUILogger.entering(CLASS_NAME, methodName);
		
		final JScrollPane scrollPane = new JScrollPane(mainTable);
		scrollPane.setPreferredSize(new Dimension(GUIConstants.SCROLLPANE_WIDTH_SIZE, 
				GUIConstants.SCROLLPANE_HEIGHT_SIZE));
		
		addAllDataAsInitialStartup();
		
		mainTable.setModel(tableModel);
		mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
	}

	/**
	 * Builds and adds all valid records from the database to Main window to 
	 * display to the user.
	 */
	private void addAllDataAsInitialStartup() {
		
		final String methodName = "addAllRecordsToDisplay";
		GUILogger.entering(CLASS_NAME, methodName);
		
		List<Record> allRecords = new ArrayList<Record>();
		
		try {
			
			if (database == null) {
				
				GUILogger.warning(CLASS_NAME, methodName, 
						"Database object is null");
				
				setStatusLabelText(GUIMessages.NULL_DATABASE_MESSAGE);
				
			} else {
			
				allRecords = database.find(null, null);
				
				setStatusLabelText(allRecords.size() 
						+ GUIMessages.RECORDS_FOUND_MESSAGE);
			}
			
		} catch (RemoteException e) {
			
			GUILogger.warning(CLASS_NAME, methodName, "Unable to load all " +
					"records to display in the initial startup: " 
					+ e.getMessage());
			
			setStatusLabelText(GUIMessages.INITIAL_STARTUP_ERROR_MESSAGE);
			
		}
		
		addDataToTableModel(allRecords);
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
	}

	/**
	 * Adds the Client GUI's frame general configuration.
	 */
	private void addFrameConfiguration() {
		
		final String methodName = "setFrameConfiguration";
		GUILogger.entering(CLASS_NAME, methodName);
		
		setTitle(GUIMessages.CLIENT_TITLE_TEXT);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new ExitMainWindow());
		
		setResizable(false);
		
		add(mainPanel);
		
		GUILogger.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * Displays the server window.
	 */
	private void displayWindow() {
		
		final String methodName = "displayWindow";
		GUILogger.entering(CLASS_NAME, methodName);
		
		pack();
        GUIUtils.centerOnScreen(this);
        setVisible(true);
        
        GUILogger.exiting(CLASS_NAME, methodName);
        
	}

	/**
	 * Adds the specified list of <code>Record</code> objects to display to the
	 * user.
	 * 
	 * @param records List of <code>Record</code> objects to display.
	 */
	public void addDataToTableModel(final List<Record> records) {
		tableModel = new RecordTableModel(records);
	}
	
	/**
	 * Sets the specified text to the status label.
	 * 
	 * @param text Text to set to the status label.
	 */
	public void setStatusLabelText(final String text) {
		statusLabel.setText(text);
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

		new MainWindow(new Database());
//		ServerWindow.getInstance().displayWindow();
		
	}
	
}

package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
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
import javax.swing.border.BevelBorder;

import suncertify.controller.ExitStandAloneWindow;
import suncertify.controller.RowSelectionListener;
import suncertify.controller.SearchRecordsListener;
import suncertify.controller.BookRoomListener;
import suncertify.db.IDatabase;
import suncertify.db.Record;

/**
 * Provides the graphical user interface for the StandAlone window.
 * 
 * @author Leo Gutierrez
 */
public class StandAloneWindow extends JFrame {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 899029L;
	
	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = StandAloneWindow.class.getName();
	
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
	private final JTable recordTable = new JTable();

	/**
	 * Reference to the Book Room button.
	 */
	private final JButton bookRoomButton = 
    		new JButton(GUIMessages.BOOK_ROOM_BUTTON_NAME);

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
	private RecordTableModel tableModel;
	
	/**
	 * Reference to the database.
	 */
	private IDatabase database;
	
	/**
	 * Constructs a <code>StandAlone</code> object and displays the Main
	 * window.
	 * 
	 * @param databaseConnection Database from where perform the operations.
	 */
	public StandAloneWindow(final IDatabase databaseConnection) 
			throws RuntimeException {

		database = databaseConnection;

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
        
        setStatusLabelText(GUIMessages.WELCOME_TEXT);
        
        bottomPanel.add(statusPanel, BorderLayout.SOUTH);
        
        GUILogger.exiting(CLASS_NAME, method);
	}

	/**
	 * Builds and adds book room section to the bottom panel.
	 */
	private void addBookRoomSection() {
		
		final String methodName = "addBookRoomSection";
		GUILogger.entering(CLASS_NAME, methodName);
		
		// Disable button until user selects a row.
		bookRoomButton.setEnabled(false);
		bookRoomButton.setMnemonic(KeyEvent.VK_B);
		
		bookRoomButton.addActionListener(new BookRoomListener(this));
        
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
		
		final SearchRecordsListener searchRecordListener = 
				new SearchRecordsListener(this);
		
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
		
		nameField.addActionListener(searchRecordListener);
		searchPanel.add(nameField);
		
		
		constraints.weightx = 0.0;
		
		JLabel locationLabel = new JLabel(GUIMessages.LOCATION_LABEL_TEXT);
		
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(locationLabel, constraints);
		searchPanel.add(locationLabel);
		
		constraints.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(locationField, constraints);
		searchPanel.add(locationField);
		locationField.addActionListener(searchRecordListener);
		
		JButton searchButton = new JButton(GUIMessages.SEARCH_BUTTON_NAME);
		searchButton.setMnemonic(KeyEvent.VK_S);
		searchButton.addActionListener(searchRecordListener);
		
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
		
		final JScrollPane scrollPane = new JScrollPane(recordTable);
		scrollPane.setPreferredSize(new Dimension(GUIConstants.SCROLLPANE_WIDTH_SIZE, 
				GUIConstants.SCROLLPANE_HEIGHT_SIZE));
		
		addAllDataAsInitialStartup();
				
		recordTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		recordTable.getSelectionModel().addListSelectionListener(
				new RowSelectionListener(this));
		recordTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
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
		
		List<Record> currentRecordsInTable = new ArrayList<Record>();
		
		try {
			
			if (database == null) {
				
				GUILogger.warning(CLASS_NAME, methodName, 
						"Database object is null");
				
				setStatusLabelText(GUIMessages.CANT_CONTACT_DB_MESSAGE);
				
			} else {
			
				currentRecordsInTable = database.find(null, null);
				
			}
			
		} catch (RemoteException e) {
			
			GUILogger.warning(CLASS_NAME, methodName, "Unable to load all "
					+ "records to display in the initial startup: " 
					+ e.getMessage());
			
			setStatusLabelText(GUIMessages.INITIAL_STARTUP_ERROR_MESSAGE);
			
		}
		
		addDataToTableModel(currentRecordsInTable);
		
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
		
		addWindowListener(new ExitStandAloneWindow());
		
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
	public void addDataToTableModel(List<Record> records) {
		
		tableModel = new RecordTableModel(records);
		recordTable.setModel(tableModel);
	}
	
	/**
	 * Retrieves the <code>Record</code> object that represents the selected 
	 * row by the user.
	 * <br />If selected row is out of bounds, a null <code>Record</code>
	 * object is returned.
	 * 
	 * @param row Row index to retrieve the <code>Record</code> object.
	 * @return A <code>Record</code> object that represents the selected 
	 *         row by the user, or null of user selects an out of bounds row.
	 */
	public Record getRecordFromTable(final int row) {
		
		final String methodName = "getRecordFromData";
		GUILogger.entering(CLASS_NAME, methodName, row);
		
		final List<Record> currentRecordsInTable = tableModel.getRecords();
		
		Record record = null;
		
		if ((row >= 0) && (row < currentRecordsInTable.size())) {
			
			record = currentRecordsInTable.get(row);
			
		}
		
		GUILogger.exiting(CLASS_NAME, methodName, record);
		
		return record;
	}
	
	/**
	 * Sets the specified text to the status label.
	 * 
	 * @param text Text to set to the status label.
	 */
	public void setStatusLabelText(final String text) {
		statusLabel.setText(text);
	}
	
	/**
	 * Retrieves the name field text specified by the user.
	 * 
	 * @return The name field text specified by the user.
	 */
	public String getHotelnameFieldText() {
		return nameField.getText();
	}
	
	/**
	 * Sets the name field text.
	 * 
	 * @param text The name field text to set.
	 */
	public void setHotelnameFieldText(final String text) {
		nameField.setText(text);
	}
	
	/**
	 * Retrieves the location field text specified by the user.
	 * 
	 * @return The location field text specified by the user.
	 */
	public String getLocationFieldText() {
		return locationField.getText();
	}
	
	/**
	 * Sets the location field text.
	 * 
	 * @param text The location field text.
	 */
	public void setLocationFieldText(final String text) {
		locationField.setText(text);
	}
	
	/**
	 * Retrieves the database to work with.
	 * 
	 * @return The database object.
	 */
	public IDatabase getDatabase() {
		return database;
	}
	
	/**
	 * Retrieves the main table that displays the records.
	 * 
	 * @return The main table that displays the records.
	 */
	public JTable getRecordTable() {
		return recordTable;
	}
	
	/**
	 * Retrieves the <code>RecordTableModel</object> that is added to the
	 * <code>JTable</code> as the model.
	 * 
	 * @return The <code>RecordTableModel</object> that is added to the
	 * <code>JTable</code> as the model.
	 */
	public RecordTableModel getRecordTableModel() {
		return tableModel;
	}
	
	/**
	 * Enables or disables the Book Room button.
	 * 
	 * @param enable <code>True</code> if want to enable the button; 
	 *               <code>False</code> otherwise.
	 */
	public void enableBookRoomButton(final boolean enable) {
		bookRoomButton.setEnabled(enable);
	}
	
}

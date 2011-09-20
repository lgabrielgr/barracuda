package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;

import suncertify.db.Database;
import suncertify.db.IDatabase;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;

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
	 * Reference to the table that displays the records.
	 */
	private JTable mainTable = new JTable();
	
	/**
	 * Reference to the search field.
	 */
	private JTextField searchField = new JTextField(20);
	
	/**
	 * Reference to the table model.
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
	public MainWindow(final IDatabase database) {
		
		this.database = database;
		
		init();
		
		displayWindow();
		
	}
	
	private void init() {
		
		setTitle("Client");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		final JPanel mainPanel = new JPanel(new BorderLayout());
		
		final JScrollPane scrollPane = new JScrollPane(mainTable);
		scrollPane.setSize(500, 250);
		
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Record> records = new ArrayList<Record>();
				try {
					records.add(database.read(74));
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RecordNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tableModel = new RecordTableModel(records);
//				mainTable.setModel(tableModel);
			}
		});
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        JButton rentButton = new JButton("Book Room");
        
        JPanel hiringPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        hiringPanel.add(rentButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(searchPanel, BorderLayout.NORTH);
        bottomPanel.add(hiringPanel, BorderLayout.SOUTH);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
        try {
			tableModel = new RecordTableModel(database.find(null, null));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        mainTable.setModel(tableModel);
        
        add(mainPanel);
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

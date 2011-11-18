package suncertify.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import suncertify.db.IDatabase;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;
import suncertify.gui.GUIMessages;
import suncertify.gui.GUIUtils;
import suncertify.gui.StandAloneWindow;

/**
 * Provides the functionality when user books a room.
 * 
 * @author Leo Gutierrez
 *
 */
public class BookRoomListener implements ActionListener {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = BookRoomListener.class.getName();
	
	/**
	 * Reference to the main window frame.
	 */
	private StandAloneWindow mainWindow = null;
	
	/**
	 * Constructs a <code>BookRoomListener</object> object.
	 * 
	 * @param mainWindow Reference to the main window frame.
	 */
	public BookRoomListener(final StandAloneWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	/**
	 * Invoked when user clicks on 'Book Room' button.
	 * 
	 * @param actionEvent Reference to the <code>ActionEvent</code> object.
	 */
	public void actionPerformed(final ActionEvent actionEvent) {
		
		final String methodName = "actionPerformed";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		try {
		
			if ((mainWindow == null) 
					|| (mainWindow.getDatabase() == null)) {

				ControllerLogger.severe(CLASS_NAME, methodName, 
						"The user booked a room, but a reference to the main " +
						"window or database does not exist");

				GUIUtils.showErrorMessageDialog(null, 
						GUIMessages.BOOK_ROOM_FAILED_MESSAGE);

				return; 
			}
		
			final String ownerId = askOwnerIdToUser();
			
			// If owner is null or empty, means that user cancels the operation
			if ((ownerId != null) && (!"".equals(ownerId.trim()))) {
				
				bookRoom(ownerId);
				
			}
			
			
		} finally {
			
			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}
		
	}
	
	/**
	 * Books a room with the specified Owner Id value. A message is displayed 
	 * to the user indicating if the room was booked successfully or not.
	 * <br/ >The <code>Record</code> object is extracted from the selected row 
	 * by the user from the main window's records table.
	 * 
	 * @param ownerId Owner Id value to use to book room.
	 */
	private void bookRoom(final String ownerId) {
		
		final String methodName = "bookRoom";
		ControllerLogger.entering(CLASS_NAME, methodName, ownerId);
		
		final JTable recordTable = mainWindow.getRecordTable();

		final int selectedRow = recordTable.getSelectedRow();
		
		final Record recordToUpdate = 
				mainWindow.getRecordFromTable(selectedRow);

		if (recordToUpdate == null) {

			ControllerLogger.severe(CLASS_NAME, methodName, 
					"Unable to book the room, the record was not found in db");

			displayError(
					GUIMessages.FAILED_BOOK_ROOM_RECORD_NOT_FOUND_MESSAGE);

			return;
			
		} 
		
		try { 

			recordToUpdate.setOwner(ownerId);

			if (updateDatabase(recordToUpdate)) {
				
				mainWindow.getRecordTable().setValueAt(ownerId, selectedRow, 
						Record.OWNER_FIELD_INDEX);
				
				final StringBuilder statusMessage = new StringBuilder();
				statusMessage.append(GUIMessages.ROOM_BOOKED_MESSAGE);
				statusMessage.append(recordToUpdate.getHotelName()).append(", ");
				statusMessage.append(recordToUpdate.getLocation());
				
				mainWindow.setStatusLabelText(statusMessage.toString());
				
				recordTable.getSelectionModel().setSelectionInterval(selectedRow, 
						selectedRow);
				
			}
			
		} catch (IllegalArgumentException e) {
			
			ControllerLogger.warning(CLASS_NAME, methodName, 
					"Invalid owner id value: " + ownerId);
			
			GUIUtils.showWarningMessage(mainWindow, 
					GUIMessages.INVALID_VALUE_TO_SET_MESSAGE);
			
		}
		
		ControllerLogger.exiting(CLASS_NAME, methodName);
		
	}

	/**
	 * Updates the database with the given <code>Record</code> that contains
	 * the owner id value specified by user.
	 * 
	 * @param record <code>Record</code> to update in database.
	 * @return True if the the <code>Record</code> was updated successfully in
	 *         the database; False otherwise.
	 */
	private boolean updateDatabase(final Record record) {
		
		final String methodName = "updateDatabase";
		ControllerLogger.entering(CLASS_NAME, methodName, record);
		
		boolean recordUpdated = true;
		
		final IDatabase database = mainWindow.getDatabase();
		
		try {

			database.update(record.getDatabaseRow(), record);
			
		} catch (RemoteException e) {
			
			ControllerLogger.severe(CLASS_NAME, methodName, 
					"Unable to book the room due to networking problems: " 
							+ e.getMessage());
			
			displayError(GUIMessages.FAILED_BOOK_ROOM_DB_MESSAGE);
			
			recordUpdated = false;
			
		} catch (RecordNotFoundException e) {
			
			ControllerLogger.severe(CLASS_NAME, methodName, 
					"Unable to book the room, the record was not found in db: " 
							+ e.getMessage());
			
			displayError(
					GUIMessages.FAILED_BOOK_ROOM_RECORD_NOT_FOUND_MESSAGE);
			
			recordUpdated = false;
		}
		
		ControllerLogger.exiting(CLASS_NAME, methodName, recordUpdated);
		
		return recordUpdated;
	}
	
	/**
	 * Updates the main window's status bar and displays the given error 
	 * message to the user.
	 * 
	 * @param errorMessage Error message to display to user.
	 */
	private void displayError(final String errorMessage) {
		
		mainWindow.setStatusLabelText(errorMessage);
		
		GUIUtils.showErrorMessageDialog(mainWindow, errorMessage);
		
	}

	/**
	 * Displays a dialog to the user asking for the Owner Id to use to book the
	 * selected room.
	 * 
	 * @return Value entered by the user, or null/empty value if user cancels
	 *         the operation.
	 */
	private String askOwnerIdToUser() {
		
		return JOptionPane.showInputDialog(mainWindow, 
				GUIMessages.ENTER_OWNER_ID_MESSAGE, 
				GUIMessages.BOOK_ROOM_TITLE, JOptionPane.PLAIN_MESSAGE);
		
	}
	
}
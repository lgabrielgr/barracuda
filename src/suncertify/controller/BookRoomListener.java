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
import suncertify.gui.ClientWindow;

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
	private static final String CLASS_NAME =
			BookRoomListener.class.getName();

	/**
	 * Reference to the client window frame.
	 */
	private ClientWindow clientWindow = null;

	/**
	 * Constructs a <code>BookRoomListener</code> object.
	 *
	 * @param clientWindowFrame Reference to the client window frame.
	 */
	public BookRoomListener(final ClientWindow clientWindowFrame) {

		clientWindow = clientWindowFrame;

	}

	/**
	 * Invoked when user clicks on 'Book Room' button.
	 *
	 * @param actionEvent Reference to the <code>ActionEvent</code> object.
	 */
	public final void actionPerformed(final ActionEvent actionEvent) {

		final String methodName = "actionPerformed";
		ControllerLogger.entering(CLASS_NAME, methodName);

		try {

			if ((clientWindow == null)
					|| (clientWindow.getDatabase() == null)) {

				ControllerLogger.severe(CLASS_NAME, methodName,
						"The user booked a room, but a reference to the stand"
								+ " alone window or database does not exist");

				GUIUtils.showErrorMessageDialog(null,
						GUIMessages.BOOK_ROOM_FAILED_MESSAGE);

				return;
			}

			String ownerId = askOwnerIdToUser();

			// If owner is null or empty, means that user cancels the operation
			if ((ownerId != null) && (!"".equals(ownerId.trim()))) {
				
				// Remove left zeros (if any)
				ownerId = Long.valueOf(ownerId).toString(); 
				
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
	 * by the user from the stand alone window's records table.
	 *
	 * @param ownerId Owner Id value to use to book room.
	 */
	private void bookRoom(final String ownerId) {

		final String methodName = "bookRoom";
		ControllerLogger.entering(CLASS_NAME, methodName, ownerId);

		final JTable recordTable = clientWindow.getRecordTable();

		if (!isRoomAlreadyBooked(recordTable)) {

			try {

				final int selectedRow = recordTable.getSelectedRow();

				final Record recordToUpdate =
						clientWindow.getRecordFromTable(selectedRow);

				recordToUpdate.setOwner(ownerId);

				if (updateDatabase(recordToUpdate)) {

					recordTable.setValueAt(ownerId, selectedRow,
							Record.OWNER_FIELD_INDEX);

					recordTable.getSelectionModel().setSelectionInterval(
							selectedRow, selectedRow);

				}

			} catch (IllegalArgumentException e) {

				ControllerLogger.warning(CLASS_NAME, methodName,
						"Invalid owner id value: " + ownerId);

				GUIUtils.showWarningMessage(clientWindow,
						GUIMessages.INVALID_VALUE_TO_SET_MESSAGE);

			}

		}

		ControllerLogger.exiting(CLASS_NAME, methodName);

	}

	/**
	 * Verifies if the given <code>Record</code> is already booked.
	 * <br />Reads the given record from the database to get a fresh data,
	 * and verify if the record was or was not booked previously by another
	 * instance.
	 *
	 *@param recordTable JTable object from where extract the
	 *                    <code>Record</code> selected by user.
	 * @return True if it is booked; False otherwise.
	 */
	private boolean isRoomAlreadyBooked(final JTable recordTable) {

		final String methodName = "isRoomAlreadyBooked";
		ControllerLogger.entering(CLASS_NAME, methodName);

		boolean roomBooked = false;

		final int selectedRow = recordTable.getSelectedRow();

		final Record recordSelected =
				clientWindow.getRecordFromTable(selectedRow);

		final Record freshRecord = readRecordFromDatabase(
				recordSelected.getDatabaseRow());

		if (freshRecord != null) {

			final String ownerId = freshRecord.getOwner();

			if ((ownerId != null) && (!"".equals(ownerId))) {

				roomBooked = true;

				recordTable.setValueAt(ownerId, selectedRow,
						Record.OWNER_FIELD_INDEX);

				recordTable.getSelectionModel().setSelectionInterval(
						selectedRow, selectedRow);

				ControllerLogger.severe(CLASS_NAME, methodName,
						"Unable to book the room, it is already booked");

				displayErrorToUser(
						GUIMessages.ROOM_ALREADY_BOOKED_MESSAGE);

			}

		}

		ControllerLogger.exiting(CLASS_NAME, methodName, roomBooked);

		return roomBooked;
	}

	/**
	 * Reads a record in the given position form the database.
	 *
	 * @param recordRow Record row position where to read the 
	 *                  <code>Record</code>
	 * @return <code>Record</code> object if found; Null otherwise.
	 */
	private Record readRecordFromDatabase(final int recordRow) {

		final String methodName = "readRecordFromDatabase";
		ControllerLogger.entering(CLASS_NAME, methodName);

		Record record = null;

		final IDatabase database = clientWindow.getDatabase();

		try {

			record = database.read(recordRow);

		} catch (RemoteException e) {

			ControllerLogger.warning(CLASS_NAME, methodName, "Unable to read "
					+ "the record from database: " + e.getMessage());

		} catch (RecordNotFoundException e) {

			ControllerLogger.warning(CLASS_NAME, methodName, "Unable to read "
					+ "the record, it wasnt found int the database");

		}

		ControllerLogger.exiting(CLASS_NAME, methodName);

		return record;

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

		final IDatabase database = clientWindow.getDatabase();

		try {

			database.update(record.getDatabaseRow(), record);

		} catch (RemoteException e) {

			ControllerLogger.severe(CLASS_NAME, methodName,
					"Unable to book the room due to networking problems: "
							+ e.getMessage());

			displayErrorToUser(GUIMessages.FAILED_BOOK_ROOM_DB_MESSAGE);
			
			clientWindow.setStatusLabelText(
					GUIMessages.NOT_CONNECTED_TO_SERVER_MESSAGE);
			
			recordUpdated = false;

		} catch (RecordNotFoundException e) {

			ControllerLogger.severe(CLASS_NAME, methodName,
					"Unable to book the room, the record was not found in db: "
							+ e.getMessage());

			displayErrorToUser(
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
	private void displayErrorToUser(final String errorMessage) {

		GUIUtils.showErrorMessageDialog(clientWindow, errorMessage);

	}

	/**
	 * Displays a dialog to the user asking for the Owner Id to use to book the
	 * selected room.
	 * 
	 * @return Value entered by the user, or null/empty value if user cancels
	 *         the operation.
	 */
	private String askOwnerIdToUser() {
		
		return JOptionPane.showInputDialog(clientWindow,
				GUIMessages.ENTER_OWNER_ID_MESSAGE,
				GUIMessages.BOOK_ROOM_TITLE, JOptionPane.PLAIN_MESSAGE);

	}

}

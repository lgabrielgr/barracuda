package suncertify.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.JTable;

import suncertify.db.IDatabase;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;
import suncertify.gui.BookRoomPane;
import suncertify.gui.ClientWindow;
import suncertify.gui.GUIMessages;
import suncertify.gui.GUIUtils;

/**
 * Provides the functionality when user wants to book a room.
 *
 * @author Leo Gutierrez
 *
 */
public class BookRoomListener implements ActionListener, MouseListener {

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

			String ownerId = BookRoomPane.showDialog(clientWindow);

			// If owner is null or empty, means that user cancelled the operation
			if ((ownerId != null) && (!"".equals(ownerId.trim()))) {
				
				bookRoom(ownerId);

			}


		} finally {

			ControllerLogger.exiting(CLASS_NAME, methodName);

		}

	}

	/**
	 * Invoked when mouse is clicked over a row in the list of rooms. The 
	 * function is only considered when user clicks twice indicating that 
	 * wants to book the selected room.
	 * 
	 * @param mouseEvent The mouse event.
	 */
	public void mouseClicked(final MouseEvent mouseEvent) {
		
		if (mouseEvent.getClickCount() == 2) {
			
			final int selectedRow = 
					clientWindow.getRecordTable().getSelectedRow();
			
			final Record recordSelected =
					clientWindow.getRecordFromTable(selectedRow);
			
			if ((recordSelected != null)
					&& ((recordSelected.getOwner() == null)
							|| ("".equals(recordSelected.getOwner().trim())))) {
				
				actionPerformed(null);
				
			}
			
		}
	}

	/**
	 * Invoked when mouse is pressed over a row in the list of rooms.
	 * <br /><b>This function is currently not supported.</b>
	 * 
	 * @param mouseEvent The mouse event.
	 */
	public void mousePressed(final MouseEvent mouseEvent) {
		
	}

	/**
	 * Invoked when mouse is released over a row in the list of rooms.
	 * <br /><b>This function is currently not supported.</b>
	 * 
	 * @param mouseEvent The mouse event.
	 */
	public void mouseReleased(final MouseEvent mouseEvent) {
		
	}

	/**
	 * Invoked when mouse is entered over a row in the list of rooms.
	 * <br /><b>This function is currently not supported.</b>
	 * 
	 * @param mouseEvent The mouse event.
	 */
	public void mouseEntered(final MouseEvent mouseEvent) {
		
	}

	/**
	 * Invoked when mouse is exited over a row in the list of rooms.
	 * <br /><b>This function is currently not supported.</b>
	 * 
	 * @param mouseEvent The mouse event.
	 */
	public void mouseExited(final MouseEvent mouseEvent) {
		
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

		if (isRoomAlreadyBooked(recordTable)) {
			
			ControllerLogger.severe(CLASS_NAME, methodName,
					"Unable to book the room, it is already booked");

			displayErrorToUser(
					GUIMessages.ROOM_ALREADY_BOOKED_MESSAGE);
			
		} else {

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

					final String messageToUser = GUIUtils.formatMessage(
							GUIMessages.ROOM_BOOKED_MESSAGE, 
							new Object[]{recordToUpdate.getHotelName(), 
									recordToUpdate.getLocation(), recordToUpdate.getOwner()});
					
					GUIUtils.showInformationMessage(clientWindow, messageToUser);
					
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

				// Update record selected with owner that already has booked the room.
				recordTable.setValueAt(ownerId, selectedRow,
						Record.OWNER_FIELD_INDEX);

				recordTable.getSelectionModel().setSelectionInterval(
						selectedRow, selectedRow);

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

}

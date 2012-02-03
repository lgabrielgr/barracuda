package suncertify.controller;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import suncertify.db.Record;
import suncertify.gui.StandAloneWindow;

/**
 * Provides the functionality when user selects a single row in the main table.
 * <br />The functionality is to check if the selected row (record) is valid to 
 * be booked. If it is, the 'Book Room' button is enabled to perform the book
 * operation. 
 * 
 * @author Leo Gutierrez
 */
public class RowSelectionListener implements ListSelectionListener {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME =
			RowSelectionListener.class.getName();
	
	/**
	 * Reference to the stand alone window frame.
	 */
	private StandAloneWindow standAloneWindow = null;

	/**
	 * Constructs a <code>RowSelectionListener</code> object.
	 * 
	 * @param standAloneWindowFrame Reference to the stand alone window frame.
	 */
	public RowSelectionListener(final StandAloneWindow standAloneWindowFrame) {

		standAloneWindow = standAloneWindowFrame;

	}

	/**
	 * Invoked when user selects a single row in the main table.
	 * <br />Verifies if the row selected is valid to be booked:
	 * <br />- Owner column is empty.
	 * <br />If it is valid, the 'Book Room' button is enabled to perform the
	 * book operation.
	 * 
	 * @param event Reference to the event.
	 */
	public final void valueChanged(final ListSelectionEvent event) {

		final String methodName = "valueChanged";
		ControllerLogger.entering(CLASS_NAME, methodName);

		// Execute the functionality whenever the selection has finalized.
		if (!event.getValueIsAdjusting()) {

			if (standAloneWindow == null) {

				ControllerLogger.warning(CLASS_NAME, methodName,
						"User has selected a main table's row but a reference "
								+ "to the stand alone window does not exist");

				return;
			}

			final JTable recordTable = standAloneWindow.getRecordTable();

			final Record recordSelected = standAloneWindow.getRecordFromTable(
					recordTable.getSelectedRow());
			
			if (recordSelected == null) {
				standAloneWindow.enableBookRoomButton(false);
			} else if ((recordSelected.getOwner() != null) 
					&& (!"".equals(recordSelected.getOwner()))) {
				standAloneWindow.enableBookRoomButton(false);
			} else {
				standAloneWindow.enableBookRoomButton(true);
			}

		}
		
		ControllerLogger.exiting(CLASS_NAME, methodName);

	}
		
}

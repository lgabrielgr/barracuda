package suncertify.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import suncertify.db.Record;

/**
 * Models the specified records to display in a <code>JTable</code>.
 * <br />If the database's columns change, this class must be updated to 
 * display the proper data.
 * 
 * @author Leo Gutierrez
 */
public class RecordTableModel extends AbstractTableModel {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 770791L;

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = RecordTableModel.class.getName();
	
	/**
	 * Reference to the column names that is defined in the database.
	 */
	private static final String[] columnNames = {"Hotel Name", "Location", "Size", 
			"Smoking", "Rate", "Date Available", "Owner"};
	
	/**
	 * Minimum rows to display in the table.
	 */
	private static final int MIN_ROWS_COUNT = 18;
	
	/**
	 * Reference to the records to model in table.
	 */
	private List<Record> records;
	
	/**
	 * Constructs a <code>RecordTableModel</code> object.
	 * 
	 * @param records Records to model in table.
	 */
	public RecordTableModel(final List<Record> records) {
		
		if (records == null) {
			
			this.records = new ArrayList<Record>();
			
		} else {
			
			this.records = records;
			
		}
	}
	
	/**
	 * Returns the number of rows in the model.
	 * 
	 * @return The number of rows in the model.
	 */
	public int getRowCount() {
		
		final String methodName = "getRowCount";
		GUILogger.entering(CLASS_NAME, methodName);
		
		int rowCount = records.size();
		
		if (rowCount < MIN_ROWS_COUNT) {
			rowCount += (MIN_ROWS_COUNT - rowCount);
		}
		
		GUILogger.exiting(CLASS_NAME, methodName, rowCount);
		
		return rowCount;
		
	}

	/**
	 * Returns the number of columns in the model.
	 * 
	 * @return The number of columns in the model.
	 */
	public int getColumnCount() {
		
		final String methodName = "getColumnCount";
		GUILogger.entering(CLASS_NAME, methodName);
		GUILogger.exiting(CLASS_NAME, methodName, columnNames.length);
		
		return columnNames.length;
	}

	/**
	 * Returns the value for the cell at columnIndex and rowIndex.
	 * 
	 * @param rowIndex The row whose value is to be queried.
	 * @param columnIndex The column whose value is to be queried.
	 * 
	 * @return The value Object at the specified cell.
	 */
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		
		final String methodName = "getValueAt";
		GUILogger.entering(CLASS_NAME, methodName, rowIndex, columnIndex);
		
		String valueAt = "";
		
		if (isValidRow(rowIndex)) {
			
			final Record record = records.get(rowIndex);

			if (record != null) {

				final String[] recordData = record.toStringArray();

				valueAt = recordData[columnIndex];

			}

		}
		
		GUILogger.exiting(CLASS_NAME, methodName, valueAt);
		
		return valueAt;
	
	}

	/**
	 * Sets the specified value at the specified coordinates.
	 * 
	 * @param value Value to set.
	 * @param rowIndex Row in which to set the value.
	 * @param updateIndex Column in which to set the value.
	 */
	public void setValueAt(final Object value, final int rowIndex, 
			final int columnIndex) {
		
		final String methodName = "setValueAt";
		GUILogger.entering(CLASS_NAME, methodName, value, rowIndex, 
				columnIndex);
		
		if (isValidRow(rowIndex)) {
			
			try {
				
				updateRowToDisplay(value, rowIndex, columnIndex);
				
				fireTableDataChanged();
				
			} catch (IllegalArgumentException e) {
				
				GUILogger.warning(CLASS_NAME, methodName, 
						"Invalid value to set: " + e.getMessage());
				
				GUIUtils.showWarningMessage(null, 
						GUIMessages.INVALID_VALUE_TO_SET_MESSAGE);
				
			}
			
		}
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
	}

	/**
	 * Updates a record value to display in the main window. 
	 * 
	 * @param value Value to update in the <code>Record</code> object.
	 * @param rowIndex Row index to extract the <code>Record</code> object.
	 * @param columnIndex Column index where the value must be updated.
	 * @throws IllegalArgumentException If the value to be updated is not 
	 *                                  valid.
	 */
	private void updateRowToDisplay(final Object value, final int rowIndex,
			final int columnIndex) throws IllegalArgumentException {
		
		final String methodName = "updateRowToDisplay";
		GUILogger.entering(CLASS_NAME, methodName, value, rowIndex, 
				columnIndex);
		
		final Record record = records.get(rowIndex);
		final String stringValue = (String) value;

		try {
			
			switch(columnIndex) {

			case Record.HOTEL_NAME_FIELD_INDEX:
				record.setHotelName(stringValue);
				break;

			case Record.LOCATION_FIELD_INDEX:
				record.setLocation(stringValue);
				break;

			case Record.DATE_FIELD_INDEX:
				record.setDate(stringValue);
				break;

			case Record.RATE_FIELD_INDEX:
				record.setRate(stringValue);
				break;

			case Record.SIZE_FIELD_INDEX:
				record.setSize(stringValue);
				break;

			case Record.SMOKING_FIELD_INDEX:
				record.setSmoking(stringValue);
				break;

			case Record.OWNER_FIELD_INDEX:
				record.setOwner(stringValue);
				break;

			default:

				GUILogger.warning(CLASS_NAME, methodName, 
						"Can't update a record with the value: " + stringValue +
						"at row: " + rowIndex + " and column: " + columnIndex);

			}

		} finally {
			GUILogger.exiting(CLASS_NAME, methodName);
		}
	}
	
	/**
	 * Retrieves the column name for the specified index.
	 * 
	 * @param column Column index to retrieve it's name.
	 * @return The column name.
	 */
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	
	/**
	 * Verifies if the given row in the table model is valid to work with
	 * (between the range of the data displayed).
	 * 
	 * @param row Row to verify.
	 * @return <code>True</code> if the given row is valid to work with; 
	 *         <code>False</code> otherwise.
	 */
	private boolean isValidRow(final int row) {
		
		final String methodName = "isValidRow";
		GUILogger.entering(CLASS_NAME, methodName, row);
		
		boolean validRow = true;
		
		if ((row < 0) || (row >= records.size())) {
			validRow = false;
		}
			
		GUILogger.exiting(CLASS_NAME, methodName, validRow);
		
		return validRow;
	}

	/**
	 * Retrieves the current list of records displayed in the main window.
	 * 
	 * @return Current list of records displayed in the main window.
	 */
	public List<Record> getRecords() {
		return records;
	}
	
}

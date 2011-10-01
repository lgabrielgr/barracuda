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
	 * Reference to the owner column number.
	 */
	private static final int OWNER_COLUMN = 6; 
	
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
		
		if (rowIndex < records.size()) {
			
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
	 * Retrieves the column name for the specified index.
	 * 
	 * @param column Column index to retrieve it's name.
	 * @return The column name.
	 */
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	/**
	 * Verifies if a cell with the given coordinates.
	 * 
	 * @param row The row being queried.
	 * @param column the column being queried.
	 * @return True if the cell is in the Owner column and does not contain a
	 *         value.
	 */
	public boolean isCellEditable(final int row, final int column) {
		
		final String methodName = "isCellEditable";
		GUILogger.entering(CLASS_NAME, methodName, row, column);
		
		boolean cellEditable = false;
		
		if ( (row < records.size()) && (column == OWNER_COLUMN)) {

			final Record record = records.get(row);

			if (record != null) {

				final Object cellValue = getValueAt(row, column);

				if ((cellValue == null) 
						|| ("".equals(cellValue.toString().trim()))) {

					cellEditable = true;

				} 

			}

		}
		
		GUILogger.exiting(CLASS_NAME, methodName, cellEditable);
		
		return cellEditable;
	}
	
}

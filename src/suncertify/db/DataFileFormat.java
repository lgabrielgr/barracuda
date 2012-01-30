package suncertify.db;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Loads dynamically the format of data in the database file. If the database's
 * format is updated (i.e. new field, new record length, field deleted) there
 * is not need to modify this class.
 * 
 * @author Leo Gutierrez
 */
public class DataFileFormat {
	
	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = DataFileFormat.class.getName();
	
	/**
	 * Magic number.
	 */
	private int magicNumber;
	
	/**
	 * Record length.
	 */
	private int recordLength;
	
	/**
	 * Number of fields per records.
	 */
	private int numberOfFieldsPerRecord;
	
	/**
	 * Data section, contain the record rows saved in database.
	 */
	private Set<Integer> recordRows = new TreeSet<Integer>();
	
	/**
	 * Database schema.
	 */
	private Set<RecordField> recordFields = new LinkedHashSet<RecordField>();

	/**
	 * Constructs a <code>DataFileFormat</code> object.
	 * 
	 * @param database Database from where to load the data file format.
	 */
	public DataFileFormat(final RandomAccessFile database) {
		init(database);
	}
	
	/**
	 * Loads the data file format in the following order:
	 * <br />- Reads the start of the file that is: Magic number, Record 
	 *         length and Number of fields per record.
	 * <br />- Reads the database schema.
	 * <br />- Reads the data section.
	 * 
	 * @param database Database from where to load the data file format.
	 */
	private void init(final RandomAccessFile database) {
		
		final String methodName = "init";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			if (database == null) {
				throw new IOException("Database is null");
			}
			
			readStartOfFile(database);
			
			readSchemaDescription(database);
			
			readDataSection(database);
			
		} catch (IOException e) {

			DatabaseLogger.severe(CLASS_NAME, methodName, "Unable to load the "
					+ "data file format due to an error: " + e.getMessage());
			
		} finally {
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
		
	}

	/**
	 * Reads the data sections. That is, saves the rows where records
	 * are stored in database.
	 * 
	 * @param database Database from where to load the data file format.
	 * @throws IOException If any I/O error occurs.
	 */
	private void readDataSection(final RandomAccessFile database)
			throws IOException {
		
		final String methodName = "readDataSection";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			final long initialDataPosition = database.getFilePointer();
			
			final long lastRecordRow = database.length() - getRecordLength();
			long currentRecordRow = 0;
			int totalRecordRows = 0;
			while (currentRecordRow < lastRecordRow) {

				final int currentRecordPosition = 
						totalRecordRows * getRecordLength();
				currentRecordRow = initialDataPosition + currentRecordPosition;

				recordRows.add((int) currentRecordRow);

				totalRecordRows++;
			}
			
		} finally {
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
	}

	/**
	 * Reads the database schema description in the database file. That is,
	 * the fields that a record must have.
	 * 
	 * @param database Database from where to load the data file format.
	 * @throws IOException If any I/O error occurs.
	 */
	private void readSchemaDescription(final RandomAccessFile database)
			throws IOException {
		
		final String methodName = "readSchemaDescription";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		try { 
			
			for (int fieldIndex = 0; 
					fieldIndex < getNumberOfFieldsPerRecord(); fieldIndex++) {

				final RecordField recordField = new RecordField();

				final short fieldNameLength = database.readShort();
				recordField.setFieldNameLength(fieldNameLength);

				final byte[] fieldName = new byte[fieldNameLength];
				database.read(fieldName);
				recordField.setFieldName(new String(fieldName));

				recordField.setFieldValueLength(database.readShort());

				recordField.setFieldPosition(fieldIndex);
				
				recordFields.add(recordField);
			}
			
		} finally {
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
	}

	/**
	 * Reads the start of the file that is:
	 * <br />- Magic number.
	 * <br />- Reads the record length.
	 * <br />- Reads the number of fields per record.
	 *  
	 * @param database Database from where to load the data file format.
	 * @throws IOException If any I/O error occurs.
	 */
	private void readStartOfFile(final RandomAccessFile database)
			throws IOException {
		
		final String methodName = "readStartOfFile";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			setMagicNumber(database.readInt());

			// Append an extra position that is used to know if it is deleted
			setRecordLength(database.readInt() + 1);

			setNumberOfFieldsPerRecord(database.readShort());
			
		} finally {
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
	}

	/**
	 * Retrieves the magic number.
	 * 
	 * @return Magic number.
	 */
	public final int getMagicNumber() {
		return magicNumber;
	}

	/**
	 * Sets the magic number.
	 * 
	 * @param magicNumberValue Magic number value.
	 */
	private void setMagicNumber(final int magicNumberValue) {
		magicNumber = magicNumberValue;
	}

	/**
	 * Retrieves the record length.
	 * 
	 * @return Record length.
	 */
	public final int getRecordLength() {
		return recordLength;
	}

	/**
	 * Sets the record length.
	 * 
	 * @param recordLengthValue Record length value.
	 */
	private void setRecordLength(final int recordLengthValue) {
		recordLength = recordLengthValue;
	}

	/**
	 * Retrieves the number of fields per record.
	 * 
	 * @return Number of fields per record.
	 */
	public final int getNumberOfFieldsPerRecord() {
		return numberOfFieldsPerRecord;
	}

	/**
	 * Sets the number of fields per record.
	 * 
	 * @param fieldsPerRecord Number of fields per record.
	 */
	private void setNumberOfFieldsPerRecord(final int fieldsPerRecord) {
		numberOfFieldsPerRecord = fieldsPerRecord;
	}

	/**
	 * Retrieves the data section.
	 * 
	 * @return A set of rows where all records are stored in database.
	 */
	public final Set<Integer> getRecordRows() {
		return recordRows;
	}
	
	/**
	 * Retrieves the database schema.
	 * 
	 * @return Database schema.
	 */
	public final Set<RecordField> getRecordFields() {
		return recordFields;
	}
	
}

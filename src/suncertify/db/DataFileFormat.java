package suncertify.db;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Set;
import java.util.TreeSet;

public class DataFileFormat {
	
	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = DataFileFormat.class.getName();
	
	private int magicNumber;
	
	private int recordLength;
	
	private int numberOfFieldsPerRecord;
	
	private Set<Integer> dataSection = new TreeSet<Integer>();
	
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
	 * <br />- Reads the magic number.
	 * <br />- Reads the record length.
	 * <br />- Reads the number of fields per record.
	 * <br />- Reads each row of all records stored in database.
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
			
			setMagicNumber(database.readInt());
			
			setRecordLength(database.readInt());
			
			setNumberOfFieldsPerRecord(database.readShort());
			
			final long initialDataPosition = database.getFilePointer();
			long currentRecordPosition = 0;
			int recordRows = 0;
			while (currentRecordPosition < database.length()) {
				
				// Append an extra position that is used to know if it is deleted
				final int totalRecordLength = getRecordLength() + 1;
				currentRecordPosition = initialDataPosition + (recordRows * totalRecordLength);
				
				dataSection.add((int)currentRecordPosition);
				
				recordRows++;
			}
			
		} catch (IOException e) {

			DatabaseLogger.severe(CLASS_NAME, methodName, "Unable to load the " +
					"data file format due to an error: " + e.getMessage());
			
		} finally {
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
		
	}

	/**
	 * Retrieves the magic number.
	 * 
	 * @return Magic number.
	 */
	public int getMagicNumber() {
		return magicNumber;
	}

	/**
	 * Sets the magic number.
	 * 
	 * @param magicNumber Magic number.
	 */
	private void setMagicNumber(int magicNumber) {
		this.magicNumber = magicNumber;
	}

	/**
	 * Retrieves the record length.
	 * 
	 * @return Record length.
	 */
	public int getRecordLength() {
		return recordLength;
	}

	/**
	 * Sets the record length.
	 * 
	 * @param recordLength Record length.
	 */
	private void setRecordLength(int recordLength) {
		this.recordLength = recordLength;
	}

	/**
	 * Retrieves the number of fields per record.
	 * 
	 * @return Number of fields per record.
	 */
	public int getNumberOfFieldsPerRecord() {
		return numberOfFieldsPerRecord;
	}

	/**
	 * Sets the number of fields per record.
	 * 
	 * @param numberOfFieldsPerRecord Number of fields per record.
	 */
	private void setNumberOfFieldsPerRecord(int numberOfFieldsPerRecord) {
		this.numberOfFieldsPerRecord = numberOfFieldsPerRecord;
	}

	/**
	 * Retrieves the data section.
	 * 
	 * @return A set of rows where all records are stored in database.
	 */
	public Set<Integer> getDataSection() {
		return dataSection;
	}
	
}

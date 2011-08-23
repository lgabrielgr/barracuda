package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Provides the access to perform the CRUD operations on the database file.
 * 
 * @author Leo Gutierrez
 */
public class Data implements DB {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = Data.class.getName();
	
	 /**
     * Thread manager.
     */
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    
    /**
     * Read Lock instance.
     */
    private Lock readLock = readWriteLock.readLock();
    
    /**
     * Write Lock instance.
     */
    private Lock writeLock = readWriteLock.writeLock();
    
    /**
     * Condition when locking a row.
     */
    private Condition lockNotReleased = writeLock.newCondition();
	
    /**
     * Contains all the record numbers that are locked.
     */
    private static final Map<Integer, Long> lock =
			new HashMap<Integer, Long>();
    
	/**
     * Cache that contains all the deleted record rows in the database.
     */
    private static final Queue<Integer> deletedRecordRows = 
    		new PriorityQueue<Integer>();
    
    /**
     * Cache that contains all valid records (not deleted) in the database.
     */
    private static final Map<Integer, String []> validRecords = 
    		new LinkedHashMap<Integer, String[]>();
  		
    /**
	 * Reference to the database file.
	 */
	private static RandomAccessFile database;
	
	/**
	 * Reference to the data file format.
	 */
	private static DataFileFormat dataFileFormat;
	
	/**
	 * Constructs a <code>Data</code> object, opening the connection
	 * with the database file.
	 * 
	 * @throws RuntimeException If can't access to the database file.
	 */
	public Data() throws RuntimeException {
		
		if (!isDBOpen()) {
			
			openDBConnection();
			
			loadCache();
			
		}
		
	}

	/**
	 * Loads to cache all records in the database, dividing the deleted 
	 * records from the valid records.
	 * 
	 * @throws RuntimeException If can't access to the database file.
	 */
	private void loadCache() throws RuntimeException {
		
		final String methodName = "loadCache";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		readLock.lock();
		try {
			
			dataFileFormat = new DataFileFormat(database);

			final Set<Integer> recordRows = dataFileFormat.getRecordRows();

			for (int currentRecordRow: recordRows) {
				
				database.seek(currentRecordRow);
				
				final int deletedRecord = database.readByte();
				if (deletedRecord == DatabaseConstants.DELETED_RECORD) {
					
					deletedRecordRows.add(currentRecordRow);
					
				} else {
					
					try {
						
						validRecords.put(currentRecordRow, 
								readFromFile(currentRecordRow));
						
					} catch (RecordNotFoundException e) {
						
						DatabaseLogger.warning(CLASS_NAME, methodName, 
								"Unable to load to cache a record: " + e.getMessage());
					}
					
				}
				
			}
			
		} catch (IOException e) {
			
			final String errorMessage = "Unable to load to cache all the " +
					"records from the database: " + e.getMessage();
			
			DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);
			
			throw new RuntimeException(errorMessage);
			
		} finally {
			readLock.unlock();
		}
		
	}

	/**
	 * Opens the database connections.
	 * 
	 * @throws RuntimeException If can't access to the database file.
	 */
	private void openDBConnection() throws RuntimeException {
		
		final String methodName = "Data";
		DatabaseLogger.entering(CLASS_NAME, methodName);

		final DatabaseProperties properties = new DatabaseProperties();

		try {

			database = new RandomAccessFile(properties.readDatabasePath(), 
					DatabaseConstants.READ_WRITE_ACCESS_MODE);

		} catch (FileNotFoundException e) {

			final String errorMessage = "Unable to open the database " +
					"connection: " + e.getMessage();

			DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);

			throw new RuntimeException(errorMessage);

		} finally {
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
			
		}
		
	}
	
	/**
	 * Verifies if already exists a database connection. There is not a 
	 * database connection if one of the following occurs:
	 * <br />If database object representation is null.
	 * <br />If any operation on the database cause an I/O Exception.
	 * 
	 * @return true if exists a database connection; false otherwise.
	 */
	private boolean isDBOpen() {
		
		final String methodName = "isDBOpen";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		boolean dbOpen = true;
		
		readLock.lock();
		try {
			if (database == null) {

				dbOpen = false;

			} else {

				try {
					database.length();
				} catch (IOException e) {
					dbOpen =  false;
				}

			}
		} finally {
			
			readLock.unlock();
			
			DatabaseLogger.exiting(CLASS_NAME, methodName, dbOpen);
		}
		
		return dbOpen;
		
	}
	
	/**
	 * Reads a record from the file. Returns an array where each
	 * element is a record value. 
	 * 
	 * @param recNo Record number to read from the database.
	 * @return An array where each element is a record value. 
	 * @throws RecordNotFoundException If the record is not found in the
	 *                                 database.
	 */
	public String[] read(final int recNo) throws RecordNotFoundException {
		
		final String methodName = "read";
		DatabaseLogger.entering(CLASS_NAME, methodName, recNo);
		
		readLock.lock();
		try {
			
			final String record[] = validRecords.get(recNo);
			
			if (record == null) {
				
				final String errorMessage = "Record not found: " + recNo;
				
				DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);
				
				throw new RecordNotFoundException(errorMessage);
			}
			
			return record;
					
		} finally {
			
			readLock.unlock();
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
		
	}

	/**
	 * Reads a record from the database file to be loaded to cache.
	 * 
	 * @param recNo Record position.
	 * @return Array of String representing the record.
	 * @throws RecordNotFoundException If the record is deleted or any I/O
	 *                                 error occurs.
	 */
	private String[] readFromFile(final int recNo) throws RecordNotFoundException {
		
		final String methodName = "readFromFile";
		DatabaseLogger.entering(CLASS_NAME, methodName, recNo);
		
		final String[] record = 
				new String[dataFileFormat.getNumberOfFieldsPerRecord()];
		
		try {
			
			final byte[] buffer = new byte[dataFileFormat.getRecordLength()];
			
			database.seek(recNo);
			database.read(buffer);
			
			final int deletedRecordIndex = Integer.valueOf(buffer[0]);
			if (deletedRecordIndex == DatabaseConstants.DELETED_RECORD) {
				
				final String errorMessage = "Record deleted: " + recNo;
				
				DatabaseLogger.warning(CLASS_NAME, methodName, errorMessage);
				
				throw new RecordNotFoundException(errorMessage);
				
			}
			
			int offset = 1;
			for (RecordField field: dataFileFormat.getRecordFields()) {
				
				final String recordFieldValue = 
						new String(buffer, offset, field.getFieldValueLength());
				
				record[field.getFieldPosition()] = recordFieldValue.trim();
				
				offset += field.getFieldValueLength();
				
			}
			
		} catch (IOException e) {
			
			final String errorMessage = "Unable to read record " + recNo + 
					"due to an I/O error: " + e.getMessage();
			
			DatabaseLogger.warning(CLASS_NAME, methodName, errorMessage);
			
			throw new RecordNotFoundException(errorMessage);
			
		} finally {
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
		
		return record;
	}

	/**
	 * Modifies the fields of a record. The new value for field n appears 
	 * in data[n]. Throws SecurityException if the record is locked with a 
	 * cookie other than lockCookie.
	 * <br /> The fields of a record must be as follow:
	 * <br />0 - Hotel name.
     * <br />1 - Location.
     * <br />2 - Room size.
     * <br />3 - Smoke.
     * <br />4 - Rate per night.
     * <br />5 - Date available.
     * <br />6 - Owner ID.
	 * 
	 * @param recNo Record number to update in the database.
	 * @param data Data to update the record.
	 * @param lockCookie Cookie value that owns the lock of the record.
	 * @throws RecordNotFoundException If the record is not found in the
	 *                                 database.
	 * @throws SecurityException If the record is locked with a 
	 *                           cookie other than lockCookie.
	 */
	public void update(int recNo, String[] data, long lockCookie)
			throws RecordNotFoundException, SecurityException {
		
		final String methodName = "update";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		writeLock.lock();
		try {
			
			if (deletedRecordRows.contains(recNo)) {
				
				final String errorMessage = "The record is deleted";
				
				DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);
				
				throw new RecordNotFoundException(errorMessage);
			}
			
			if (!isRecordLocked(recNo, lockCookie)) {
				
				final String errorMessage = "The given lock cookie does not " +
						"own the lock on record";

				DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);

				throw new SecurityException(errorMessage);
			}
			
			final byte[] record = dataToByteArray(data);
			
			database.seek(recNo);
			database.write(record);
			
			validRecords.put(recNo, data);
			
		} catch (IOException e) {
			
			final String errorMessage = "Unable to update record due to " +
					"an I/O error: " + e.getMessage();
			
			DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);
			
			throw new RecordNotFoundException(errorMessage);
			
		} finally {
			
			writeLock.unlock();
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
		
	}

	/**
	 * Returns a byte representation of the given record data to 
	 * insert/update into the database.
	 * 
	 * @param data Array of string that contains the record data.
	 * @return A byte representation of the given record data.
	 */
	private byte[] dataToByteArray(final String data[]) {
		
		final String methodName = "dataToByteArray";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		final StringBuilder byteArray = new StringBuilder();
		byteArray.append(DatabaseConstants.NOT_DELETED_RECORD);
		
		for (RecordField field: dataFileFormat.getRecordFields()) {
			
			final StringBuilder currentFieldValue = 
					new StringBuilder(data[field.getFieldPosition()]);
			
			while (currentFieldValue.length() < field.getFieldValueLength()) {
				currentFieldValue.append(DatabaseConstants.EMPTY_SPACE);
			}
			
			byteArray.append(currentFieldValue.toString());
			
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName);
		
		return byteArray.toString().getBytes();
	}

	/**
	 * Deletes a record, making the record number and associated disk storage 
	 * available for reuse. Throws SecurityException if the record is locked 
	 * with a cookie other than lockCookie.
	 * 
	 * @param recNo Record number to delete in the database.
	 * @param lockCookie Cookie value that owns the lock of the record.
	 * @throws RecordNotFoundException If the record is not found in the
	 *                                 database.
	 * @throws SecurityException If the record is locked with a cookie other 
	 *                           than lockCookie.
	 */
	public void delete(final int recNo, final long lockCookie)
			throws RecordNotFoundException, SecurityException {
		
		final String methodName = "delete";
		DatabaseLogger.entering(CLASS_NAME, methodName, recNo, lockCookie);
		
		writeLock.lock();
		try {
			
			if (deletedRecordRows.contains(recNo)) {
				
				final String errorMessage = "Record is already deleted";
				
				DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);
				
				throw new RecordNotFoundException(errorMessage);
			}
			
			if (!isRecordLocked(recNo, lockCookie)) {
				
				final String errorMessage = "The given lock cookie does not " +
						"own the lock on record";
				
				DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);
				
				throw new SecurityException(errorMessage);
			}
			
			database.seek(recNo);
			database.write((byte)DatabaseConstants.DELETED_RECORD);
			
			deletedRecordRows.add(recNo);
			validRecords.remove(recNo);
			
		} catch (IOException e) {

			final String errorMessage = "Unable to delete record due to an I/O " +
					"error: " + e.getMessage();
			
			DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);
			
			throw new RecordNotFoundException(errorMessage);
			
		} finally {
			
			writeLock.unlock();
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}

	}

	/**
	 * Returns an array of record numbers that match the specified criteria. 
	 * A null value in criteria[n] matches any field value. A non-null value in criteria[n] 
	 * matches any field value that exactly matches with criteria[n]. 
	 * <br />The criteria content must be as the follow order:
	 * <br />0 - Hotel name.
     * <br />1 - Location.
     * <br />2 - Room size.
     * <br />3 - Smoke.
     * <br />4 - Rate per night.
     * <br />5 - Date available.
     * <br />6 - Owner ID.
	 * <br />If the given criteria does not match as above, an empty array is 
	 * returned.
	 * 
	 * @param criteria Array containing the search criteria, or null if 
	 *                 want all records numbers in the database.
	 * @return An array containing all the records numbers found.
	 */
	public int[] find(final String[] criteria) {
		
		final String methodName = "find";
		DatabaseLogger.entering(CLASS_NAME, methodName);
	
		readLock.lock();
		try {
			
			return searchCriteria(criteria);
			
		} finally {
			
			readLock.unlock();
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
		
	}

	/**
	 * Performs a search into all valid records (not deleted) into the 
	 * database filtering with the given criteria. If criteria is null, all 
	 * the valid records are returned.
	 * <br />The criteria content must be as the follow order:
	 * <br />0 - Hotel name.
     * <br />1 - Location.
     * <br />2 - Room size.
     * <br />3 - Smoke.
     * <br />4 - Rate per night.
     * <br />5 - Date available.
     * <br />6 - Owner ID.
	 * <br />If the given criteria does not match as above, an empty array is 
	 * returned.
	 * 
	 * @param criteria Criteria to apply in the search. Can be null.
	 * @return An array that contains all the record rows found during the 
	 *         searching.
	 */
	private int[] searchCriteria(final String [] criteria) {
		
		final String methodName = "searchCriteria";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		Set<Integer> filteredRowsFound = null;
		
		if (criteria == null) {
			
			filteredRowsFound = validRecords.keySet();
			
		} else if (criteria.length < 
				dataFileFormat.getNumberOfFieldsPerRecord() ) {
			
			filteredRowsFound = new TreeSet<Integer>();
			
		} else {
			
			filteredRowsFound = searchFilterByCriteria(criteria);
			
		}
		
		int [] recordRowsFound = new int[filteredRowsFound.size()];
		
		int index = 0;
		for (int currentRow: filteredRowsFound) {
			recordRowsFound[index++] = currentRow;
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName);
		
		return recordRowsFound;		
	}

	/**
	 * Performs a search into the database filtering by the given criteria.
	 * <br />The criteria content must be as the follow order:
	 * <br />0 - Hotel name.
     * <br />1 - Location.
     * <br />2 - Room size.
     * <br />3 - Smoke.
     * <br />4 - Rate per night.
     * <br />5 - Date available.
     * <br />6 - Owner ID.
	 * <br />If the given criteria does not match as above, an empty list is 
	 * returned.
	 * 
	 * @param criteria Criteria to apply in the search filter.
	 * @return A list that contains all the record rows found during the 
	 *         searching with the criteria applied.
	 */
	private Set<Integer> searchFilterByCriteria(final String[] criteria) {
		
		final String methodName = "searchFilterByCriteria";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			final Set<Integer> filteredRowsFound = new TreeSet<Integer>();

			if (criteria.length < 
					dataFileFormat.getNumberOfFieldsPerRecord()) {
				return filteredRowsFound;
			}
			
			final Set<Entry<Integer, String[]>> allRecords = 
					validRecords.entrySet();

			for (Entry<Integer, String[]> currentRecord: allRecords) {

				final int recordRow = currentRecord.getKey();
				final String [] recordData = currentRecord.getValue();

				boolean match = true;

				for (int index = 0; 
						index < dataFileFormat.getNumberOfFieldsPerRecord(); 
						index++) {

					if (!matchCriteria(recordData[index], criteria[index])) {
						match = false;
						break;
					}

				}

				if (match) {
					filteredRowsFound.add(recordRow);
				}
			}
			
			return filteredRowsFound;
			
		} finally {
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
	
	}
	
	/**
	 * Verifies if the given record field value matches with the given 
	 * criteria. If the criteria is null, returns true.
	 * 
	 * @param recordFieldSource Record field to compare with the criteria.
	 * @param recordFieldCriteria Criteria to apply in comparison.
	 * @return True if record field value matches with the criteria or
	 *         if criteria is null; otherwise, false.
	 */
	private boolean matchCriteria(final String recordFieldSource, 
			final String recordFieldCriteria) {
		
		final String methodName = "matchCriteria";
		DatabaseLogger.entering(CLASS_NAME, methodName, recordFieldSource, 
				recordFieldCriteria);
		
		boolean match = false;
		
		if (recordFieldCriteria == null) {
			match = true;
		} else {
			match = recordFieldsEqual(recordFieldSource, recordFieldCriteria);
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName, match);
		
		return match;
	}
	
	/**
	 * Creates a new record in the database (possibly reusing a deleted entry). 
	 * Inserts the given data, and returns the record number of the new record
	 * if created successfully, otherwise return -1.
	 * <br /> The fields of a record to insert must be as follow:
	 * <br />0 - Hotel name.
     * <br />1 - Location.
     * <br />2 - Room size.
     * <br />3 - Smoke.
     * <br />4 - Rate per night.
     * <br />5 - Date available.
     * <br />6 - Owner ID.
	 * 
	 * @param data Array that contains the data to be inserted as a new record.
	 * @return The record number of the new record if created successfully; 
	 *         otherwise, return -1.
	 * @throws DuplicateKeyException If the record already exists in the 
	 *                               database.
	 */
	public int create(final String[] data) throws DuplicateKeyException {
		
		final String methodName = "create";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		int newRecordRow = -1;
		
		writeLock.lock();
		try {
			
			if (isRecordDuplicated(data)) {
				
				final String errorMessage = "Record already exists in " +
						"database";
				
				DatabaseLogger.severe(CLASS_NAME, errorMessage, errorMessage);
				
				throw new DuplicateKeyException(errorMessage);
			}
			
			final byte [] newRecord = dataToByteArray(data);
			newRecordRow = locateNewRecordRow();
			
			database.seek(newRecordRow);
			database.write(newRecord);
			
			validRecords.put(newRecordRow, data);
			
		} catch (IOException e) {
			
			newRecordRow = -1;
			
			DatabaseLogger.severe(CLASS_NAME, methodName, 
					"Unable to create record due to an I/O error: " + e.getMessage());
			
		} finally {
			
			writeLock.unlock();
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
		
		return newRecordRow;
	}

	/**
	 * Locates and returns a new record row to insert into the database.
	 * 
	 * @return A new record row to insert into the database.
	 * @throws IOException If can't access to the database.
	 */
	private int locateNewRecordRow() throws IOException {
		
		final String methodName = "calculateNewRecordRow";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		int newRecordRow = 0;
		
		if (deletedRecordRows.isEmpty()) {
			
			newRecordRow = (int)database.length();
			
		} else {
			
			newRecordRow = deletedRecordRows.poll();
			
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName, newRecordRow);
		
		return newRecordRow;
	}
	
	/**
	 * Verifies if the given record data already exists into the 
	 * database (the owner id field is not checked).
	 * 
	 * @param dataToCompare Record data to verify.
	 * @return True if already exists; otherwise, false.
	 */
	private boolean isRecordDuplicated(final String dataToCompare[]) {

		final String methodName = "isRecordDuplicated";
		DatabaseLogger.entering(CLASS_NAME, methodName);
		
		boolean recordDuplicated = true;
		
		final Set<Integer> recordRows = validRecords.keySet();
		
		for (int currentRecordRow: recordRows) {
			
			recordDuplicated = true;
			
			final String [] record = validRecords.get(currentRecordRow);
			
			for (int fieldIndex = 0; 
					fieldIndex < (dataFileFormat.getNumberOfFieldsPerRecord() -1);
					fieldIndex++) {
				
				if (!recordFieldsEqual(record[fieldIndex], 
						dataToCompare[fieldIndex])) {
					recordDuplicated = false;
					break;
				}
				
			}
			
			if (recordDuplicated) {
				break;
			} 
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName);
		
		return recordDuplicated;
	}
	
	/**
	 * Verifies if the given two strings (representing a record field value) 
	 * are equal or not.
	 * Null values are initialized to empty string.
	 * 
	 * @param fieldSource Record field value source.
	 * @param fieldToCompare Record field value to compare.
	 * @return True if they are equal; otherwise, false.
	 */
	private boolean recordFieldsEqual(String fieldSource, 
			String fieldToCompare) {
		
		final String methodName = "recordFieldsEqual";
		DatabaseLogger.entering(CLASS_NAME, methodName, fieldSource, 
				fieldToCompare);
		
		if (fieldSource == null) {
			fieldSource = "";
		}
		
		if (fieldToCompare == null) {
			fieldToCompare = "";
		}
		
		final boolean fieldsEqual = fieldSource.equals(fieldToCompare);
		
		DatabaseLogger.exiting(CLASS_NAME, methodName, fieldsEqual);
		
		return fieldsEqual;
		
	}
	
	/**
	 * Locks a record so that it can only be updated or deleted by this client.
	 * Returned value is a cookie that must be used when the record is unlocked,
	 * updated, or deleted. If the specified record is already locked by a different
	 * client, the current thread gives up the CPU and consumes no CPU cycles until
	 * the record is unlocked.
	 * 
	 * @param recNo Record number to be locked.
	 * @return Cookie value that owns the lock on the record.
	 * @throws RecordNotFoundException If the record is not found in the database.
	 */
	public long lock(int recNo) throws RecordNotFoundException {
		
		final String methodName = "lock";
		DatabaseLogger.entering(CLASS_NAME, methodName, recNo);
		
		final long lockNumber = 
				(long) (Math.random() * System.currentTimeMillis());
		
		writeLock.lock();
		try {
			
			while (lock.containsKey(recNo)) {
				try {
					lockNotReleased.await();
				} catch (InterruptedException e) {

					final String errorMessage = "Unexpected interrumption has " +
							"occurs during the waiting for the lock release for " +
							"the record: " + recNo;
					
					DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);
					
					throw new RecordNotFoundException(errorMessage);
					
				}
			}
			
			
			if (!validRecords.containsKey(recNo)) {
				
				final String errorMessage = "Unable to locate record: " + recNo;
				
				DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);
				
				throw new RecordNotFoundException(errorMessage);
				
			}
			
			lock.put(recNo, lockNumber);
			
		} finally {
			
			writeLock.unlock();
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
		
		return lockNumber;
	}

	/**
	 * Releases the lock on a record. Cookie must be the cookie returned when 
	 * the record was locked; otherwise throws SecurityException.
	 * 
	 * @param recNo Record number to be unlocked.
	 * @param cookie Cookie value that owns the lock on the record.
	 * @throws RecordNotFoundException If the record is not found in the 
	 *                                 database.
	 * @throws SecurityException If cookie value does not own the lock on the
	 *                           record.
	 */
	public void unlock(final int recNo, 
			final long cookie) throws RecordNotFoundException, SecurityException {
		
		final String methodName = "unlock";
		DatabaseLogger.entering(CLASS_NAME, methodName, recNo, cookie);
		
		writeLock.lock();
		try {
			
			if (!isRecordLocked(recNo, cookie)) {

				final String errorMessage = "Cookie value " + cookie + " does not " +
						"own the lock on record " + recNo;

				DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);

				throw new SecurityException(errorMessage);

			}

			lock.remove(recNo);
			lockNotReleased.signalAll();
			
		} finally {
			
			writeLock.unlock();
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
		}
	}

	/**
	 * Verifies if the given record is locked with the given lock value.
	 * 
	 * @param recNo Record number to verify.
	 * @param cookie Lock value to verify.
	 * @return True if the given record is locked with the given lock value; 
	 *         otherwise, false.
	 */
	private boolean isRecordLocked(final int recNo, final long cookie) {
		
		final String methodName = "isRecordLocked";
		DatabaseLogger.entering(CLASS_NAME, methodName, recNo, cookie);
		
		boolean recordLocked = true;
		
		try {
			
			final Long lockValue = lock.get(recNo);

			if (lockValue == null) {

				recordLocked = false;

			} else {

				recordLocked = lockValue.equals(cookie);

			}
			
		} finally {
			DatabaseLogger.exiting(CLASS_NAME, methodName, recordLocked);
		}
		
		return recordLocked;
	}
}
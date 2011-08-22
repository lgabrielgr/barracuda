package suncertify.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
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
     * Cache that contains all the deleted record rows in the database.
     */
    private static final Queue <Integer> deletedRecordRows = 
    		new PriorityQueue <Integer>();
    
    /**
     * Cache that contains all valid records (not deleted) in the database.
     */
    private static final Map < Integer, String [] > validRecords = 
    		new HashMap <Integer, String[]>();
    		
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
	 * with the database.
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

			final Set<Integer> recordRows = dataFileFormat.getDataSection();

			for (int currentRecordRow: recordRows) {
				
				database.seek(currentRecordRow);
				
				final int deletedRecord = database.readByte();
				if (deletedRecord == DatabaseConstants.DELETED_RECORD) {
					
					deletedRecordRows.add(currentRecordRow);
					
				} else {
					
					try {
						
						validRecords.put(currentRecordRow, read(currentRecordRow));
						
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
		
		if (database == null) {
			
			dbOpen = false;
			
		} else {

			try {
				database.read();
			} catch (IOException e) {
				dbOpen =  false;
			}

		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName, dbOpen);
		
		return dbOpen;
		
	}
	
	/* (non-Javadoc)
	 * @see suncertify.db.DB#read(int)
	 */
	@Override
	public String[] read(int recNo) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see suncertify.db.DB#update(int, java.lang.String[], long)
	 */
	@Override
	public void update(int recNo, String[] data, long lockCookie)
			throws RecordNotFoundException, SecurityException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see suncertify.db.DB#delete(int, long)
	 */
	@Override
	public void delete(int recNo, long lockCookie)
			throws RecordNotFoundException, SecurityException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see suncertify.db.DB#find(java.lang.String[])
	 */
	@Override
	public int[] find(String[] criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see suncertify.db.DB#create(java.lang.String[])
	 */
	@Override
	public int create(String[] data) throws DuplicateKeyException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see suncertify.db.DB#lock(int)
	 */
	@Override
	public long lock(int recNo) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see suncertify.db.DB#unlock(int, long)
	 */
	@Override
	public void unlock(int recNo, long cookie) throws RecordNotFoundException,
			SecurityException {
		// TODO Auto-generated method stub

	}

}
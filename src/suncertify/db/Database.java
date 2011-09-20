package suncertify.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Exposes the CRUD operations to perform on the database.
 * 
 * @author Leo Gutierrez
 */
public class Database implements IDatabase {

	/**
	 * Class name.
	 */
	final static private String CLASS_NAME = Database.class.getName();
	
	/**
	 * Reference to the database object.
	 */
	final private DB database = new Data(); 
	
	/**
	 * Constructs a <code>Database</code> object.
	 */
	public Database() {
		
	}
	
	/**
	 * Creates a new record into the database.
	 * 
	 * @param record Record data to save into the database.
	 * @return Database position where the record was saved. 
	 * @throws DuplicateKeyException If the record already exists in the 
	 *                               database.
	 */
	public int create(Record record) throws DuplicateKeyException {
		
		final String methodName = "create";
		DatabaseLogger.entering(CLASS_NAME, methodName, record);
		
		int recordRow = -1;
		
		try {
		
			final String [] recordData = record.toStringArray();
			
			recordRow = database.create(recordData);
			
			return recordRow;
		
		} finally {
			
			DatabaseLogger.exiting(CLASS_NAME, methodName, recordRow);
			
		}
		
	}


	/**
	 * Deletes the specified record in the database.
	 * 
	 * @param recordRow Record position where to delete.
	 * @throws RecordNotFoundException If the record is not found or already 
	 *                                 deleted.
	 */
	public void delete(int recordRow) throws RecordNotFoundException {
		
		final String methodName = "delete";
		DatabaseLogger.entering(CLASS_NAME, methodName, recordRow);

		final long lockCookie = database.lock(recordRow);
		
		try {
			
			database.delete(recordRow, lockCookie);
			
		} finally {
			
			database.unlock(recordRow, lockCookie);
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
			
		}
		
	}

	/**
	 * Updates the specified record position with he given record data.
	 * 
	 * @param recordRow Record position where to update.
	 * @param record Record data to update.
	 * @throws RecordNotFoundException If the record is not found or it is 
	 *                                 deleted.
	 */
	public void update(int recordRow, Record record)
			throws RecordNotFoundException {
		
		final String methodName = "update";
		DatabaseLogger.entering(CLASS_NAME, methodName, recordRow, record);

		final long lockCookie = database.lock(recordRow);
		
		try {
			
			if (isRoomBooked(recordRow)) {
				
				final String errorMessage = "Unable to update the record, " +
						"the room is already booked";
				
				DatabaseLogger.severe(CLASS_NAME, methodName, errorMessage);
				
				throw new RecordNotFoundException(errorMessage);
				
			}
			
			final String [] recordData = record.toStringArray();
			
			database.update(recordRow, recordData, lockCookie);
			
		} finally {
			
			database.unlock(recordRow, lockCookie);
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
			
		}
		
	}

	/**
	 * Reads a record in the specified record position.
	 * 
	 * @param recordRow Record position where to read.
	 * @return The record read.
	 * @throws RecordNotFoundException If the record is not found or it is
	 * 								   deleted.
	 */
	public Record read(int recordRow) throws RecordNotFoundException {
		
		final String methodName = "read";
		DatabaseLogger.entering(CLASS_NAME, methodName, recordRow);
		
		final Record record = new Record();
		
		try {
			
			final String [] recordData = database.read(recordRow);

			record.setName(recordData[Record.NAME_FIELD_INDEX]);
			record.setLocation(recordData[Record.LOCATION_FIELD_INDEX]);
			record.setSize(recordData[Record.SIZE_FIELD_INDEX]);
			record.setSmoking(recordData[Record.SMOKING_FIELD_INDEX]);
			record.setRate(recordData[Record.RATE_FIELD_INDEX]);
			record.setDate(recordData[Record.DATE_FIELD_INDEX]);
			record.setOwner(recordData[Record.OWNER_FIELD_INDEX]);
			record.setDatabaseRow(recordRow);
			
			return record;
			
		} finally {
			
			DatabaseLogger.exiting(CLASS_NAME, methodName, record);
			
		}
	}

	/**
	 * Locates and retrieves record(s) in the database that exactly match with
	 * the given name and location values. 
	 * <br />The values can be null:
	 * <br />- If name is null, all name values in database will match.
	 * <br />- If location is null, all location values in database will match.
	 * <br />- If both are null, all records are retrieved.
	 * 
	 * @param name Name to search. Can be null (will match any name value).
	 * @param location Location to search. Can be null (will match any 
	 *                 location value).
	 * @return Record(s) found during the search. If no record is found, an 
	 *         empty list is returned.
	 */
	public List<Record> find(String name, String location) {
		
		final String methodName = "find";
		DatabaseLogger.entering(CLASS_NAME, methodName, name, location);
		
		try {
			
			final String [] criteria = new String [Record.TOTAL_RECORD_FIELDS];
			
			criteria[Record.NAME_FIELD_INDEX] = name;
			criteria[Record.LOCATION_FIELD_INDEX] = location;
			
			final int [] recordRowsFound = database.find(criteria);
			
			final List<Record> records = new ArrayList<Record>();
			
			for (int recordRow: recordRowsFound) {
				
				try {
					
					records.add(read(recordRow));
					
				} catch (RecordNotFoundException e) {
					
					DatabaseLogger.warning(CLASS_NAME, methodName, 
							"Unable to read record: " + e.getMessage());
					
				}
				
			}
			
			return records;
			
		} finally {
			
			DatabaseLogger.exiting(CLASS_NAME, methodName);
			
		}
		
	}
	
	/**
	 * Verifies if the room of the given record is already booked.
	 * 
	 * @param recordRow Record to verify the room.
	 * @return True if the room is already booked; False otherwise.
	 * @throws RecordNotFoundException If the record is not found or it is
	 *                                 deleted.
	 */
	private boolean isRoomBooked(final int recordRow) 
			throws RecordNotFoundException {
		
		boolean roomBooked = false;
		
		final Record record = read(recordRow);
		
		String owner = record.getOwner();
		
		if (owner == null) {
			owner = "";
		}
		
		if (!"".equals(owner.trim())) {
			roomBooked = true; 
		}
		
		return roomBooked;
		
	}
	
}

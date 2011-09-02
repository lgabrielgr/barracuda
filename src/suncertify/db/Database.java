package suncertify.db;

import java.util.Set;

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
		
			final String [] recordData = 
					new String [Record.TOTAL_RECORD_FIELDS];
			
			recordData[Record.NAME_FIELD_INDEX] = record.getName();
			recordData[Record.LOCATION_FIELD_INDEX] = record.getLocation();
			recordData[Record.SIZE_FIELD_INDEX] = record.getSize();
			recordData[Record.SMOKING_FIELD_INDEX] = record.getSmoking();
			recordData[Record.RATE_FIELD_INDEX] = record.getRate();
			recordData[Record.DATE_FIELD_INDEX] = record.getDate();
			recordData[Record.OWNER_FIELD_INDEX] = record.getOwner();
			
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
		// TODO Auto-generated method stub

	}

	@Override
	public void update(int recordRow, Record record)
			throws RecordNotFoundException {
		// TODO Auto-generated method stub

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

	@Override
	public Set<Record> find(String name, String location) {
		// TODO Auto-generated method stub
		return null;
	}

}

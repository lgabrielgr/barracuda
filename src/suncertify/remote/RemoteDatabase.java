package suncertify.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import suncertify.db.Database;
import suncertify.db.DuplicateKeyException;
import suncertify.db.IDatabase;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;

public class RemoteDatabase extends UnicastRemoteObject implements IDatabase {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 8974663L;

	/**
	 * Reference to the database object.
	 */
	private final Database database = new Database();
	
	/**
	 * Constructs a <code>RemoteDatabase</code> object.
	 * 
	 * @throws RemoteException If any networking error occurs.
	 */
	public RemoteDatabase() throws RemoteException {
		super();
	}
	
	/**
	 * Creates a new record into the database.
	 * 
	 * @param record Record data to save into the database.
	 * @return Database position where the record was saved. 
	 * @throws DuplicateKeyException If the record already exists in the 
	 *                               database.
	 * @throws RemoteException If any networking error occurs.
	 */
	public int create(final Record record) throws DuplicateKeyException,
			RemoteException {
		
		return database.create(record);
		
	}

	/**
	 * Deletes the specified record in the database.
	 * 
	 * @param recordRow Record position where to delete.
	 * @throws RecordNotFoundException If the record is not found or already 
	 *                                 deleted.
	 * @throws RemoteException If any networking error occurs.
	 */
	public void delete(final int recordRow) throws RecordNotFoundException,
			RemoteException {

		database.delete(recordRow);

	}

	/**
	 * Updates the specified record position with he given record data.
	 * 
	 * @param recordRow Record position where to update.
	 * @param record Record data to update.
	 * @throws RecordNotFoundException If the record is not found or it is 
	 *                                 deleted.
	 * @throws RemoteException If any networking error occurs.
	 */
	public void update(final int recordRow, final Record record)
			throws RecordNotFoundException, RemoteException {
		
		database.update(recordRow, record);
		
	}

	/**
	 * /**
	 * Reads a record in the specified record position.
	 * 
	 * @param recordRow Record position where to read.
	 * @return The record read.
	 * @throws RecordNotFoundException If the record is not found or it is
	 * 								   deleted.
	 * @throws RemoteException If any networking error occurs.
	 */
	public Record read(final int recordRow) throws RecordNotFoundException,
			RemoteException {
		
		return database.read(recordRow);
		
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
	 * @throws RemoteException If any networking error occurs.
	 */
	public List<Record> find(final String name, final String location)
			throws RemoteException {
		
		return database.find(name, location);
		
	}

}

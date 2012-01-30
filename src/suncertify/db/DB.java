package suncertify.db;

/**
 * Models the access to the database's operations.
 * <br />This interface is provided by Sun Microsystems as part of
 * the requirements.
 * 
 * @author Sun Microsystems.
 */
public interface DB {
	
	/**
	 * Reads a record from the file. Returns an array where each
	 * element is a record value. 
	 * 
	 * @param recNo Record number to read from the database.
	 * @return An array where each element is a record value. 
	 * @throws RecordNotFoundException If the record is not found in the
	 *                                 database.
	 */
	String[] read(int recNo) throws RecordNotFoundException;

	/**
	 * Modifies the fields of a record. The new value for field n appears 
	 * in data[n]. Throws SecurityException if the record is locked with a 
	 * cookie other than lockCookie.
	 * 
	 * @param recNo Record number to update in the database.
	 * @param data Data to update the record.
	 * @param lockCookie Cookie value that owns the lock of the record.
	 * @throws RecordNotFoundException If the record is not found in the
	 *                                 database.
	 * @throws SecurityException If the record is locked with a 
	 *                           cookie other than lockCookie.
	 */
	void update(int recNo, String[] data, long lockCookie)
	throws RecordNotFoundException, SecurityException;

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
	void delete(int recNo, long lockCookie)
	throws RecordNotFoundException, SecurityException;

	/**
	 * Returns an array of record numbers that match the specified criteria. 
	 * Field n in the database file is described by criteria[n]. A null value 
	 * in criteria[n] matches any field value. A non-null value in criteria[n] 
	 * matches any field value that begins with criteria[n]. 
	 * (For example, "Fred" matches "Fred" or "Freddy".)
	 * 
	 * @param criteria Array containing the search criteria.
	 * @return An array containing all the records numbers found.
	 */
	int[] find(String[] criteria);

	/**
	 * Creates a new record in the database (possibly reusing a deleted 
	 * entry). Inserts the given data, and returns the record number of 
	 * the new record.
	 * 
	 * @param data Array that contains the data to be inserted as a new record.
	 * @return The record number of the new record.
	 * @throws DuplicateKeyException If the record already exists in the 
	 *                               database.
	 */
	int create(String[] data) throws DuplicateKeyException;

	/**
	 * Locks a record so that it can only be updated or deleted by this client.
	 * Returned value is a cookie that must be used when the record is 
	 * unlocked, updated, or deleted. If the specified record is already locked 
	 * by a different client, the current thread gives up the CPU and consumes 
	 * no CPU cycles until the record is unlocked.
	 * 
	 * @param recNo Record number to be locked.
	 * @return Cookie value that owns the lock on the record.
	 * @throws RecordNotFoundException If the record is not found in the 
	 *                                 database.
	 */
	long lock(int recNo) throws RecordNotFoundException;

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
	void unlock(int recNo, long cookie)
	throws RecordNotFoundException, SecurityException;

} 

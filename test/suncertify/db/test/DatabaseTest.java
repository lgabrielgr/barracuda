package suncertify.db.test;


import junit.framework.TestCase;
import suncertify.db.Database;
import suncertify.db.DuplicateKeyException;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;

public class DatabaseTest extends TestCase {

	final Database database = new Database();
	
	public void testReadExistingRecord() {
		
		try {
			
			database.read(74);
			
			assertTrue(true);
				
		} catch (RecordNotFoundException e) {
			
			fail(e.getMessage());
			
		}
		
	}
	
	public void testReadNotExistingRecord() {
		
		try {
			
			database.read(12345);
			
			fail("Record read");
			
		} catch (RecordNotFoundException e) {
			
			assertTrue(true);
			
		}
		
	}
	
	public void testCreate() {
		
		final Record record = new Record();
		record.setName("Riu");
		record.setLocation("Mazatlan");
		record.setSize("2");
		record.setSmoking("Y");
		record.setRate("$456.12");
		record.setDate("2011/09/02");
		record.setOwner("1");
		
		try {
			
			final int recordRow = database.create(record);
			
			database.read(recordRow);
			
		} catch (DuplicateKeyException e) {
			
			fail(e.getMessage());
			
		} catch (RecordNotFoundException e) {
			
			fail(e.getMessage());
			
		}
	}
	
	public void testCreateDuplicated() {
		
		try {
			
			final Record record = database.read(74);
			
			database.create(record);
			
			fail("Duplicated record created");
			
		} catch (RecordNotFoundException e) {
			
			fail(e.getMessage());
			
		} catch (DuplicateKeyException e) {
			
			assertTrue(true);
			
		}
		
	}
	
}

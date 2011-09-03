package suncertify.db.test;


import java.util.Set;

import junit.framework.TestCase;
import suncertify.db.Database;
import suncertify.db.DuplicateKeyException;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;

public class DatabaseTest extends TestCase {

	final Database database = new Database();
	
	public void testReadExistingRecord() {
		
		try {
			
			System.out.println(database.read(74));
			
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
	
	public void testUpdate() {
		
		try {
			
			Record record = database.read(74);
			
			record.setLocation("Guadalajara");
			
			database.update(74, record);
			
			record = database.read(74);
			
			assertEquals("Guadalajara", record.getLocation());
			
		} catch (RecordNotFoundException e) {
			
			fail(e.getMessage());
			
		}
		
		
	}
	
	public void testUpdateRoomBooked() {
		
		try {

			Record record = database.read(4714);

			record.setOwner("0");

			database.update(4714, record);

			record = database.read(4714);
			
			record.setOwner("1");
			
			database.update(4714, record);
			
			fail("Record already booked updated");
			
		} catch (RecordNotFoundException e) {
			
			assertTrue(true);
			
		}
		
	}
	
	public void testFind() {
		
		Set<Record> records = database.find("Palace", null);
		
		assertEquals(3, records.size());
		
		records = database.find(null, "Hobbiton");
		
		assertEquals(3, records.size());
		
		records = database.find("Grandview", "Hobbiton");

		assertEquals(1, records.size());
	}
	
}

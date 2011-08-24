package suncertify.db.test;

import suncertify.db.Record;
import junit.framework.TestCase;

public class RecordValidatorTest extends TestCase {

	public void testValidRecord() {
		
		try {
			
			final Record record = new Record();
			record.setName("Motel Perron");
			record.setLocation("Mazatlan");
			record.setDate("2011/08/24");
			record.setSize("3");
			record.setSmoking("Y");
			record.setRate("$500.50");
			record.setOwner("1");
			
			assertTrue(true);
			
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}
	}
	
	public void testInvalidRecordName() {
		
		try {
			
			final Record record = new Record();
			record.setName("");
			record.setName(null);
			
			fail("Can set a invalid hotel name");
			
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	public void testInvalidRecordLocation() {

		try {

			final Record record = new Record();
			record.setLocation("");
			record.setLocation(null);

			fail("Can set a invalid hotel location");

		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	public void testInvalidRecordDate() {

		try {

			final Record record = new Record();
			record.setDate("2011-08-24");
			record.setDate("");

			fail("Can set a invalid date");

		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	public void testInvalidRecordRate() {

		try {

			final Record record = new Record();
			record.setRate("111.11");
			record.setRate("");

			fail("Can set a invalid rate");

		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	public void testInvalidRecordSize() {

		try {

			final Record record = new Record();
			record.setSize("0");
			record.setSize(null);

			fail("Can set a invalid room size");

		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	public void testInvalidRecordSmoking() {

		try {

			final Record record = new Record();
			record.setSmoking("S");
			record.setSmoking(null);

			fail("Can set a invalid smoking");

		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	public void testInvalidRecordOwner() {

		try {

			final Record record = new Record();
			record.setOwner("-1");
			record.setOwner(null);

			fail("Can set a invalid owner");

		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
}
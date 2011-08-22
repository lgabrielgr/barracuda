package suncertify.db.test;

import junit.framework.TestCase;
import suncertify.db.Data;
import suncertify.db.RecordNotFoundException;

public class DataTest extends TestCase {

	public void testRead() {
		final Data data = new Data();
		try {
			assertNotNull(data.read(1));
		} catch (RecordNotFoundException e) {
			fail(e.getMessage());
		}
	}
	
}

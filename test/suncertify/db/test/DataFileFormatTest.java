package suncertify.db.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import suncertify.db.DataFileFormat;

public class DataFileFormatTest extends TestCase {

	private RandomAccessFile database;
	
	private DataFileFormat dataFileFormat;
	
	@Before
	public void setUp() {
		try {
			database = 
					new RandomAccessFile("C:\\Documents and Settings\\Administrator\\Desktop\\PBC Goals\\OCJD\\db\\db-1x1.db", "r");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		dataFileFormat = new DataFileFormat(database);
	}
	
	@After
	public void tearDown() {
		if (database != null) {
			try {
				database.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@Test
	public void testDataFileFormat() {
		assertEquals(257, dataFileFormat.getMagicNumber());
		assertEquals(7, dataFileFormat.getNumberOfFieldsPerRecord());
		assertEquals(159, dataFileFormat.getRecordLength());
		assertFalse(dataFileFormat.getDataSection().isEmpty());
	}
}

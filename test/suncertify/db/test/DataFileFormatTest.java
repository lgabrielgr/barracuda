package suncertify.db.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import junit.framework.TestCase;

import suncertify.db.DataFileFormat;

public class DataFileFormatTest extends TestCase {

	private RandomAccessFile database;
	
	private DataFileFormat dataFileFormat;
	
	public void setUp() {
		try {
			database = 
					new RandomAccessFile("C:\\db-1x1.db", "r");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		dataFileFormat = new DataFileFormat(database);
	}
	
	public void tearDown() {
		if (database != null) {
			try {
				database.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void testDataFileFormat() {
		assertEquals(257, dataFileFormat.getMagicNumber());
		assertEquals(7, dataFileFormat.getNumberOfFieldsPerRecord());
		assertEquals(160, dataFileFormat.getRecordLength());
		assertFalse(dataFileFormat.getRecordFields().isEmpty());
		assertFalse(dataFileFormat.getRecordRows().isEmpty());
		
		for (int row: dataFileFormat.getRecordRows()) {
			System.out.println(row);
		}
	}
}

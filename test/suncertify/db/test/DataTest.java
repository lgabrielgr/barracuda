package suncertify.db.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import junit.framework.TestCase;
import suncertify.db.Data;
import suncertify.db.DatabaseProperties;
import suncertify.db.DuplicateKeyException;
import suncertify.db.RecordNotFoundException;

public class DataTest extends TestCase {

	final Data data = new Data();
	
	public void testRead() {
		
		try {
			assertNotNull(data.read(74));
			
			assertNotNull(data.read(394));
			
			assertNotNull(data.read(4874));
			
		} catch (RecordNotFoundException e) {
			fail(e.getMessage());
		}
	}
	
	public void testLockUnlock() {
		try {
			
			final long lockValue = data.lock(74);
			data.unlock(74, lockValue);
			assertTrue(true);
			
		} catch (RecordNotFoundException e) {
			fail(e.getMessage());
		}
		
	}
	
	/**
	 * Print to console on Data.lock() and Data.unlock() when records
	 * are locked and unlocked to see in a clearest way how it is working.
	 */
	public void testLockUnlockMultiThread() {
		
		final Runnable r1 = new Runnable() {

			@Override
			public void run() {
				try {
					
					final long lockValue74 = data.lock(74);
					final long lockValue394 = data.lock(394);
					Thread.sleep(5000);
					data.unlock(394, lockValue394);
					data.unlock(74, lockValue74);
					
				} catch (RecordNotFoundException e) {
					System.out.println(e.getMessage());
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
			
		};
		
		try {
			
			final Thread t1 = new Thread(r1);
			t1.start();
			
			Thread.sleep(1000);
			
			final long lockValue4874 = data.lock(4874);
			data.unlock(4874, lockValue4874);
			
			final long lockValue74_2 = data.lock(74);
			data.unlock(74, lockValue74_2);
			
			assertTrue(true);

		} catch (RecordNotFoundException e) {
			fail(e.getMessage());
		} catch (InterruptedException e) {
			fail(e.getMessage());
		}
	}
	
	public void testDelete() {
		
		try {
			
			final long lockValue = data.lock(1514);
			
			data.delete(1514, lockValue);
			
			data.unlock(1514, lockValue);
			
			try {
				
				data.read(1514);
				assertTrue("Record not deleted", false);
				
			} catch (RecordNotFoundException e) {
				assertTrue(true);
				
				// Rollback
				final DatabaseProperties properties = new DatabaseProperties();
				try {

					final RandomAccessFile database = 
							new RandomAccessFile(properties.readDatabasePath(), "rw");
					database.seek(1514);
					database.write((byte)0);

					database.close();
				} catch (FileNotFoundException e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
			}
			
		} catch (RecordNotFoundException e) {
			fail(e.getMessage());
		}
		
	}
	
	public void testUpdate() {
		
		final String [] recordToUpdate = new String[7];
		recordToUpdate[0] = "El Cid";
		recordToUpdate[1] = "Mazatlan";
		recordToUpdate[2] = "5";
		recordToUpdate[3] = "Y";
		recordToUpdate[4] = "$111.01";
		recordToUpdate[5] = "2005/06/23";
		recordToUpdate[6] = "1";
		
		try {
			final String [] recordBkp = data.read(4394);
			
			long lockValue = data.lock(4394);
			data.update(4394, recordToUpdate, lockValue);
			data.unlock(4394, lockValue);
			
			final String[] recordRead = data.read(4394);
			assertEquals(recordToUpdate[0], recordRead[0]);
			assertEquals(recordToUpdate[1], recordRead[1]);
			assertEquals(recordToUpdate[2], recordRead[2]);
			assertEquals(recordToUpdate[3], recordRead[3]);
			assertEquals(recordToUpdate[4], recordRead[4]);
			assertEquals(recordToUpdate[5], recordRead[5]);
			assertEquals(recordToUpdate[6], recordRead[6]);
			
			// Rollback
			lockValue = data.lock(4394);
			data.update(4394, recordBkp, lockValue);
			data.unlock(4394, lockValue);
			
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testCreateDuplicated() {
		
		final String [] recordToInsert = new String[7];
		recordToInsert[0] = "Palace";
		recordToInsert[1] = "Smallville";
		recordToInsert[2] = "2";
		recordToInsert[3] = "Y";
		recordToInsert[4] = "$150.00";
		recordToInsert[5] = "2005/07/27";
		recordToInsert[6] = "2";
		
		try {
			data.create(recordToInsert);
			fail("DuplicateKeyException was expected, but not wasnt thrown");
		} catch (DuplicateKeyException e) {
			assertTrue(true);
		}
		
	}
	
	public void testCreateWithOutDeletedCache() {
		
		final String [] recordToInsert = new String[7];
		recordToInsert[0] = "Riu";
		recordToInsert[1] = "Mazatlan";
		recordToInsert[2] = "8";
		recordToInsert[3] = "Y";
		recordToInsert[4] = "$222.01";
		recordToInsert[5] = "2011/08/22";
		recordToInsert[6] = "2";
		
		try {
			
			final int newRecorwRow = data.create(recordToInsert);
			
			final long lockValue = data.lock(newRecorwRow);
			final String [] recordRead = data.read(newRecorwRow);
			data.unlock(newRecorwRow, lockValue);
			
			assertEquals(recordToInsert[0], recordRead[0]);
			assertEquals(recordToInsert[1], recordRead[1]);
			assertEquals(recordToInsert[2], recordRead[2]);
			assertEquals(recordToInsert[3], recordRead[3]);
			assertEquals(recordToInsert[4], recordRead[4]);
			assertEquals(recordToInsert[5], recordRead[5]);
			assertEquals(recordToInsert[6], recordRead[6]);
			
		} catch (DuplicateKeyException e) {
			fail(e.getMessage());
		} catch (RecordNotFoundException e) {
			fail(e.getMessage());
		}
		
	}
	
	public void testCreateWithDeletedCache() {
		
		try {
			
			final long lockValue = data.lock(4074);
			data.delete(4074, lockValue);
			data.unlock(4074, lockValue);

			final String [] recordToInsert = new String[7];
			recordToInsert[0] = "Motelito";
			recordToInsert[1] = "Guadalajara";
			recordToInsert[2] = "1";
			recordToInsert[3] = "Y";
			recordToInsert[4] = "$100.50";
			recordToInsert[5] = "2011/09/23";
			recordToInsert[6] = "666";
			
			final int recordRow = data.create(recordToInsert);
			
			assertEquals(4074, recordRow);
			
		} catch (DuplicateKeyException e) {
			fail(e.getMessage());
		} catch (RecordNotFoundException e) {
			fail(e.getMessage());
		}
		
	}
	
	public void testWaitForUpdateBeforeRead() {
		
		final String [] recordToUpdate = new String[7];
		recordToUpdate[0] = "Castle Perron";
		recordToUpdate[1] = "Cuidad Esmeralda";
		recordToUpdate[2] = "4";
		recordToUpdate[3] = "Y";
		recordToUpdate[4] = "$150.50";
		recordToUpdate[5] = "2003/05/16";
		recordToUpdate[6] = "";
		
		final Runnable runMePlease = new Runnable() {
			
			@Override
			public void run() {
				
				try {
					
					final long lockValue = data.lock(2954);
					
					Thread.sleep(5000);
					
					data.update(2954, recordToUpdate, lockValue);
					
					data.unlock(2954, lockValue);
					
					
				} catch (RecordNotFoundException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				
				
			}
		};
		
		try {
		
			final Thread t = new Thread(runMePlease);
			t.start();
			
			Thread.sleep(1000); // Wait a little bit for thread above
			
			final long lockValue = data.lock(2954);
			final String [] recordRead = data.read(2954);
			data.unlock(2954, lockValue);
			
			assertEquals(recordToUpdate[0], recordRead[0]);
			assertEquals(recordToUpdate[1], recordRead[1]);
			assertEquals(recordToUpdate[2], recordRead[2]);
			assertEquals(recordToUpdate[3], recordRead[3]);
			assertEquals(recordToUpdate[4], recordRead[4]);
			assertEquals(recordToUpdate[5], recordRead[5]);
			assertEquals(recordToUpdate[6], recordRead[6]);
			
		} catch (InterruptedException e) {
			fail(e.getMessage());
		} catch (RecordNotFoundException e) {
			fail(e.getMessage());
		}
	
	}
	
	public void testFind() {
		
		String [] criteria = new String[7];
		criteria[0] = "Palace";
		
		int[] recordsRows = data.find(criteria);
		
		assertEquals(Arrays.toString(recordsRows), Arrays.toString(new int[]{74, 554, 4074}));
		
		criteria = new String[7];
		criteria[0] = "Palace";
		criteria[4] = "$90.00";
		
		recordsRows = data.find(criteria);
		
		assertEquals(Arrays.toString(recordsRows), Arrays.toString(new int[]{4074}));
	}
}

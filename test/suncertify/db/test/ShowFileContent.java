package suncertify.db.test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ShowFileContent {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		
		File f = new File("/home/leo/dbd/db-1x111.db");
		System.out.println(f.exists());
		
		final RandomAccessFile database = new RandomAccessFile("/home/leo/db/db-1x1.db", "rw");
				//new RandomAccessFile(f, "rw");

		database.seek(74);
		
		final long lastRecordRow = database.length() - 160;
		long currentRecordRow = 0;
		int totalRecordRows = 0;
		while (currentRecordRow < lastRecordRow) {

			final int currentRecordPosition = totalRecordRows * 160;
			currentRecordRow = 74 + currentRecordPosition;

			database.seek(currentRecordRow);
			final byte [] record = new byte[160];
			database.read(record);
			System.out.println(currentRecordRow + ": " + new String(record));

			totalRecordRows++;
		}
		
		System.out.println(totalRecordRows);
		
		database.close();
	}

}

package suncertify.db.test;

import java.io.IOException;

public class ShowFileContent {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		final String name = "IBM System Storage DS5020 Midrange Disk System (all models) with IBM Flex System C4240 M4 Compute Node";
		System.out.println(name.length());
		
		/*
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
		
		database.close();*/
	}

}

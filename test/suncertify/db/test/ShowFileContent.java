package suncertify.db.test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowFileContent {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		format.setLenient(false);
		try {
			System.out.println(format.parse("2011/08/24").toString());
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		
//		final RandomAccessFile database = new RandomAccessFile("C:\\db-1x1.db", "rw");
//
//		database.seek(74);
//		
//		final long lastRecordRow = database.length() - 160;
//		long currentRecordRow = 0;
//		int totalRecordRows = 0;
//		while (currentRecordRow < lastRecordRow) {
//
//			final int currentRecordPosition = totalRecordRows * 160;
//			currentRecordRow = 74 + currentRecordPosition;
//
//			database.seek(currentRecordRow);
//			final byte [] record = new byte[160];
//			database.read(record);
//			System.out.println(currentRecordRow + ": " + new String(record));
//
//			totalRecordRows++;
//		}
//		
//		database.close();
	}

}

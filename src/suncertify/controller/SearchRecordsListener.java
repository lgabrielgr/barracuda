package suncertify.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import suncertify.db.IDatabase;
import suncertify.db.Record;
import suncertify.gui.GUIMessages;
import suncertify.gui.GUIUtils;
import suncertify.gui.StandAloneWindow;

/**
 * Provides the functionality when user clicks on Search button.
 * 
 * @author Leo Gutierrez
 */
public class SearchRecordsListener implements ActionListener {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = 
			SearchRecordsListener.class.getName();
	
	/**
	 * Reference to the stand alone window frame.
	 */
	private StandAloneWindow standAloneWindow = null;
	
	/**
	 * Constructs a <code>SearchRecordsListener</code> object.
	 * 
	 * @param standAloneWindow Reference of the stand alone window frame.
	 */
	public SearchRecordsListener(final StandAloneWindow standAloneWindow) {
		this.standAloneWindow = standAloneWindow;
	}
	
	/**
	 * Invoked when user clicks on Search button or presses Enter in the search fields.
	 * 
	 * @param actionEvent Reference to the <code>ActionEvent</code> object.
	 */
	public void actionPerformed(final ActionEvent actionEvent) {
		
		final String methodName = "actionPerformed";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			if (standAloneWindow == null) {

				ControllerLogger.severe(CLASS_NAME, methodName, 
						"User clicked on Search button but a reference to the " 
								+ "stand alone window does not exist");

				GUIUtils.showErrorMessageDialog(null, 
						GUIMessages.UNABLE_TO_SEARCH_MESSAGE);

				return;
			}

			final IDatabase database = standAloneWindow.getDatabase();
			if (database == null) {

				ControllerLogger.severe(CLASS_NAME, methodName, 
						"User clicked on Search button but a reference to the " 
								+ "database does not exist");

				GUIUtils.showErrorMessageDialog(null, 
						GUIMessages.CANT_ACCESS_TO_DB_MESSAGE);

				return;
			}

			final List<Record> records = searchRecords(database);
			standAloneWindow.addDataToTableModel(records);

		} finally {
			ControllerLogger.exiting(CLASS_NAME, methodName);
		}
	}

	/**
	 * Searches records in the given database with the user input data 
	 * (hotel name and location).
	 * <br />If user does not introduce any data in search fields, all records
	 * are returned. 
	 * <br />If no record is found or an exception occurs
	 * and empty list of records is returned.
	 * 
	 * @param database Database in which to search the records.
	 * @return A list of <code>Record</code> objects found that match with 
	 *         the user input data; Or an empty list if no record is found or
	 *         and exception occurs.
	 */
	private List<Record> searchRecords(final IDatabase database) {
		
		final String methodName = "searchRecords";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		final String [] userInput = readUserInputSearchData();
		final String searchHotel = userInput[0];
		final String searchLocation = userInput[1];
		
		List<Record> records = null;
		
		try {
			
			if ((isEmptyString(searchHotel)) 
					&& (isEmptyString(searchLocation))) {
				
				records = searchAllRecords();
				
			} else {

				records = searchRecordsWithExactlyMatch(searchHotel, 
						searchLocation);
			}
			
			ControllerLogger.info(CLASS_NAME, methodName, 
					"Records found: " + records.size());
			
			standAloneWindow.setStatusLabelText(records.size() 
					+ GUIMessages.RECORDS_FOUND_MESSAGE);
			
		} catch (RemoteException e) {
			
			ControllerLogger.severe(CLASS_NAME, methodName, 
					"Unable to contact the database server: " + e.getMessage());
			
			GUIUtils.showErrorMessageDialog(null, 
					GUIMessages.CANT_ACCESS_TO_DB_MESSAGE);
			
			standAloneWindow.setStatusLabelText(GUIMessages.CANT_CONTACT_DB_MESSAGE);
			
			records = new ArrayList<Record>();
			
		}
		
		ControllerLogger.exiting(CLASS_NAME, methodName, records.size());
		
		return records;
		
	}

	/**
	 * Searches records in the given database that exactly match the Hotel name
	 * and location with the specified string values.
	 * 
	 * @param database Database object in which search the records.
	 * @param searchHotel Hotel name value to apply in the search criteria.
	 * @param searchLocation Location value to apply in the search criteria.
	 * @return List of <code>Record</code> objects found in the search action.
	 * @throws RemoteException If any networking problem occurs.
	 */
	private List<Record> searchRecordsWithExactlyMatch(final String searchHotel,
			final String searchLocation) throws RemoteException {
		
		final String methodName = "searchRecordsWithExactlyMatch";
		ControllerLogger.entering(CLASS_NAME, methodName, searchHotel, 
				searchLocation);
		
		final List<Record> recordsFound = new ArrayList<Record>();
		
		try {
			
			final List<Record> allRecords = searchAllRecords();

			for (Record record: allRecords) {

				final boolean exactlyMatchHotel = isExactlyMatch(searchHotel, 
						record.getHotelName());
				final boolean exactlyMatchLocation = 
						isExactlyMatch(searchLocation, record.getLocation());

				if (exactlyMatchHotel && exactlyMatchLocation) {
					recordsFound.add(record);
				}

			}

		} finally {
			ControllerLogger.exiting(CLASS_NAME, methodName);
		}
		
		return recordsFound;
	}
	
	/**
	 * Searches all records in the database, not applying any search criteria.
	 * 
	 * @return All valid records (not deleted) in the database.
	 * @throws RemoteException If any networking problem occurs.
	 */
	private List<Record> searchAllRecords() throws RemoteException {
		
		final String methodName = "searchAllRecords";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			final IDatabase database = standAloneWindow.getDatabase();
			return database.find(null, null);
			
		} finally {
			ControllerLogger.exiting(CLASS_NAME, methodName);
		}
		
	}
	
	/**
	 * Verifies if the given strings exactly match or not, returns true if they 
	 * do, false otherwise.
	 * 
	 * @param searchInput User input search value (If the value is empty, will 
	 *                    match any value).
	 * @param recordValue Record value.
	 * @return True of they exactly match; False otherwise.
	 */
	private boolean isExactlyMatch(final String searchInput, 
			final String recordValue) {
		
		final String methodName = "isExactlyMatch";
		ControllerLogger.entering(CLASS_NAME, methodName, searchInput, 
				recordValue);
		
		boolean match = false;
		
		if (isEmptyString(searchInput)) {
			
			match = true;
			
		} else {
		
			match = searchInput.equals(recordValue);
			
		}
		
		ControllerLogger.exiting(CLASS_NAME, methodName, match);
		
		return match;
	}
	
	/**
	 * Verifies if the given value is null or empty.
	 * 
	 * @param value Value to verify.
	 * @return True if it is null or empty; False otherwise.
	 */
	private boolean isEmptyString(final String value) {
		
		if ((value == null) || ("".equals(value.trim()))) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Reads and retrieves an array of strings with the input user search data 
	 * as follow:
	 * <br />0 - Hotel name.
	 * <br />1 - Location.
	 * 
	 * @return An array of string with the input user search data.
	 */
	private String[] readUserInputSearchData() {
		
		final String methodName = "readUserInputSearchData";
		ControllerLogger.entering(CLASS_NAME, methodName);
		
		final String[] userInputData = new String[2];
		
		String hotelName = standAloneWindow.getHotelnameFieldText();
		String location = standAloneWindow.getLocationFieldText();
		
		if (hotelName != null) {
			userInputData[0] = hotelName.trim();
		}
		
		if (location != null) {
			userInputData[1] = location.trim();
		}
		
		ControllerLogger.exiting(CLASS_NAME, methodName, 
				Arrays.toString(userInputData));
		
		return userInputData;
		
	}

}

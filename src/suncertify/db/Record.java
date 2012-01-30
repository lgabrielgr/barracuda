package suncertify.db;

import java.io.Serializable;

/**
 * Represents a record in the database.
 * <br />If the database record format changes (i.e. new record field) this 
 * class must be updated with the new format. 
 * 
 * @author Leo Gutierrez
 */
public class Record implements Serializable {

	/**
	 * Class serial version.
	 */
	private static final long serialVersionUID = 402264L;
	
	/**
	 * Record's name field index.
	 */
	public static final int HOTEL_NAME_FIELD_INDEX = 0;
	
	/**
	 * Record's location field index.
	 */
	public static final int LOCATION_FIELD_INDEX = 1;
	
	/**
	 * Record's room size field index.
	 */
	public static final int SIZE_FIELD_INDEX = 2;
	
	/**
	 * Record's smoking field index.
	 */
	public static final int SMOKING_FIELD_INDEX = 3;
	
	/**
	 * Record's rate field index.
	 */
	public static final int RATE_FIELD_INDEX = 4;
	
	/**
	 * Record's date field index.
	 */
	public static final int DATE_FIELD_INDEX = 5;
	
	/**
	 * Record's owner field index.
	 */
	public static final int OWNER_FIELD_INDEX = 6;
	
	/**
	 * Total record fields.
	 */
	public static final int TOTAL_RECORD_FIELDS = 7;
	
	/**
	 * The name of the hotel this vacancy record relates to.
	 */
	private String hotelName;
	
	/**
	 * The location of this hotel.
	 */
	private String location;
	
	/**
	 * The maximum number of people permitted in this room, not including 
	 * infants.
	 */
	private String size;
	
	/**
	 * Flag indicating if smoking is permitted. Valid values are "Y" 
	 * indicating a smoking room, and "N" indicating a non-smoking room.
	 */
	private String smoking;
	
	/**
	 * Charge per night for the room. This field includes the currency symbol.
	 */
	private String rate;
	
	/**
	 * The single night to which this record relates, format is yyyy/mm/dd. 
	 */
	private String date;
	
	/**
	 * The id value (an 8 digit number) of the customer who has booked this.
	 */
	private String owner;
	
	/**
	 * Row number where this record is saved in the database file.
	 */
	private int databaseRow;
	
	/**
	 * Constructs a <code>Record</code> object.
	 */
	public Record() {
		
	}

	/**
	 * Retrieves the record hotel name.
	 * 
	 * @return The record hotel name.
	 */
	public final String getHotelName() {
		return hotelName;
	}

	/**
	 * Sets the record hotel name.
	 * 
	 * @param hotalName The record hotel name.
	 * @throws IllegalArgumentException If the record hotel name is not valid 
	 *                                  (null or empty).
	 */
	public final void setHotelName(final String hotalName) 
			throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidHotelName(hotalName)) {
			throw new IllegalArgumentException("Hotel name not valid: " 
					+ hotalName);
		}
		
		this.hotelName = hotalName;
	}

	/**
	 * Retrieves the record hotel location.
	 * 
	 * @return The record hotel location.
	 */
	public final String getLocation() {
		return location;
	}

	/**
	 * Sets the record hotel location.
	 * 
	 * @param locationValue The record hotel location.
	 * @throws IllegalArgumentException If the record hotel location is not
	 *                                  valid (null or empty).
	 */
	public final void setLocation(final String locationValue) 
			throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidLocation(locationValue)) {
			throw new IllegalArgumentException("Hotel location not "
					+ "valid: " + hotelName);
		}
		
		location = locationValue;
	}

	/**
	 * Retrieves the record room size.
	 * 
	 * @return The record room size.
	 */
	public final String getSize() {
		return size;
	}

	/**
	 * Sets the record room size.
	 * 
	 * @param sizeValue The record room size value.
	 * @throws IllegalArgumentException If the record room size is not valid
	 *                                  (null, empty or not a positive number).
	 */
	public final void setSize(final String sizeValue) 
			throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidRoomSize(sizeValue)) {
			throw new IllegalArgumentException("The room size is not "
					+ "valid: " + sizeValue);
		}
		
		size = sizeValue;
	}

	/**
	 * Retrieves the record room smoking value.
	 * 
	 * @return The record room smoking value.
	 */
	public final String getSmoking() {
		return smoking;
	}

	/**
	 * Sets the record room smoking value.
	 * 
	 * @param smokingValue The record room smoking value.
	 * @throws IllegalArgumentException If the record room smoking value is not
	 *                                  value (not 'Y' and 'N').
	 */
	public final void setSmoking(final String smokingValue) 
			throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidSmoking(smokingValue)) {
			throw new IllegalArgumentException("Room smoking value not "
					+ "valid: " + smokingValue);
		}
		
		smoking = smokingValue;
	}

	/**
	 * Retrieves the record room rate.
	 * 
	 * @return The record room rate.
	 */
	public final String getRate() {
		return rate;
	}

	/**
	 * Sets the record room rate.
	 * 
	 * @param rateValue The record room rate value.
	 * @throws IllegalArgumentException If the record room rate value is not
	 *                                  valid (does not start with the 
	 *                                  currency symbol '$', or is not a valid
	 *                                  number between 0 and 9999.99).
	 */
	public final void setRate(final String rateValue) 
			throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidRate(rateValue)) {
			throw new IllegalArgumentException("Room rate not valid: " 
					+ rateValue);
		}
		
		rate = rateValue;
	}

	/**
	 * Retrieves the record date available.
	 * 
	 * @return The record date available.
	 */
	public final String getDate() {
		return date;
	}

	/**
	 * Sets the record date available.
	 * 
	 * @param dateValue The record date available.
	 * @throws IllegalArgumentException If the record date available value is 
	 *                                  not valid (null, empty or not in format
	 *                                  'yyyy/MM/dd').
	 */
	public final void setDate(final String dateValue) 
			throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidDate(dateValue)) {
			throw new IllegalArgumentException("Date available not "
					+ "valid: " + dateValue);
		}
		
		date = dateValue;
	}

	/**
	 * Retrieves the record owner.
	 * 
	 * @return The record owner.
	 */
	public final String getOwner() {
		return owner;
	}

	/**
	 * Sets the record owner.
	 * 
	 * @param ownerIdValue The record owner id value.
	 * @throws IllegalArgumentException If the record owner value is not valid
	 *                                  (null, or not a positive number).
	 */
	public final void setOwner(final String ownerIdValue) 
			throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidOwner(ownerIdValue)) {
			throw new IllegalArgumentException("Owner value not "
					+ "valid: " + ownerIdValue);
		}
		
		owner = ownerIdValue;
	}

	/**
	 * Retrieves the database row where this record is saved.
	 * 
	 * @return The database row where this record is saved.
	 */
	public final int getDatabaseRow() {
		return databaseRow;
	}

	/**
	 * Sets the database row where this record is saved.
	 * 
	 * @param value The database row where this record is saved.
	 */
	public final void setDatabaseRow(final int value) {
		databaseRow = value;
	}
	
	/**
	 * Returns an Array of String representation for this <code>Record</code>.
	 * 
	 * @return An Array of String representation for this <code>Record</code>.
	 */
	public final String[] toStringArray() {
		
		final String [] recordData = 
				new String [TOTAL_RECORD_FIELDS];
		
		recordData[Record.HOTEL_NAME_FIELD_INDEX] = getHotelName();
		recordData[Record.LOCATION_FIELD_INDEX] = getLocation();
		recordData[Record.SIZE_FIELD_INDEX] = getSize();
		recordData[Record.SMOKING_FIELD_INDEX] = getSmoking();
		recordData[Record.RATE_FIELD_INDEX] = getRate();
		recordData[Record.DATE_FIELD_INDEX] = getDate();
		recordData[Record.OWNER_FIELD_INDEX] = getOwner();
		
		return recordData;
	}
	
	/**
	 * Returns a String representation of this <code>Record</code>.
	 * 
	 * @see Object#toString()
	 * 
	 * @return A String representation of this <code>Record</code>.
	 */
	public final String toString() {
		
		final StringBuilder recordString = new StringBuilder();
		
		recordString.append(getHotelName()).append("/");
		recordString.append(getLocation()).append("/");
		recordString.append(getSize()).append("/");
		recordString.append(getSmoking()).append("/");
		recordString.append(getRate()).append("/");
		recordString.append(getDate()).append("/");
		recordString.append(getOwner());
		
		return recordString.toString();
	}
	
	/**
	 * Returns a hash code value for this <code>Record</code>.
	 * 
	 * @see Object#hashCode()
	 * 
	 * @return A hash code value for this <code>Record</code>.
	 */
	public final int hashCode() {
		
		if (getHotelName() == null) {
			return 0;
		} else {
			return getHotelName().hashCode();
		}
		
	}
	
	/**
	 * Indicates whether some other object is "equal to" this 
	 * <code>Record</code>.
	 * 
	 * @param object Object to compare with.
	 * 
	 * @return True if they're equals; False otherwise.
	 */
	public final boolean equals(final Object object) {
		
		if (!(object instanceof Record)) {
			return false;
		}
		
		final Record recordToCompare = (Record) object;
		
		if (!recordToCompare.getHotelName().equals(getHotelName())) {
			return false;
		}
		
		if (!recordToCompare.getLocation().equals(getLocation())) {
			return false;
		}
		
		if (!recordToCompare.getSize().equals(getSize())) {
			return false;
		}
		
		if (!recordToCompare.getSmoking().equals(getSmoking())) {
			return false;
		}
		
		if (!recordToCompare.getDate().equals(getDate())) {
			return false;
		}
		
		if (!recordToCompare.getRate().equals(getRate())) {
			return false;
		}
		
		if (!recordToCompare.getOwner().equals(getOwner())) {
			return false;
		}
		
		return true;
	}
	
}

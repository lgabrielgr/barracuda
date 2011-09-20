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
	public static final int NAME_FIELD_INDEX = 0;
	
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
	private String name;
	
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
	 * Flag indicating if smoking is permitted. Valid values are "Y" indicating 
	 * a smoking room, and "N" indicating a non-smoking room.
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
	public String getName() {
		return name;
	}

	/**
	 * Sets the record hotel name.
	 * 
	 * @param name The record hotel name.
	 * @throws IllegalArgumentException If the record hotel name is not valid 
	 *                                  (null or empty).
	 */
	public void setName(String name) throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidHotelName(name)) {
			throw new IllegalArgumentException("Hotel name not valid: " + name);
		}
		
		this.name = name;
	}

	/**
	 * Retrieves the record hotel location.
	 * 
	 * @return The record hotel location.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the record hotel location.
	 * 
	 * @param location The record hotel location.
	 * @throws IllegalArgumentException If the record hotel location is not
	 *                                  valid (null or empty).
	 */
	public void setLocation(String location) throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidLocation(location)) {
			throw new IllegalArgumentException("Hotel location not " +
					"valid: " + name);
		}
		
		this.location = location;
	}

	/**
	 * Retrieves the record room size.
	 * 
	 * @return The record room size.
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Sets the record room size.
	 * 
	 * @param size The record room size.
	 * @throws IllegalArgumentException If the record room size is not valid
	 *                                  (null, empty or not a positive number).
	 */
	public void setSize(String size) throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidRoomSize(size)) {
			throw new IllegalArgumentException("The room size is not " +
					"valid: " + size);
		}
		
		this.size = size;
	}

	/**
	 * Retrieves the record room smoking value.
	 * 
	 * @return The record room smoking value.
	 */
	public String getSmoking() {
		return smoking;
	}

	/**
	 * Sets the record room smoking value.
	 * 
	 * @param smoking The record room smoking value.
	 * @throws IllegalArgumentException If the record room smoking value is not
	 *                                  value (not 'Y' and 'N').
	 */
	public void setSmoking(String smoking) throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidSmoking(smoking)) {
			throw new IllegalArgumentException("Room smoking value not " +
					"valid: " + smoking);
		}
		
		this.smoking = smoking;
	}

	/**
	 * Retrieves the record room rate.
	 * 
	 * @return The record room rate.
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * Sets the record room rate.
	 * 
	 * @param rate The record room rate.
	 * @throws IllegalArgumentException If the record room rate value is not
	 *                                  valid (does not start with the currency 
	 *                                  symbol '$', or is not a valid number 
	 *                                  between 0 and 9999.99).
	 */
	public void setRate(String rate) throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidRate(rate)) {
			throw new IllegalArgumentException("Room rate not valid: " + rate);
		}
		
		this.rate = rate;
	}

	/**
	 * Retrieves the record date available.
	 * 
	 * @return The record date available.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the record date available.
	 * 
	 * @param date The record date available.
	 * @throws IllegalArgumentException If the record date available value is 
	 *                                  not valid (null, empty or not in format
	 *                                  'yyyy/MM/dd').
	 */
	public void setDate(String date) throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidDate(date)) {
			throw new IllegalArgumentException("Date available not " +
					"valid: " + date);
		}
		
		this.date = date;
	}

	/**
	 * Retrieves the record owner.
	 * 
	 * @return The record owner.
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Sets the record owner.
	 * 
	 * @param owner The record owner.
	 * @throws IllegalArgumentException If the record owner value is not valid
	 *                                  (null, empty or not a positive number).
	 */
	public void setOwner(String owner) throws IllegalArgumentException {
		
		final RecordValidator validator = new RecordValidator();
		
		if (!validator.isValidOwner(owner)) {
			throw new IllegalArgumentException("Owner value not " +
					"valid: " + owner);
		}
		
		this.owner = owner;
	}

	/**
	 * Retrieves the database row where this record is saved.
	 * 
	 * @return The database row where this record is saved.
	 */
	public int getDatabaseRow() {
		return databaseRow;
	}

	/**
	 * Sets the database row where this record is saved.
	 * 
	 * @param databaseRow The database row where this record is saved.
	 */
	public void setDatabaseRow(int databaseRow) {
		this.databaseRow = databaseRow;
	}
	
	/**
	 * Returns an Array of String representation for this <code>Record</code>.
	 * 
	 * @return An Array of String representation for this <code>Record</code>.
	 */
	public String[] toStringArray() {
		
		final String [] recordData = 
				new String [TOTAL_RECORD_FIELDS];
		
		recordData[Record.NAME_FIELD_INDEX] = getName();
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
	public String toString() {
		
		final StringBuilder recordString = new StringBuilder();
		
		recordString.append(getName()).append("/");
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
	public int hashCode() {
		
		if (getName() == null) {
			return 0;
		} else {
			return getName().hashCode();
		}
		
	}
	
	/**
	 * Indicates whether some other object is "equal to" this 
	 * <code>Record</code>.
	 * 
	 * @see Object#equals(Object)
	 * 
	 * @return True if they're equals; False otherwise.
	 */
	public boolean equals(final Object object) {
		
		if (!(object instanceof Record)) {
			return false;
		}
		
		final Record recordToCompare = (Record)object;
		
		if (!recordToCompare.getName().equals(getName())) {
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

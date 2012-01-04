package suncertify.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RecordValidator {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = RecordValidator.class.getName();
	
	/**
	 * Verifies if the given string value is null or empty.
	 * 
	 * @param value Value to check.
	 * @return True if string value is null or empty; otherwise, false.
	 */
	private boolean isEmptyValue(final String value) {
		
		if (value == null) {
			return true;
		}
		
		if (DatabaseConstants.EMPTY_SPACE.equals(value.trim())) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Validates a record hotel name value. It is considered invalid if the value 
	 * is null or empty.
	 * 
	 * @param name Hotel name value to validate.
	 * @return True if value is valid (not null or empty); otherwise, false.
	 */
	public boolean isValidHotelName(final String name) {
		
		final String methodName = "isValidHotelName";
		DatabaseLogger.entering(CLASS_NAME, methodName, name);
		
		boolean validHotelName = true;
		
		if (isEmptyValue(name)) {
			validHotelName = false;
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName, validHotelName);
		
		return validHotelName;
	}
	
	/**
	 * Validates a record location value. It is considered invalid if the value
	 * is null or empty.
	 * 
	 * @param location Location value to validate.
	 * @return True if value is valid (not null or empty); otherwise, false.
	 */
	public boolean isValidLocation(final String location) {
		
		final String methodName = "isValidLocation";
		DatabaseLogger.entering(CLASS_NAME, methodName, location);
		
		boolean validLocation = true;
		
		if (isEmptyValue(location)) {
			validLocation = false;
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName, validLocation);
		
		return validLocation;
	}
	
	/**
	 * Validates a record room size value. It is considered invalid if the value 
	 * is not a number, negative, zero, null or empty.
	 * 
	 * @param size Size value to validate.
	 * @return True if the size value is valid; otherwise, false.
	 */
	public boolean isValidRoomSize(final String size) {
		
		final String methodName = "isValidRoomSize";
		DatabaseLogger.entering(CLASS_NAME, methodName, size);
		
		boolean validRoomSize = true;
		
		if (isEmptyValue(size)) {
			
			validRoomSize = false;
			
		} else {
			
			try {
				
				final int sizeNo = Integer.parseInt(size);
				
				if (sizeNo <= 0) {
					validRoomSize = false;
				}
				
			} catch (NumberFormatException e) {
				validRoomSize = false;
			}
			
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName, validRoomSize);
		
		return validRoomSize;
	}
	
	/**
	 * Validates a record smoking value. It is considered invalid if the value
	 * is null, empty, or different to 'Y' and 'N'.
	 * 
	 * @param smoking Smoking value to validate.
	 * @return True if the smoking value is valid; otherwise, false.
	 */
	public boolean isValidSmoking(final String smoking) {
		
		final String methodName = "isValidSmoking";
		DatabaseLogger.entering(CLASS_NAME, methodName, smoking);
		
		boolean validSmoking = true;
		
		if (isEmptyValue(smoking)) {
			
			validSmoking = false;
			
		} else if (!(DatabaseConstants.SMOKING_ROOM.equals(smoking)) 
					&& !(DatabaseConstants.NON_SMOKING_ROOM.equals(smoking))) {
				
			validSmoking = false;
			
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName, validSmoking);
		
		return validSmoking;
	}
	
	/**
	 * Validates a record rate value. It is considered invalid if the value
	 * is null or empty, is not a number, does not start with the currency symbol
	 * '$', or is not in the range 0-9999.99.
	 * 
	 * @param rate Rate value to validate.
	 * @return True is the record rate value is valid; otherwise, false.
	 */
	public boolean isValidRate(final String rate) {
		
		final String methodName = "isValidRate";
		DatabaseLogger.entering(CLASS_NAME, methodName, rate);
		
		boolean validRate = true;
		
		if (isEmptyValue(rate)) {
			
			validRate = false;
			
		} else if (!rate.startsWith("$")) {
			
			validRate = false;
			
		} else {
			
			try {
				
				final String rateAmount = rate.substring(1);
				
				double rateDouble = Double.parseDouble(rateAmount);
			
				if ((rateDouble <= 0) 
						|| (rateDouble > DatabaseConstants.MAX_RATE_VALUE)) {
					validRate = false;
				}
				
			} catch (NumberFormatException e) {
				validRate = false;
			}
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName, validRate);
		
		return validRate;
		
	}
	
	/**
	 * Validates a record date value. It is considered invalid if the value
	 * is null, empty or is not with the format 'yyyy/MM/dd'.
	 * 
	 * @param date Date value to validate.
	 * @return True is the record date value is valid; otherwise, false.
	 */
	public boolean isValidDate(final String date) {
		
		final String methodName = "isValidaDate";
		DatabaseLogger.entering(CLASS_NAME, methodName, date);
		
		boolean validDate = true;
		
		if (isEmptyValue(date)) {
			
			validDate = false;
			
		} else {
			
			final SimpleDateFormat dateFormat = 
					new SimpleDateFormat("yyyy/MM/dd");
			
			try {
				dateFormat.parse(date);
			} catch (ParseException e) {
				validDate = false;
			}
			
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName, validDate);
		
		return validDate;
		
	}
	
	/**
	 * Validates a record owner value. It is considered invalid if the value
	 * is null, not a number (empty string is valid) or is not in the range
	 * between 1 and 99999999.
	 * 
	 * @param owner Owner value to validate.
	 * @return True is the record owner value is valid; otherwise, false.
	 */
	public boolean isValidOwner(final String owner) {
		
		final String methodName = "isValidOwner";
		DatabaseLogger.entering(CLASS_NAME, methodName, owner);
		
		boolean validOwner = true;
		
		if (owner == null) {
			
			validOwner = false;
			
		} else if (!"".equals(owner)) {
			
			try {
				
				final long ownerId = Long.parseLong(owner);
				
				if ((ownerId <= 0) 
						|| (ownerId > DatabaseConstants.MAX_OWNER_ID_VALUE)) {
					validOwner = false;
				}
			
			} catch (NumberFormatException e) {
				validOwner = false;
			}
			
		}
		
		DatabaseLogger.exiting(CLASS_NAME, methodName, validOwner);
		
		return validOwner;
		
	}
}

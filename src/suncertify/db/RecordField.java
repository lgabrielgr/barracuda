package suncertify.db;

/**
 * Represents a record field with the field name, field name length and
 * field value length.
 * 
 * @author Leo Gutierrez
 */
public class RecordField {

	/**
	 * Field name.
	 */
	private String fieldName;
	
	/**
	 * Field name length.
	 */
	private int fieldNameLength;
	
	/**
	 * Field value length.
	 */
	private int fieldValueLength;

	/**
	 * Field position in record.
	 */
	private int fieldPosition;
	
	/**
	 * Retrieves the record field name.
	 * 
	 * @return Record field name.
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Sets the record field name.
	 * 
	 * @param fieldNameValue Record field name value.
	 */
	public void setFieldName(String fieldNameValue) {
		fieldName = fieldNameValue;
	}

	/**
	 * Retrieves the record field name length.
	 * 
	 * @return Record field name length.
	 */
	public int getFieldNameLength() {
		return fieldNameLength;
	}

	/**
	 * Sets the record field name length.
	 * 
	 * @param fieldNameLengthValue Record field name length value.
	 */
	public void setFieldNameLength(int fieldNameLengthValue) {
		fieldNameLength = fieldNameLengthValue;
	}

	/**
	 * Retrieves the record field value length.
	 * 
	 * @return Record field value length.
	 */
	public int getFieldValueLength() {
		return fieldValueLength;
	}

	/**
	 * Sets the record field value length.
	 * 
	 * @param valueLength Record field value length.
	 */
	public void setFieldValueLength(int valueLength) {
		fieldValueLength = valueLength;
	}
	
	/**
	 * Retrieves the field position in record.
	 * 
	 * @return Field position in record.
	 */
	public int getFieldPosition() {
		return fieldPosition;
	}

	/**
	 * Sets the field position in record.
	 * 
	 * @param fieldPositionValue Field position in record.
	 */
	public void setFieldPosition(int fieldPositionValue) {
		fieldPosition = fieldPositionValue;
	}

	/**
	 * Returns a hash code value for this object.
	 * 
	 * @return A hash code value for this object
	 */
	public int hashCode() {
		
		if (getFieldName() == null) {
			return 0;
		} else {
			return getFieldName().hashCode();
		}
	}
	
	/**
	 * Verifies if this object is equal to the object received as parameter, 
	 * comparing each of its properties values.
	 * 
	 * @param object The reference object with which to compare. 
	 * @return True if this object is the same as the object argument; 
	 *         false otherwise.
	 */
	public boolean equals(final Object object) {
		
		if (!(object instanceof RecordField)) {
			return false;
		}
		
		final RecordField objectToCompare = (RecordField) object;
		
		String fieldNameToCompare = objectToCompare.getFieldName();
		if (fieldNameToCompare == null) {
			fieldNameToCompare = "";
		}
		
		if (!fieldNameToCompare.equals(getFieldName())) {
			return false;
		}
		
		if (objectToCompare.getFieldNameLength() != getFieldNameLength()) {
			return false;
		}
		
		if (objectToCompare.getFieldValueLength() != getFieldValueLength()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns a string representation of the object.
	 * 
	 * @return A string representation of the object.
	 */
	public String toString() {
		
		final StringBuilder recordFieldString = new StringBuilder();
		
		recordFieldString.append("Field name: ").append(getFieldName());
		recordFieldString.append("\nField name length: ").append(getFieldNameLength());
		recordFieldString.append("\nField value length: ").append(getFieldValueLength());
		
		return recordFieldString.toString();
	}
	
}

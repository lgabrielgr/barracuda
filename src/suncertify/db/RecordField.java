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
	public final String getFieldName() {
		return fieldName;
	}

	/**
	 * Sets the record field name.
	 * 
	 * @param fieldNameValue Record field name value.
	 */
	public final void setFieldName(final String fieldNameValue) {
		fieldName = fieldNameValue;
	}

	/**
	 * Retrieves the record field name length.
	 * 
	 * @return Record field name length.
	 */
	public final int getFieldNameLength() {
		return fieldNameLength;
	}

	/**
	 * Sets the record field name length.
	 * 
	 * @param fieldNameLengthValue Record field name length value.
	 */
	public final void setFieldNameLength(final int fieldNameLengthValue) {
		fieldNameLength = fieldNameLengthValue;
	}

	/**
	 * Retrieves the record field value length.
	 * 
	 * @return Record field value length.
	 */
	public final int getFieldValueLength() {
		return fieldValueLength;
	}

	/**
	 * Sets the record field value length.
	 * 
	 * @param valueLength Record field value length.
	 */
	public final void setFieldValueLength(final int valueLength) {
		fieldValueLength = valueLength;
	}
	
	/**
	 * Retrieves the field position in record.
	 * 
	 * @return Field position in record.
	 */
	public final int getFieldPosition() {
		return fieldPosition;
	}

	/**
	 * Sets the field position in record.
	 * 
	 * @param fieldPositionValue Field position in record.
	 */
	public final void setFieldPosition(final int fieldPositionValue) {
		fieldPosition = fieldPositionValue;
	}

	/**
	 * Returns a hash code value for this object.
	 * 
	 * @return A hash code value for this object
	 */
	public final int hashCode() {
		
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
	public final boolean equals(final Object object) {
		
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
	public final String toString() {
		
		final StringBuilder recordFieldString = new StringBuilder();
		
		recordFieldString.append("Field name: ").append(getFieldName());
		recordFieldString.append("\nField name length: ").append(
				getFieldNameLength());
		recordFieldString.append("\nField value length: ").append(
				getFieldValueLength());
		
		return recordFieldString.toString();
	}
	
}

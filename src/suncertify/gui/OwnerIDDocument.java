package suncertify.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import suncertify.db.DatabaseConstants;

/**
 * <code>Document</code> to use in <code>JTextField</code> to accept valid
 * values for Owner ID (positive numbers, from 1 to 99999999).
 * 
 * @author Leo Gutierrez
 */
public class OwnerIDDocument extends PlainDocument {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 5230030145673656928L;

	/**
	 * Inserts valid number in the document. If it is not valid, the 
	 * value is ignored and the document preserves the previous value.
	 * It is considered value if the new value to insert is a positive number
	 * from 1 to 99999999.
	 * 
	 * @param offset Offset to start to insert the new value.
	 * @param valueToInsert Value to insert.
	 * @param attributeSet The attribute set.
	 */
	public void insertString(final int offset, final String valueToInsert, 
			final AttributeSet attributeSet) throws BadLocationException {

		if (valueToInsert != null) {

			String previousValue = getText(0, getLength());
			
			final StringBuilder newValue = new StringBuilder();
			newValue.append(previousValue.substring(0, offset)); 
		    newValue.append(valueToInsert);
		    newValue.append(previousValue.substring(offset));
		    
			try {
				
				Long.parseLong(newValue.toString());

				if (newValue.length() 
						<= DatabaseConstants.OWNER_FIELD_LENGTH) {
				
					super.insertString(offset, valueToInsert, attributeSet);
				
				}

			} catch (NumberFormatException e) {
				// The value that it is trying to insert is not a number,
				// and it is ignored.
			}
		}

	}
	
}

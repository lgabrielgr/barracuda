package suncertify.gui;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * <code>JTextField</code> that only accepts valid numbers as user's input.
 * <br />If user inserts other thing that numbers, this is ignored and
 * the field preserves the previous value.
 * 
 * @author Leo Gutierrez
 */
public class NumericTextField extends JTextField {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -6058348394353116783L;

	/**
	 * Constructs a <code>NumericTextField</code> object.
	 * 
	 * @param size Size of text field.
	 */
	public NumericTextField(final int size) {

		super(size);

	}

	/**
	 * Retrieves the <code>Document</code> to use in the number text field.
	 * <br />This <code>Document</code> only accepts numbers as input.
	 * 
	 * @return The <code>Document</code> to use in the number text field.
	 */
	protected Document createDefaultModel() {
		return new NumericTextDocument();
	}

	/**
	 * <code>Document</code> that only accepts numbers to insert. 
	 * 
	 * @author Leo Gutierrez
	 */
	private class NumericTextDocument extends PlainDocument {

		/**
		 * Serial version UID.
		 */
		private static final long serialVersionUID = 5230030145673656928L;

		/**
		 * Inserts valid number in the document. If it is not valid, the 
		 * value is ignored and the document preserves the previous value.
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
					
					Integer.parseInt(newValue.toString());
					
					super.insertString(offset, valueToInsert, attributeSet);
					
				} catch (NumberFormatException e) {
					// The value that it is trying to insert is not a number,
					// this value is ignore.
				}
			}

		}
	}

}

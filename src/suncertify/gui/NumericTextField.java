package suncertify.gui;

import javax.swing.JTextField;
import javax.swing.text.Document;

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
	 * Constructs a <code>NumericTextField</code> object with the specified 
	 * column size.
	 * 
	 * @param columns the number of columns to use to calculate the preferred 
	 *                width; if columns is set to zero, the preferred width 
	 *                will be whatever naturally results from the component 
	 *                implementation.
	 */
	public NumericTextField(final int columns) {

		super(columns);

	}

	/**
	 * Constructs a <code>NumericTextField</code> object.
	 */
	public NumericTextField() {
		
	}
	
	/**
	 * Retrieves the <code>Document</code> to use in the number text field.
	 * <br />This <code>Document</code> only accepts numbers as input.
	 * 
	 * @return The <code>Document</code> to use in the number text field.
	 */
	protected Document createDefaultModel() {
		return new OwnerIDDocument();
	}

}

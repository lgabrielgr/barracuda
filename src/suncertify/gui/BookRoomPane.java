package suncertify.gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Standardizes the <code>JPanel</code> to use for asking to the user the 
 * Owner ID when He or She wants to book a room. 
 * 
 * @author Leo Gutierrez
 */
public class BookRoomPane extends JPanel implements DocumentListener {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 879099862L;

	/**
	 * Reference to the panel's <code>JLabel</code>.
	 */
	final private JLabel ownerIDLabel = 
			new JLabel(GUIMessages.ENTER_OWNER_ID_MESSAGE);
	
	/**
	 * Reference to the panel's <code>JTextField</code>.
	 */
	final private JTextField ownerIDTextField = new NumericTextField();
	
	/**
	 * Reference to the <code>Map</code> of the JOptionPane's buttons.
	 */
	final Map<String, JButton> optionPaneButtons = 
			new HashMap<String, JButton>();
	
	/**
	 * Constructor.
	 */
	public BookRoomPane() {
		
		super(new GridLayout(2, 1));
		
		init();
	}
	
	/**
	 * Invoked when user inserts text into Owner ID <code>JTextField</code>.
	 * 
	 * @param documentEvent Document event.
	 */
	public void insertUpdate(final DocumentEvent documentEvent) {
		verifyIfUserCanBookRoom();
	}

	/**
	 * Invoked when user removes text into Owner ID <code>JTextField</code>.
	 * 
	 * @param documentEvent Document event.
	 */
	public void removeUpdate(final DocumentEvent documentEvent) {
		verifyIfUserCanBookRoom();
	}

	/**
	 * Invoked when user changes text into Owner ID <code>JTextField</code>.
	 * 
	 * @param documentEvent Document event.
	 */
	public void changedUpdate(final DocumentEvent documentEvent) {
		verifyIfUserCanBookRoom();
	}
	
	/**
	 * Verifies if user can book a room. If Owner ID <code>JTextField</code>
	 * is empty the OK button is disabled and can't book a room; Otherwise, 
	 * user can book the room. 
	 */
	private void verifyIfUserCanBookRoom() {
		
		final JButton okButton = retrieveJOptionPaneOKButton(); 
		
		if (okButton != null) {
			
			final String ownerIDText = ownerIDTextField.getText();
			
			if ((ownerIDText != null)
					&& (!"".equals(ownerIDText.trim()))
					&& (!"0".equals(ownerIDText.trim()))) {
				
				okButton.setEnabled(true);
				
			} else {
				
				okButton.setEnabled(false);
				
			}
			
		}
		
	}
	
	/**
	 * Initializes the panel.
	 */
	private void init() {
		
		add(ownerIDLabel);
		
		ownerIDTextField.getDocument().addDocumentListener(this);
		
		add(ownerIDTextField);
		
	}
	
	/**
	 * Searches the <code>JOptionPane</code> buttons, so the document listener
	 * can enable and disable the OK button depending on what the user enters 
	 * into the Owner ID <code>JTextField</code>. 
	 * 
	 * @param panelComponents <code>Component</code> list from where the buttons
	 *                        are searched. 
	 */
	private void initDocumentListener(final Component [] panelComponents) {
		
		for (Component component: panelComponents) {
			
			if (component instanceof JButton) {
				
				final JButton button = (JButton) component;
				
				optionPaneButtons.put(button.getText(), button);
				
			} else if (component instanceof JPanel) {
				
				final JPanel panel = (JPanel) component;
				
				initDocumentListener(panel.getComponents());
				
			}
			
		}
		
		insertUpdate(null);
		
	}
	
	/**
	 * Retrieves the JOptionPane's OK Button.
	 * 
	 * @return JOptionPane's OK Button.
	 */
	private JButton retrieveJOptionPaneOKButton() {
		
		return optionPaneButtons.get(GUIConstants.OK_TEXT);
		
	}
	
	/**
	 * Retrieves the Owner ID value entered by the user into the Owner ID
	 * <code>JTextField</code>.
	 * 
	 * @return
	 */
	private String getOwnerID() {
		return ownerIDTextField.getText();
	}
	
	/**
	 * Creates a <code>JDialog</code> to ask to user the owner id value
	 * to use in the book room action.
	 * 
	 * @param parentFrame Parent <code>JFrame</code> as owner of this 
	 *                    <code>JDialog</code>.
	 * @param title       Title message.
	 * @param optionPane  <code>JOptionPane</code> to set as content pane.
	 * @return <code>JDialog</code> to ask to user the owner id value
	 *         to use in the book room action.
	 */
	private JDialog createDialog(final JFrame parentFrame,
			final String title, final JOptionPane optionPane) {
		
		final JDialog dialog = new JDialog(parentFrame, title, true);
		
		dialog.setContentPane(optionPane);
		dialog.pack();
		dialog.setLocationRelativeTo(parentFrame);
		
		optionPane.addPropertyChangeListener(new PropertyChangeListener() {

			/**
			 * Invoked when a bound property is changed.
			 * 
			 * @param propertyChanged Property change event.
			 */
			public void propertyChange(
					final PropertyChangeEvent propertyChanged) {
				
				if (propertyChanged.getPropertyName()
						.equals(JOptionPane.VALUE_PROPERTY)) {
					
					final int newValue = (Integer) propertyChanged.getNewValue();
					
					if ((newValue == JOptionPane.OK_OPTION)
							|| (newValue == JOptionPane.CANCEL_OPTION)) {
						dialog.setVisible(false);
					}
					
				}

			}
		});
		
		GUIUtils.enableEscapeToExit(dialog, dialog.getRootPane(), false);
		
		return dialog;
	}
	
	/**
	 * Shows a dialog asking for the owner Id to book a room. 
	 * 
	 * @param parentFrame Parent <code>JFrame</code> for the dialog. Can be 
	 *                    null.
	 * @return Owner ID value entered by the user; Null otherwise, if user
	 *         cancelled the dialog.
	 */
	public static String showDialog(final JFrame parentFrame) {
		
		String ownerID = null;
		
		final BookRoomPane bookRoomPane = new BookRoomPane();
		
		final JOptionPane optionPane = new JOptionPane(bookRoomPane, 
				JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		
		bookRoomPane.initDocumentListener(optionPane.getComponents());
		
		final JDialog dialog = bookRoomPane.createDialog(parentFrame, 
				GUIMessages.BOOK_ROOM_TITLE, optionPane);
		
		dialog.setVisible(true);
		
		int userSelection = JOptionPane.CANCEL_OPTION;
		
		if ((!optionPane.getValue().equals(JOptionPane.UNINITIALIZED_VALUE))
				&& (optionPane.getValue() != null)) {
			
			userSelection = 
					Integer.valueOf(optionPane.getValue().toString());
			
		}
		
		if (JOptionPane.OK_OPTION == userSelection) {
			
			ownerID = bookRoomPane.getOwnerID();
			
		}
		
		return ownerID;
		
	}
	
}

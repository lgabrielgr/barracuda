package suncertify.controller;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import suncertify.gui.GUIConstants;
import suncertify.gui.GUIMessages;

/**
 * Filters the set of files shown to the user by a <code>JFileChooser</code>,
 * letting to user only to select folders or *.db files.
 *
 * @author Leo Gutierrez
 */
public class DatabaseFileFilter extends FileFilter {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME =
			DatabaseFileFilter.class.getName();
	
	/**
	 * Accepts or not the specified file to be selected by the user.
	 * 
	 * @param file File to decide to be filtered or not.
	 * @return True if the user can select this <code>File</code>; False
	 *         otherwise.
	 */
	public final boolean accept(final File file) {

		final String methodName = "accept";
		ControllerLogger.entering(CLASS_NAME, methodName);

		try {

			if (file == null) {
				return false;
			}

			if (file.isFile()) {

				return file.getName().endsWith(
						GUIConstants.DATABASE_FILE_EXTENSION);

			} else {

				return true;

			}

		} finally {
			
			ControllerLogger.exiting(CLASS_NAME, methodName);
			
		}
		
	}

	/**
	 * Retrieves the file filter description.
	 * 
	 * @return The file filter description.
	 */
	public final String getDescription() {
		
		final String methodName = "getDescription";
		ControllerLogger.entering(CLASS_NAME, methodName);
		ControllerLogger.exiting(CLASS_NAME, methodName);
		
		return GUIMessages.DATABASE_FILE_CHOOSER_DESCRIPTION;
	}

}

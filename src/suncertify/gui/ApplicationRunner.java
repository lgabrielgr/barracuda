package suncertify.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Starts the application (main method) according with the mode specified in 
 * arguments.
 * 
 * @author Leo Gutierrez
 */
public class ApplicationRunner {

	/**
	 * Class name.
	 */
	public static final String CLASS_NAME = ApplicationRunner.class.getName();
	
	/**
	 * Launches the application.
	 * 
	 * @param args Possible modes to start the application, may be one of:
	 *             <br />"alone" - Starts the GUI Console (a server connection 
	 *             is required).
	 *             <br />"server" - Starts the Server.
	 *             <br />(No Parameter) - Starts the Server and the GUI Console.
	 */
	public static void main(final String [] args) {
		
		final String methodName = "main";
		GUILogger.entering(CLASS_NAME, methodName);
		
		startApplication(args);
		
		GUILogger.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * Starts the application according with the mode specified in the 
	 * arguments.
	 * 
	 * @param args Possible modes to start the application, may be one of:
	 *             <br />"alone" - Starts the GUI Console (a server connection 
	 *             is required).
	 *             <br />"server" - Starts the Server.
	 *             <br />(No Parameter) - Starts the Server and the GUI Console.
	 */
	private static void startApplication(final String [] args) {
		
		final String methodName = "startApplication";
		GUILogger.entering(CLASS_NAME, methodName);
		
		setLookAndFeel();
		
		if (args.length == 0) {
			
			new ConnectToServerWindow();
			
		} else if (GUIConstants.SERVER_MODE_PARAMETER.equals(args[0])) {
			
			new ServerWindow();
			
		} else if (GUIConstants.STAND_ALONE_MODE_PARAMETER.equals(args[0])) {
			
			new ConnectToDatabaseWindow();
			
		} else {
			
			GUILogger.severe(CLASS_NAME, methodName, "Invalid option to start" 
					+ " the application: " + args[0]);
			
			printUsage();
			
		}
		
		GUILogger.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * Sets the LookAndFeel to the application.
	 */
	private static void setLookAndFeel() {
		
		final String methodName = "setLookAndFeel";
		GUILogger.entering(CLASS_NAME, methodName);
		
		try {
			
            UIManager.setLookAndFeel(
            		UIManager.getSystemLookAndFeelClassName());
            
        } catch (UnsupportedLookAndFeelException uex) {
            
        	GUILogger.warning(CLASS_NAME, methodName, "Unable to set look and "
        			+ "feel to UI Manager: " + uex.getMessage());
        	
        } catch (ClassNotFoundException cex) {
            
        	GUILogger.warning(CLASS_NAME, methodName, "Unable to set look and "
        			+ "feel to UI Manager: " + cex.getMessage());
        	
        } catch (InstantiationException iex) {
            
        	GUILogger.warning(CLASS_NAME, methodName, "Unable to set look and "
        			+ "feel to UI Manager: " + iex.getMessage());
        	
        } catch (IllegalAccessException iaex) {

        	GUILogger.warning(CLASS_NAME, methodName, "Unable to set look and "
        			+ "feel to UI Manager: " + iaex.getMessage());
        	
        }
		
		GUILogger.exiting(CLASS_NAME, methodName);
		
	}

	/**
	 * Prints how to start the application in the different possible modes.
	 */
	private static void printUsage() {
		
		System.out.println("Command line options may be one of:");
		System.out.println("\"alone\" - Starts the GUI Console.");
        System.out.println("\"server\" - Starts the Server.");
        System.out.println("(No Parameter) - Starts the Server and the GUI " 
        		+ "Console");
		
	}
	
}

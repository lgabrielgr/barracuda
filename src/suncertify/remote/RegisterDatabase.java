package suncertify.remote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Registers and initializes the server (Database) through RMI.
 * 
 * @author Leo Gutierrez
 */
public class RegisterDatabase {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = RegisterDatabase.class.getName();
	
	/**
	 * Reference to the RMI registry.
	 */
	private static Registry registry = null;
	
	/**
	 * Prevents <code>RegisterDatabase</code> instantiations, all
	 * methods are static.
	 */
	private RegisterDatabase() {
		
	}
	
	/**
	 * Binds the database and initializes the server.
	 * 
	 * @throws RemoteException If any networking error occurs.
	 */
	public static void bind() throws RemoteException {
		
		final String methodName = "register";
		RemoteLogger.entering(CLASS_NAME, methodName);
		
		try {
			
			final int rmiPort = retrieveRMIPort();

			registry = LocateRegistry.createRegistry(rmiPort);
			registry.rebind(RemoteConstants.DATABASE_BIND_NAME, 
					new RemoteDatabase());
			
		} finally {
			
			RemoteLogger.exiting(CLASS_NAME, methodName);
			
		}
	}

	/**
	 * Unbinds the server.
	 */
	public static void unbind() {
		
		final String methodName = "unbind";
		RemoteLogger.entering(CLASS_NAME, methodName);
		
		if (registry != null) {
			
			try {
				
				registry.unbind(RemoteConstants.DATABASE_BIND_NAME);
				
			} catch (RemoteException e) {
				
				final String errorMessage = "Unable to unbind server due to: " 
						+ e.getMessage();
				
				RemoteLogger.warning(CLASS_NAME, methodName, errorMessage);
				
			} catch (NotBoundException e) {
				
				final String errorMessage = "Unable to unbind server due to: " 
						+ e.getMessage();
				
				RemoteLogger.warning(CLASS_NAME, methodName, errorMessage);
				
			}
		}
		
		RemoteLogger.exiting(CLASS_NAME, methodName);
		
	}
	
	/**
	 * Retrieves the RMI port from the properties file.
	 * 
	 * @return The RMI port.
	 */
	private static int retrieveRMIPort() {
		
		final String methodName = "retrieveRMIPort";
		RemoteLogger.entering(CLASS_NAME, methodName);
		
		final RemoteProperties properties = new RemoteProperties();

		int rmiPort = RemoteConstants.DEFAULT_RMI_PORT;

		final String definedRMIPort = properties.readRMIPort();

		if (definedRMIPort != null) {

			try {

				rmiPort = Integer.valueOf(definedRMIPort);

			} catch (NumberFormatException e) {

				RemoteLogger.severe(CLASS_NAME, methodName, "Invalid rmi port " +
						"defined in properties file: " + definedRMIPort + ", " +
						"setting the default rmi port : " + rmiPort);

			}

		}
		
		RemoteLogger.exiting(CLASS_NAME, methodName, rmiPort);
		
		return rmiPort;
	}
	
}

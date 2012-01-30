package suncertify.remote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import suncertify.db.IDatabase;

/**
 * Does the connection to the server to access to the database.
 * 
 * @author Leo Gutierrez
 */
public class RemoteDatabaseConnector {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = 
			RemoteDatabaseConnector.class.getName();
	
	/**
	 * Prevents <code>RemoteDatabaseConnector</code> instantiations, all 
	 * methods are static.
	 */
	protected RemoteDatabaseConnector() {
		
	}
	
	/**
	 * Connects to the database server with the specified host and port.
	 * 
	 * @param host Host to which to connect.
	 * @param port Port to which to connect.
	 * @return The database connection.
	 * @throws RemoteException If any networking error occurs.
	 */
	public static IDatabase getConnection(final String host, 
			final int port) throws RemoteException {
		
		final String methodName = "getConnection";
		RemoteLogger.entering(CLASS_NAME, methodName, host, port);
		
		final String rmiURL = "rmi://" + host + ":" + port + "/" 
				+ RemoteConstants.DATABASE_BIND_NAME;
		
		IDatabase database = null;
		
		try {
			
			database = (IDatabase) Naming.lookup(rmiURL);
			
		} catch (MalformedURLException e) {
			
			final String errorMessage = "Unable to connect to the database "
					+ "server: " + e.getMessage();
			
			RemoteLogger.severe(CLASS_NAME, methodName, errorMessage);
			
			throw new RemoteException(errorMessage);
			
		} catch (NotBoundException e) {
			
			final String errorMessage = "Unable to connect to the database "
					+ "server: " + e.getMessage();
			
			RemoteLogger.severe(CLASS_NAME, methodName, errorMessage);
			
			throw new RemoteException(errorMessage);
			
		} finally {
			
			RemoteLogger.exiting(CLASS_NAME, methodName);
			
		}
		
		return database;
	}
	
}

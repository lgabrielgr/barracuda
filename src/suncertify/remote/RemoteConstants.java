package suncertify.remote;

/**
 * Defines all the constants to use into the <code>suncertify.remote</code> 
 * package.
 * 
 * @author Leo Gutierrez
 */
public class RemoteConstants {

	/**
	 * Constructor.
	 */
	protected RemoteConstants() {
		
	}
	
	/**
	 * rmi.port property name.
	 */
	public static final String RMI_PORT_PROP = "rmi.port";
	
	/**
	 * rmi.host property name.
	 */
	public static final String RMI_HOST_PROP = "rmi.host";
	
	/**
	 * Default RMI host.
	 */
	public static final String DEFAULT_RMI_HOST = "127.0.0.1";
	
	/**
	 * Default RMI port.
	 */
	public static final int DEFAULT_RMI_PORT = 
			java.rmi.registry.Registry.REGISTRY_PORT;
	
	/**
	 * Bind name for the database.
	 */
	public static final String DATABASE_BIND_NAME = "DBMediator";
}

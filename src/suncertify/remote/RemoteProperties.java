package suncertify.remote;

import suncertify.properties.AppProperties;

/**
 * Reads the defined properties values to use into the 
 * <code>suncertify.remote</code> package.
 * 
 * @author Leo Gutierrez.
 */
public class RemoteProperties extends AppProperties {

	/**
	 * Constructs a <code>RemoteProperties</code> object.
	 */
	public RemoteProperties() {
		super();
	}
	
	/**
	 * Reads the RMI Port. There is no default value if the property is not
	 * found.
	 * 
	 * @return The RMI Port.
	 */
	public String readRMIPort() {
		return readPropertyValue(RemoteConstants.RMI_PORT_PROP, null);
	}
	
	/**
	 * Reads the RMI Host. If the property is not found, a default value is
	 * returned '127.0.0.1'.
	 * 
	 * @return The RMI Host.
	 */
	public String readRMIHost() {
		return readPropertyValue(RemoteConstants.RMI_HOST_PROP, 
				RemoteConstants.DEFAULT_RMI_HOST);
	}
	
}

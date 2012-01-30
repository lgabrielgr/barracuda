package suncertify.gui;

import suncertify.controller.ServerStandAloneListener;

/**
 * Provides the graphical user interface for the ServerStandAlone window to start/stop 
 * the server and start the Client window.
 * 
 * @author Leo Gutierrez
 */
public class ServerStandAloneWindow extends ServerWindow {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 448302L;

	/**
	 * Constructs a <code>ServerStandAloneWindow</code> object.
	 */
	public ServerStandAloneWindow() {
		
	}
	
	/**
	 * Adds the proper listener to the primary button on Server StandAlone 
	 * window frame.
	 */
	protected final void addListenerToPrimaryServerButton() {
		addListenerToPrimaryServerButton(new ServerStandAloneListener(this));
	}
	
}

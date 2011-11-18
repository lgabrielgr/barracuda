package suncertify.controller;

import suncertify.gui.AbstractServerWindow;

public class StartServerStandAloneListener extends StartServerListener {

	/**
	 * Reference to the Server window frame.
	 */
	private AbstractServerWindow serverWindow;
	
	public StartServerStandAloneListener(final AbstractServerWindow serverWindow) {
		
		super(serverWindow);
		
		this.serverWindow = serverWindow;
		
	}
	
}

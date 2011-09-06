package suncertify.db.test;

import java.rmi.RemoteException;

import junit.framework.TestCase;
import suncertify.db.IDatabase;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;
import suncertify.remote.RegisterDatabase;
import suncertify.remote.RemoteDatabaseConnector;
import suncertify.remote.RemoteProperties;

public class RemoteDatabaseTest extends TestCase {

	public void testRemoteRead() {
		
		try {
			
			RegisterDatabase.bind();
			
			final RemoteProperties prop = new RemoteProperties();
			
			final IDatabase database = 
					RemoteDatabaseConnector.getConnection(prop.readRMIHost(), 
					Integer.valueOf(prop.readRMIPort()));
			
			final Record record = database.read(74);
			
			RegisterDatabase.unbind();
			
			assertNotNull(record);
			
		} catch (RemoteException e) {
			
			fail(e.getMessage());
			
		} catch (RecordNotFoundException e) {
			
			fail(e.getMessage());
			
		}
		
	}
	
}

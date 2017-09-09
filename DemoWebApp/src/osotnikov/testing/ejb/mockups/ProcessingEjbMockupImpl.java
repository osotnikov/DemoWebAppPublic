package osotnikov.testing.ejb.mockups;

import javax.annotation.Resource;
import javax.ejb.SessionContext;

import osotnikov.testing.mockups.ProcessingMockup;
import osotnikov.testing.mockups.ProcessingMockupImpl;

public class ProcessingEjbMockupImpl extends ProcessingMockupImpl implements ProcessingMockup{

	/* From the Javadocs:
	 * 
	 * The SessionContext interface provides access to the runtime session context that 
	 * the container provides for a session bean instance. The container passes the 
	 * SessionContext interface to an instance after the instance has been created. 
	 * The session context remains associated with the instance for the lifetime of 
	 * the instance.
	 * */
	@Resource // In other words because of container magic an EJB can reference itself.
	private SessionContext sessionContext;
	
	@Override
	protected String getProcessingReport(java.util.Date startTime, String mockupName){
		
		return getClass().getSimpleName() + ": [" + mockupName + 
				"] with EJB proxy hash code: [" + sessionContext.getBusinessObject(ProcessingMockup.class) + 
				"] with processing object hash code [" + this.hashCode() + 
				"] whose internal state is [" + getMockupState() + "] started at [" 
				+ startTime + "] ended at [" + new java.util.Date().toString() + "]";
	}

}

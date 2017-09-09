package osotnikov.testing.mockups;

//import javax.enterprise.inject.Alternative;

import osotnikov.testing.mockups.ProcessingMockup;

//@Alternative
public class ProcessingMockupImpl implements ProcessingMockup {
	
	private int mockupState;

	/* (non-Javadoc)
	 * @see osotnikov.testing.mockups.ProcessingMockup#process(java.lang.String, long)
	 */
	@Override
	public String process(String mockupName, long duration){
		
		java.util.Date startTime = new java.util.Date();
		
		System.out.println("ProcessingMockupImpl is used.");
		
		try{
			Thread.currentThread().sleep(duration);
		}catch(Exception e){
			System.out.println("ERROR: ProcessingMockup failed! [" + mockupName + "]");
			e.printStackTrace();
			return null;
		}
	
		return getProcessingReport(startTime, mockupName);
	}
	
	protected String getProcessingReport(java.util.Date startTime, String mockupName){

		return getClass().getSimpleName() + ": [" + mockupName + 
				"] with processing object hash code [" + this.hashCode() + 
				"] whose internal state is [" + getMockupState() + "] started at [" 
				+ startTime + "] ended at [" + new java.util.Date().toString() + "]";
	}

	/* (non-Javadoc)
	 * @see osotnikov.testing.mockups.ProcessingMockup#getMockupState()
	 */
	@Override
	public int getMockupState() {
		return mockupState;
	}

	/* (non-Javadoc)
	 * @see osotnikov.testing.mockups.ProcessingMockup#setMockupState(int)
	 */
	@Override
	public void setMockupState(int mockupState) {
		this.mockupState = mockupState;
	}
	
}

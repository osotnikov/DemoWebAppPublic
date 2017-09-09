package osotnikov.demowebapp.services.concurrency;

import javax.enterprise.inject.Alternative;

import osotnikov.testing.mockups.ProcessingMockup;
import osotnikov.testing.mockups.ProcessingMockupImpl;

@Alternative
public class ProcessingMockupImpl2 extends ProcessingMockupImpl implements ProcessingMockup{

	@Override
	public String process(String mockupName, long duration){
		
		java.util.Date startTime = new java.util.Date();
		
		System.out.println("ProcessingMockupImpl2 is used.");
		
		try{
			Thread.currentThread().sleep(duration);
		}catch(Exception e){
			System.out.println("ERROR: ProcessingMockup failed! [" + mockupName + "]");
			e.printStackTrace();
			return null;
		}
	
		return getProcessingReport(startTime, mockupName);
	}
	
}

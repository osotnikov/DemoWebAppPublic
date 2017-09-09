package osotnikov.demowebapp.services.concurrency;

import javax.ejb.Local;
import javax.ejb.Stateless;
import osotnikov.testing.mockups.ProcessingMockup;
import osotnikov.testing.ejb.mockups.ProcessingEjbMockupImpl;

@Stateless(name="statelessProcessingBean")
@Local(ProcessingMockup.class)
public class StatelessProcessingBean extends ProcessingEjbMockupImpl implements ProcessingMockup{
	
}

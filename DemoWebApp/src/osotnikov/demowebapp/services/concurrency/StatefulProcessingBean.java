package osotnikov.demowebapp.services.concurrency;

import javax.ejb.Local;
import javax.ejb.Stateful;
import osotnikov.testing.mockups.ProcessingMockup;
import osotnikov.testing.ejb.mockups.ProcessingEjbMockupImpl;

@Stateful(name="statefulProcessingBean")
@Local(ProcessingMockup.class)
public class StatefulProcessingBean extends ProcessingEjbMockupImpl implements ProcessingMockup{
	/*@Resource
	SessionContext context;
	
	public String getSessionBeanID(){
		return String.valueOf(context.getEJBObject().hashCode());
	}
	
	@Override
	public String toString(){
		return super.toString() + " with session bean ID [" + getSessionBeanID() + "]";
	}*/
}

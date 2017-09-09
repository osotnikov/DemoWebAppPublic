package osotnikov.demowebapp.services.concurrency;

import javax.enterprise.context.RequestScoped;
import osotnikov.demowebapp.services.concurrency.qualifiers.RequestScopedQualifier;
import osotnikov.testing.mockups.ProcessingMockup;
import osotnikov.testing.mockups.ProcessingMockupImpl;

@RequestScoped
@RequestScopedQualifier
public class PojoRequestScopedProcessingBean extends ProcessingMockupImpl implements ProcessingMockup{
	
}

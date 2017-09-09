package osotnikov.demowebapp.services.concurrency;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import osotnikov.demowebapp.services.concurrency.qualifiers.RequestScopedEJBQualifier;
import osotnikov.testing.mockups.ProcessingMockup;
import osotnikov.testing.ejb.mockups.ProcessingEjbMockupImpl;


@Stateful(name="statefulRequestScopedProcessingBean")
@Local(ProcessingMockup.class)
@RequestScoped
@RequestScopedEJBQualifier
public class StatefulRequestScopedProcessingBean extends ProcessingEjbMockupImpl implements ProcessingMockup{

}

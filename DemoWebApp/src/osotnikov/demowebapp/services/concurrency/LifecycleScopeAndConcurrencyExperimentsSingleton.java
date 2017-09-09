package osotnikov.demowebapp.services.concurrency;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.inject.Inject;
import osotnikov.demowebapp.services.concurrency.qualifiers.RequestScopedEJBQualifier;
import osotnikov.demowebapp.services.concurrency.qualifiers.RequestScopedQualifier;
import osotnikov.testing.mockups.ProcessingMockup;

// Singleton

/*Annotating a singleton class with @Lock specifies that all the business methods and any 
timeout methods of the singleton will use the specified lock type unless they explicitly 
set the lock type with a method-level @Lock annotation.*/
@Lock(LockType.READ)
public class LifecycleScopeAndConcurrencyExperimentsSingleton implements LifecycleScopeAndConcurrencyExperimentsLocal {

	@EJB(beanName="statelessProcessingBean")
	private ProcessingMockup statelessDependentScopedProcessingBean;
	
	@EJB(beanName="statefulProcessingBean")
	private ProcessingMockup statefulDependentScopedProcessingBean1;
	
	@EJB(beanName="statefulProcessingBean")
	private ProcessingMockup statefulDependentScopedProcessingBean2;
	
	@Inject
	@RequestScopedEJBQualifier
	private ProcessingMockup statefulRequestScopedProcessingBean;
	
	@Inject
	private ProcessingMockup pojoProcessingBean1;
	@Inject
	private ProcessingMockup pojoProcessingBean2;
	@Inject // @EJB Won't take into account the request scope!!!
	@RequestScopedQualifier
	private ProcessingMockup pojoRequestScopedProcessingBean;
	
	@Override
	@Lock(LockType.WRITE)
	public ProcessingMockup getStatelessDependentScopedProcessingBeanWithWriteLock10Seconds() {
		try{Thread.currentThread().sleep(10000);}catch(Exception e){}
		return statelessDependentScopedProcessingBean;
	}
	@Override
	public ProcessingMockup getStatelessDependentScopedProcessingBeanWithReadLock10Seconds() {
		try{Thread.currentThread().sleep(10000);}catch(Exception e){}
		return statelessDependentScopedProcessingBean;
	}
	@Override
	@Lock(LockType.WRITE)
	public ProcessingMockup getStatelessDependentScopedProcessingBeanWithWriteLockInstant() {
		return statelessDependentScopedProcessingBean;
	}
	
	@Override
	public ProcessingMockup getStatelessDependentScopedProcessingBeanWithReadLockInstant() {
		return statelessDependentScopedProcessingBean;
	}
	
	@Override
	public ProcessingMockup getStatefulDependentScopedProcessingBean1() {
		return statefulDependentScopedProcessingBean1;
	}
	
	@Override
	public ProcessingMockup getStatefulDependentScopedProcessingBean2() {
		return statefulDependentScopedProcessingBean2;
	}
	
	
	@Override
	public ProcessingMockup getStatefulRequestScopedProcessingBean() {
		return statefulRequestScopedProcessingBean;
	}
	
	@Override
	public ProcessingMockup getPojoProcessingBean1() {
		return pojoProcessingBean1;
	}
	
	@Override
	public ProcessingMockup getPojoProcessingBean2() {
		return pojoProcessingBean2;
	}
	
	@Override
	public ProcessingMockup getPojoRequestScopedProcessingBean() {
		return pojoRequestScopedProcessingBean;
	}
	
}

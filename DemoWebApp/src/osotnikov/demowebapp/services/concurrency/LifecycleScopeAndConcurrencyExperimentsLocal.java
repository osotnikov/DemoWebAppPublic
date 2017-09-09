package osotnikov.demowebapp.services.concurrency;

import osotnikov.testing.mockups.ProcessingMockup;

public interface LifecycleScopeAndConcurrencyExperimentsLocal {

	ProcessingMockup getStatelessDependentScopedProcessingBeanWithWriteLock10Seconds();
	
	ProcessingMockup getStatelessDependentScopedProcessingBeanWithReadLock10Seconds();
	
	ProcessingMockup getStatelessDependentScopedProcessingBeanWithWriteLockInstant();
	
	ProcessingMockup getStatelessDependentScopedProcessingBeanWithReadLockInstant();

	ProcessingMockup getStatefulDependentScopedProcessingBean1();

	ProcessingMockup getStatefulDependentScopedProcessingBean2();

	ProcessingMockup getStatefulRequestScopedProcessingBean();

	ProcessingMockup getPojoProcessingBean1();

	ProcessingMockup getPojoProcessingBean2();

	ProcessingMockup getPojoRequestScopedProcessingBean();

}
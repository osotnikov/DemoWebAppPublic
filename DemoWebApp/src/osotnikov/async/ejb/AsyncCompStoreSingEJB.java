package osotnikov.async.ejb;

import java.util.HashMap;

import osotnikov.async.services.AsyncCompStoreAbstract;
import osotnikov.async.services.enums.ComputationState;
import osotnikov.async.services.vo.ComputationResponse;
import osotnikov.utils.vo.ObjectWithKey;

public abstract class AsyncCompStoreSingEJB<Key, CReq extends ObjectWithKey<Key>, 
CResp extends ComputationResponse<Key>> extends AsyncCompStoreAbstract<Key, CReq, CResp>{

	public AsyncCompStoreSingEJB(){
		super();
		// The concurrency management will be provided by the Container and the Singleton EJB so
		// no need for a thread safe collection.
		computationsStore = new HashMap<Key, CResp>();
	}
	
	@Override
	// With EJBs it is not possible to call a method with a WRITE Lock from inside a method with
	// a READ Lock because an exception is thrown. So if you have chosen EJB as your solution
	// to concurrency then you must lock the whole structure on the method that gets called first
	// and all the methods that it calls and all the methods that these methods call must have
	// a WRITE lock. So basically with EJB managed concurrency the whole structure locks
	// with every action that is performed on it, in most cases. The reason for that is that
	// a READ method also obtains a lock but just for the case of an another WRITE method that
	// may be executed at the same time, so in cases of one method calling another, because
	// all methods obtain the same lock, in cases of a READ method calling a WRITE method
	// a deadlock occurs and that is why an exception is thrown, in all other cases there
	// is no exception though.
	public CResp requestAsyncComputationAndUpdateStatus(CReq compReq) {
		// An EJB does not have to handle concurrency issues in case of the default Lock setting
		// of LockType.WRITE (which the requestComputation method is going to have).
		
		CResp cResp = createPlainCRespInstance();
		boolean requestForComputationHasBeenSuccessfullyMade = requestAsyncComputation(compReq);
		
		if(requestForComputationHasBeenSuccessfullyMade){
			cResp.setCompState(ComputationState.UNDER_PROCESSING);
		}else{
			cResp.setCompState(ComputationState.FAILED);
		}
		
		return cResp;
	}

}

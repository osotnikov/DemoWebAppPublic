package osotnikov.async.services;

import java.util.Map;

import osotnikov.async.services.enums.ComputationState;
import osotnikov.async.services.vo.ComputationResponse;
import osotnikov.utils.vo.ObjectWithKey;

/**
 * The concurrency handling may be provided inside or outside of this abstract class.
 * No concurrency mechanisms are implemented inside this abstract class, only an interface
 * that may enable such mechanisms.
 * */
public abstract class AsyncCompStoreAbstract<Key, CReq extends ObjectWithKey<Key>, CResp extends ComputationResponse<Key>> 
implements AsyncCompStore<Key, CReq, CResp>{
	
	// Stores each computation by its key. First parameter is the CResp key.
	protected Map<Key, CResp> computationsStore;
	
	/* (non-Javadoc)
	 * @see osotnikov.async.services.AsyncCompStore#requestComputation(CReq)
	 */
	@Override
	public CResp requestComputation(CReq compReq){
		
		CResp compResp = computationsStore.get(compReq.getKey());

		if(compResp != null && (ComputationState.COMPUTED.equals(compResp.getCompState()) ||
		   ComputationState.UNDER_PROCESSING.equals(compResp.getCompState()))){
	
			return compResp;
		}else if(compResp == null || ComputationState.FAILED.equals(compResp.getCompState()) ||
				ComputationState.NOT_REQUESTED.equals(compResp.getCompState())){
			
			compResp = requestAsyncComputationAndUpdateStatus(compReq);
			return compResp;
		}
		
		return compResp;
	}
	
	/* (non-Javadoc)
	 * @see osotnikov.async.services.AsyncCompStore#requestAsyncComputationAndUpdateStatus(CReq)
	 */
	@Override
	public abstract CResp requestAsyncComputationAndUpdateStatus(CReq compReq);
	
	/* (non-Javadoc)
	 * @see osotnikov.async.services.AsyncCompStore#requestAsyncComputation(CReq)
	 */
	@Override
	public abstract boolean requestAsyncComputation(CReq compReq);
	
	/* (non-Javadoc)
	 * @see osotnikov.async.services.AsyncCompStore#storeComputation(CResp)
	 */
	@Override
	public void storeComputation(CResp cResp){
		computationsStore.put(cResp.getKey(), cResp);
	}
	
	/* (non-Javadoc)
	 * @see osotnikov.async.services.AsyncCompStore#createPlainCRespInstance()
	 */
	@Override
	public abstract CResp createPlainCRespInstance();
}

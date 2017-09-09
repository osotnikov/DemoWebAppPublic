package osotnikov.async.services;

import osotnikov.async.services.vo.ComputationResponse;
import osotnikov.utils.vo.ObjectWithKey;

public interface AsyncCompStore<Key, CReq extends ObjectWithKey<Key>, CResp extends ComputationResponse<Key>> {

	/** Every request for computation should be made from here. */
	CResp requestComputation(CReq compReq);

	/** The purpose of this method is to model the atomic action of submitting an asynchronous
	 *  request and updating the state of the computation to ComputationState.UNDER_PROCESSING
	 *  iff the computation was successfully requested or to ComputationState.FAILED if the 
	 *  request has failed. There could me a number of ways to obtain the lock e.g. on the
	 *  whole container structure or only on the element representing the computation. This
	 *  method serves mainly to model the obtaining of the lock. */
	CResp requestAsyncComputationAndUpdateStatus(CReq compReq);

	/** @return Returns true if the asynchronous computation was successfully requested or false if the
	 *  request has failed. */
	boolean requestAsyncComputation(CReq compReq);

	/** This model has to handle concurrency issues. */
	void storeComputation(CResp cResp);

	/** Can only be provided by the last class in the hierarchy. */
	CResp createPlainCRespInstance();

}
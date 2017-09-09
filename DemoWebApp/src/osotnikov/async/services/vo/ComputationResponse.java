package osotnikov.async.services.vo;

import osotnikov.async.services.enums.ComputationState;
import osotnikov.utils.vo.ObjectWithKey;

public abstract class ComputationResponse<Key> extends ObjectWithKey<Key>{

	private static final long serialVersionUID = 1L;
	
	private ComputationState compState;
	// Lacks the result state.

	public ComputationState getCompState() {
		return compState;
	}

	public void setCompState(ComputationState computationState) {
		this.compState = computationState;
	}
	
}

package osotnikov.demowebapp.services.fibonacci.vo;

import osotnikov.async.services.vo.ComputationResponse;

public class ComputeFibonacciResponse extends ComputationResponse<Integer>{
	
	private static final long serialVersionUID = 1L;
	
	private Integer termInd;
	private Integer result;

	public Integer getTermInd() {
		return termInd;
	}

	public void setTermInd(Integer termInd) {
		this.termInd = termInd;
	}

	@Override
	public Integer getKey() {
		return getTermInd();
	}

	@Override
	public void setKey(Integer key) {
		setTermInd(key);
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ComputeFibonacciResponse [termInd=" + termInd + ", result=" + result + "]";
	}
	
	
}

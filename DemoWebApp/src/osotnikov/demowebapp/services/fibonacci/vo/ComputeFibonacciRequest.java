package osotnikov.demowebapp.services.fibonacci.vo;

import osotnikov.demowebapp.services.user_management.vo.User;
import osotnikov.utils.vo.ObjectWithKey;

public class ComputeFibonacciRequest extends ObjectWithKey<Integer> {

	private static final long serialVersionUID = 1L;

	private Integer termInd;
	private User user;

	public Integer getTermInd() {
		return termInd;
	}

	public void setTermInd(Integer termInd) {
		this.termInd = termInd;
	}
	
	@Override
	public Integer getKey(){
		return termInd;
	}
	
	@Override
	public void setKey(Integer key){
		termInd = key;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}

package osotnikov.demowebapp.services.counter;

import javax.ejb.Stateful;

@Stateful(name="CounterServiceBean")
public class CounterServiceBean implements CounterService{

	int count = 0;	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public void incrementCount() {
		++count;
	}

}

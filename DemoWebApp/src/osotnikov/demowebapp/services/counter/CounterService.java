package osotnikov.demowebapp.services.counter;

import javax.ejb.Local;

@Local
public interface CounterService {
	public int getCount();
	public void incrementCount();
}

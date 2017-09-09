package osotnikov.demowebapp.services.fibonacci;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import osotnikov.async.ejb.AsncCmpStrSingToMDB;
import osotnikov.async.services.AsyncCompStore;
import osotnikov.demowebapp.services.fibonacci.vo.ComputeFibonacciRequest;
import osotnikov.demowebapp.services.fibonacci.vo.ComputeFibonacciResponse;

@Singleton(name="FibonacciComputationsStoreSingleton")
@Local(AsyncCompStore.class)
public class FibonacciComputationsStoreSingleton
	extends AsncCmpStrSingToMDB<Integer, ComputeFibonacciRequest, ComputeFibonacciResponse>{
	
	@Override
	// ConnectionFactory, Connection and Queue (Destination in general) objects are 
	// thread safe (not that it matters here). Session, MessageProducer, and 
	// MessageConsumer are not. 
	@Resource(
		name="jms/CF" ,// Will create the reference to the lookup resource in the ENC with the specified name.
	    type=javax.jms.ConnectionFactory.class,
	    lookup="WebAppDemo/jms/conFactories/CF") // Will lookup the resource in the JNDI context.
	public void setConnFactory(ConnectionFactory connectionFactory) {
		super.setConnFactory(connectionFactory);
	}

	@Override
	@Resource(
		name="jms/Q", //You can also specify the lookup value here, this attribute exhibits overloaded behavior...
	    type=javax.jms.Queue.class,
	    lookup = "WebAppDemo/jms/queues/computationalRequestsQueue")
	public void setQueue(Queue fibonnaciQueue) {
		super.setQueue(fibonnaciQueue);
	}

	@Override
	@Lock(LockType.READ)
	public ComputeFibonacciResponse createPlainCRespInstance() {
		
		return new ComputeFibonacciResponse();
	}

}

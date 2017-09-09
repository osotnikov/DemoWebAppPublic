package osotnikov.async.ejb;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import osotnikov.async.services.vo.ComputationResponse;
import osotnikov.utils.vo.ObjectWithKey;

public abstract class AsncCmpStrSingToMDB<Key, CReq extends ObjectWithKey<Key>, CResp extends ComputationResponse<Key>>
extends AsyncCompStoreSingEJB<Key, CReq, CResp>{

	private ConnectionFactory connFactory;
	private Queue queue;
	
	@Override
	public boolean requestAsyncComputation(CReq compReq) {
		
		Connection jmsCon = null;
		Session jmsSess = null;
		MessageProducer prod = null;
		ObjectMessage objMsg = null;
		
		try {
			// SEND THE MESSAGE TO THE QUEUE
			jmsCon = getConnFactory().createConnection();
			jmsSess = jmsCon.createSession();
			prod = jmsSess.createProducer(getQueue());
			objMsg = jmsSess.createObjectMessage();
			objMsg.setObject(compReq);
			prod.send(objMsg);
			
			return true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}finally{
			
			try {
				if (prod != null) {
					prod.close();
				}
				if (jmsSess != null) {
					jmsSess.close();
				}
				if (jmsCon != null) {
					jmsCon.close();
				}
			} catch (JMSException e) {
				System.out.println("Failed to release a JMS Resource but it will probably be garbage collected...");
			}
		}
	}

	// ConnectionFactory, Connection and Queue (Destination in general) objects are 
	// thread safe (not that it matters in the case of an EJB). Session, 
	// MessageProducer, and MessageConsumer are not.
	public ConnectionFactory getConnFactory() {
		return connFactory;
	}

	public void setConnFactory(ConnectionFactory connFactory) {
		this.connFactory = connFactory;
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}
}

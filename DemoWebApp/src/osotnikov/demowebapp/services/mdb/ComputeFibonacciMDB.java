package osotnikov.demowebapp.services.mdb;

import java.util.Date;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import osotnikov.async.services.AsyncCompStore;
import osotnikov.async.services.enums.ComputationState;
import osotnikov.demowebapp.services.fibonacci.constants.FibCompStep;
import osotnikov.demowebapp.services.fibonacci.entity.FibCompBill;
import osotnikov.demowebapp.services.fibonacci.entity.FibCompCrmLog;
import osotnikov.demowebapp.services.fibonacci.vo.ComputeFibonacciRequest;
import osotnikov.demowebapp.services.fibonacci.vo.ComputeFibonacciResponse;
import osotnikov.utils.MathUtils;
 
@MessageDriven(
  name = "ComputeFibonacciBean",
  activationConfig = {
    @ActivationConfigProperty(propertyName  = "destinationType", 
                              propertyValue = "javax.jms.Queue"),

    @ActivationConfigProperty(propertyName  = "connectionFactoryJndiName",
                              propertyValue = "WebAppDemo/jms/conFactories/CF"), // External JNDI Name
 
    @ActivationConfigProperty(propertyName  = "destinationJndiName",
                              propertyValue = "WebAppDemo/jms/queues/computationalRequestsQueue"), // Ext. JNDI Name

  }
)
public class ComputeFibonacciMDB implements MessageListener {

	@Inject
	MathUtils mathUtils;
	
	@EJB(beanName="FibonacciComputationsStoreSingleton")
	AsyncCompStore<Integer, ComputeFibonacciRequest, ComputeFibonacciResponse> fibCompStore;
	
	@PersistenceContext(unitName="web_demo_pu")
    private EntityManager webDemoEm;
	
	@PersistenceContext(unitName="web_demo_crm_pu")
    private EntityManager webDemoCrmEm;
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED) 
	// Don't forget to set the JTA transaction time limit in the server's console.
	public void onMessage(Message msg) {

		ComputeFibonacciResponse fibResp = new ComputeFibonacciResponse();
		fibResp.setCompState(ComputationState.UNDER_PROCESSING);

		try{
			
			System.out.println("ComputeFibonacciMDB onMessage started at: " + new Date());
			
			// EXTRACT THE JMSMessageID
			String jmsMsgId;
			jmsMsgId = msg.getJMSMessageID();// may throw a JMSException
			System.out.println("JMSMessageId: " + jmsMsgId);

			// EXTRACT REQUEST
			ComputeFibonacciRequest fibReq = null;
			try {
				fibReq = (ComputeFibonacciRequest)(((ObjectMessage)msg).getObject());
			} catch (JMSException e) {
				
				fibResp.setCompState(ComputationState.FAILED);
				fibCompStore.storeComputation(fibResp);
				
				e.printStackTrace();
				
				return;
			}
			
			// SET THE TERM INDEX (fibonacci computation argument) OF THE RESPONSE
			fibResp.setTermInd(fibReq.getTermInd());
			
			// LOG THE COMPUTATION STEP
			System.out.println("ComputeFibonacciMDB: JMSMessageId: " + jmsMsgId + 
				", FibCompStep: " + FibCompStep.STARTED.getStepName());
			
			// DO THE COMPUTATION
			Date compStartTime = new Date();
			fibResp.setResult(mathUtils.computeFibonacci(fibReq.getTermInd()));
			Date compEndTime = new Date();
			
			// STORE THE RESULT IN THE COMPUTATION STORE
			fibResp.setCompState(ComputationState.COMPUTED);
			fibCompStore.storeComputation(fibResp);
					
			// LOG THE COMPUTATION STEP
			System.out.println("ComputeFibonacciMDB: JMSMessageId: " + jmsMsgId + 
					", FibCompStep: " + FibCompStep.COMPUTED.getStepName());
			
			// CREATE A BILLING RECORD
			FibCompBill fibCompBill = new FibCompBill();
			fibCompBill.setTermInd(fibReq.getTermInd());
			fibCompBill.setResult(fibResp.getResult());
			fibCompBill.setStartTime(compStartTime);
			fibCompBill.setEndTime(compEndTime);
			long duration = compEndTime.getTime() - compStartTime.getTime();
			fibCompBill.setDuration(duration);
			long cost = duration * FibCompBill.BILLING_FACTOR;
			fibCompBill.setCost(cost);
			fibCompBill.setUser(fibReq.getUser());
			
			// STORE fibComBill RECORD IN DB
			webDemoEm.persist(fibCompBill);
			webDemoEm.flush(); 
			
			// LOG THE COMPUTATION STEP
			System.out.println("ComputeFibonacciMDB: JMSMessageId: " + jmsMsgId + 
					", FibCompStep: " + FibCompStep.CREATED_BILL.getStepName());
			
			// CREATE A CRM RECORD
			FibCompCrmLog fibCompCrmLog = new FibCompCrmLog();
			fibCompCrmLog.setTermInd(fibReq.getTermInd());
			fibCompCrmLog.setResult(fibResp.getResult());
			fibCompCrmLog.setStartTime(compStartTime);
			fibCompCrmLog.setEndTime(compEndTime);
			fibCompCrmLog.setDuration(duration);
			fibCompCrmLog.setCost(cost);
			webDemoEm.refresh(fibCompBill);// Make sure we get the billing id...
			fibCompCrmLog.setBillingId(fibCompBill.getId());
			fibCompCrmLog.setUsername(fibReq.getUser().getEmail());
			String firstAndLastname = fibReq.getUser().getFirstName() + " " + fibReq.getUser().getLastName();
			fibCompCrmLog.setFirstAndLastname(firstAndLastname);

			// STORE fibComBill RECORD IN DB
			webDemoCrmEm.persist(fibCompCrmLog);
			webDemoCrmEm.flush();
			
			// LOG THE COMPUTATION STEP
			System.out.println("ComputeFibonacciMDB: JMSMessageId: " + jmsMsgId + 
				", FibCompStep: " + FibCompStep.CREATED_CRM_LOG.getStepName());
			
		}catch(Exception e){
			// Mostly in case of runtime exceptions.
			e.printStackTrace();
			
		}finally{
			
			if(ComputationState.UNDER_PROCESSING.equals(fibResp.getCompState())){
				fibResp.setCompState(ComputationState.FAILED);
				fibCompStore.storeComputation(fibResp);
			}
		}
		
	}
 
}
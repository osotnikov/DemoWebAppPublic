package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import osotnikov.constants.OperationResult;
import osotnikov.demowebapp.services.concurrency.LifecycleScopeAndConcurrencyExperimentsLocal;
import osotnikov.demowebapp.services.concurrency.qualifiers.RequestScopedEJBQualifier;
import osotnikov.demowebapp.services.concurrency.qualifiers.RequestScopedQualifier;
import osotnikov.demowebapp.web.constants.LifecycleScopeAndConcurrencyExperimentType;
import osotnikov.testing.mockups.ProcessingMockup;
import osotnikov.web.json.JsonResponse;

public class LifecycleScopeAndConcurrencyExperimentsServlet extends HttpServlet{

	@EJB
	private LifecycleScopeAndConcurrencyExperimentsLocal lcycleSingleton;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		System.out.println("LifecycleScopeAndConcurrencyExperimentsLocal.doGet: started for request: "
			+ request.getRequestURL());
		
		String experimentType = request.getParameter("experimentType");
		
		JsonResponse jsonRes = new JsonResponse(OperationResult.ERROR);
		if(experimentType != null){
			
			String processingReport = null;
			
			lcycleSingleton.getStatelessDependentScopedProcessingBeanWithReadLockInstant().setMockupState(1);
			lcycleSingleton.getStatefulDependentScopedProcessingBean1().setMockupState(2);
			lcycleSingleton.getStatefulDependentScopedProcessingBean2().setMockupState(3);
			lcycleSingleton.getStatefulRequestScopedProcessingBean().setMockupState(4);
			lcycleSingleton.getPojoProcessingBean1().setMockupState(5);
			lcycleSingleton.getPojoProcessingBean2().setMockupState(6);
			lcycleSingleton.getPojoRequestScopedProcessingBean().setMockupState(7);
			
			if(LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE_WRITE_LOCK_10_SEC.
				getName().equals(experimentType)){
				
				processingReport = lcycleSingleton.getStatelessDependentScopedProcessingBeanWithWriteLock10Seconds().process(
					LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE_WRITE_LOCK_10_SEC.getName(),
					3000);
			}else if(LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE_READ_LOCK_10_SEC.
				getName().equals(experimentType)){
				
				processingReport = lcycleSingleton.getStatelessDependentScopedProcessingBeanWithReadLock10Seconds().process(
					LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE_READ_LOCK_10_SEC.getName(),
					3000);
			}else if(LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE_WRITE_LOCK_INSTANT.
				getName().equals(experimentType)){
			
				processingReport = lcycleSingleton.getStatelessDependentScopedProcessingBeanWithWriteLockInstant().process(
					LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE_WRITE_LOCK_INSTANT.getName(),
					3000);
			}else if(LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE.
				getName().equals(experimentType)){
			
				processingReport = lcycleSingleton.getStatelessDependentScopedProcessingBeanWithReadLockInstant().process(
					LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE.getName(),
					3000);
			}
			else if(LifecycleScopeAndConcurrencyExperimentType.STATEFUL_DEPENDENT_SCOPE_1.
				getName().equals(experimentType)){
				
				processingReport = lcycleSingleton.getStatefulDependentScopedProcessingBean1().process(
					LifecycleScopeAndConcurrencyExperimentType.STATEFUL_DEPENDENT_SCOPE_1.getName(),
					3000);
			}else if(LifecycleScopeAndConcurrencyExperimentType.STATEFUL_DEPENDENT_SCOPE_2.
				getName().equals(experimentType)){
			
				processingReport = lcycleSingleton.getStatefulDependentScopedProcessingBean2().process(
					LifecycleScopeAndConcurrencyExperimentType.STATEFUL_DEPENDENT_SCOPE_2.getName(),
					3000);
			}else if(LifecycleScopeAndConcurrencyExperimentType.STATEFUL_REQ_SCOPE.
				getName().equals(experimentType)){
			
				processingReport = lcycleSingleton.getStatefulRequestScopedProcessingBean().process(
					LifecycleScopeAndConcurrencyExperimentType.STATEFUL_REQ_SCOPE.getName(),
					3000);
			}else if(LifecycleScopeAndConcurrencyExperimentType.POJO_DEPENDENT_SCOPE_1.
				getName().equals(experimentType)){
			
				processingReport = lcycleSingleton.getPojoProcessingBean1().process(
					LifecycleScopeAndConcurrencyExperimentType.POJO_DEPENDENT_SCOPE_1.getName(),
					3000);
			}else if(LifecycleScopeAndConcurrencyExperimentType.POJO_DEPENDENT_SCOPE_2.
				getName().equals(experimentType)){
			
				processingReport = lcycleSingleton.getPojoProcessingBean2().process(
					LifecycleScopeAndConcurrencyExperimentType.POJO_DEPENDENT_SCOPE_2.getName(),
					3000);
			}else if(LifecycleScopeAndConcurrencyExperimentType.POJO_REQ_SCOPE.
				getName().equals(experimentType)){
			
				processingReport = lcycleSingleton.getPojoRequestScopedProcessingBean().process(
					LifecycleScopeAndConcurrencyExperimentType.POJO_REQ_SCOPE.getName(),
					3000);
			}
			
			jsonRes.setStatus(OperationResult.SUCCESS);
			jsonRes.setData(processingReport);
		}else{
			jsonRes.setStatus(OperationResult.ERROR);
		}
		
		// RESPONSE
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter resOut = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        String jsonResString = mapper.writeValueAsString(jsonRes);
        
        resOut.write(jsonResString);
        
        System.out.println("LifecycleScopeAndConcurrencyExperimentsLocal.doGet: ended for request: "
    		+ request.getRequestURL());
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
	
}

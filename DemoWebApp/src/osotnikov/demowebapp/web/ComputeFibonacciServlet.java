
package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import osotnikov.async.services.AsyncCompStore;
import osotnikov.constants.OperationResult;
import osotnikov.demowebapp.constants.SessionAttributeEnum;
import osotnikov.demowebapp.services.fibonacci.FibonacciComputationsStoreSingleton;
import osotnikov.demowebapp.services.fibonacci.vo.ComputeFibonacciRequest;
import osotnikov.demowebapp.services.fibonacci.vo.ComputeFibonacciResponse;
import osotnikov.demowebapp.services.user_management.vo.User;
import osotnikov.web.json.JsonResponse;

public class ComputeFibonacciServlet extends HttpServlet {

	@EJB(beanName="FibonacciComputationsStoreSingleton")
	AsyncCompStore<Integer, ComputeFibonacciRequest, ComputeFibonacciResponse> fibCompStore;
	
    public ComputeFibonacciServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		int termInd = Integer.valueOf(request.getParameter("termInd"));
		System.out.println("ComputeFibonacciServlet.doGet ... started, termInd: " + termInd);
		
		ComputeFibonacciRequest cFibReq = new ComputeFibonacciRequest();
		cFibReq.setTermInd(termInd);
		User loggedInUser = (User)(request.getSession().getAttribute(SessionAttributeEnum.LOGGED_IN_USER.getName()));
		cFibReq.setUser(loggedInUser);
		
		// MAKE THE REQUEST FOR THE ASYNCHRONOUS COMPUTATION
		ComputeFibonacciResponse cFibResp = fibCompStore.requestComputation(cFibReq);
		
		// STORE THE REQUEST IN SESSION
		request.getSession().setAttribute(SessionAttributeEnum.LAST_FIB_COMP_REQ.getName(), cFibReq);
		
		// CREATE THE HTTP RESPONSE BODY
		
		JsonResponse jsonRes = new JsonResponse(OperationResult.SUCCESS); // The json response object.
        ObjectMapper mapper = new ObjectMapper();
        jsonRes.setData(cFibResp);
        
        String jsonResponseString = mapper.writeValueAsString(jsonRes);
        
        System.out.println("ComputeFibonacciServlet.doGet: jsonResponseString: " + 
        		jsonResponseString);
       
        response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter resOut = response.getWriter();
        resOut.write(jsonResponseString);
		    
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		System.out.println("TimeServlet.doPost: started ... just calls doGet ...");
		doGet(request, response);
		
	}

}

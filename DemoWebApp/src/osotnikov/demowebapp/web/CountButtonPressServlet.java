package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

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

import osotnikov.constants.OperationResult;
import osotnikov.demowebapp.services.counter.CounterService;
import osotnikov.web.json.JsonResponse;

public class CountButtonPressServlet extends HttpServlet {

	@EJB(beanName="CounterServiceBean")
	CounterService counterService;
	
    public CountButtonPressServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		System.out.println("CountButtonPressServlet.doGet: started for request: "
				+ request.getRequestURL());
		
		counterService.incrementCount();
		int curCount = counterService.getCount();
		
		System.out.println("CountButtonPressServlet, curCount: " + curCount);
		
		JsonResponse jsonRes = new JsonResponse(OperationResult.SUCCESS); // The json response object.

		// CREATE THE HTTP RESPONSE BODY
        
        ObjectMapper mapper = new ObjectMapper();
        jsonRes.setData(new Integer(curCount));
        
        String jsonResponseString = mapper.writeValueAsString(jsonRes);
        
        System.out.println("CountButtonPressServlet.doGet: jsonResponseString: " + 
        		jsonResponseString);
       
        response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter resOut = response.getWriter();
        resOut.write(jsonResponseString);
        
        System.out.println("CountButtonPressServlet.doGet: ended for request: "
			+ request.getRequestURL());
		    
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		System.out.println("CountButtonPressServlet.doPost: started ... just calls doGet ...");
		doGet(request, response);
		
	}

}

package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import osotnikov.constants.OperationResult;
import osotnikov.web.json.JsonResponse;

public class PathInfoReaderServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		System.out.println("PathInfoReaderServlet.doGet ... started.");
		
		// Parse the path parameter from the request URL.
		String pathInfo = request.getPathInfo().substring(1);// Get rid of the first slash.
		System.out.println("PathInfoReaderServlet.doGet ... pathInfo: " + pathInfo);
		String[] pathParams =pathInfo.split("/");
		System.out.println("PathInfoReaderServlet.doGet ... pathParams: " + Arrays.toString(pathParams));

		JsonResponse jsonRes = new JsonResponse(OperationResult.SUCCESS); // The json response object.

		// CREATE THE HTTP RESPONSE BODY
        
        ObjectMapper mapper = new ObjectMapper();
        jsonRes.setData(new Integer(0));
        
        String jsonResponseString = mapper.writeValueAsString(jsonRes);
        
        System.out.println("PathInfoReaderServlet.doGet: jsonResponseString: " + 
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

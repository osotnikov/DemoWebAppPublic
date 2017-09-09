package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import osotnikov.constants.OperationResult;
import osotnikov.demowebapp.services.user_management.vo.User;
import osotnikov.demowebapp.ws.client.DemoWebAppWsEndpoint;
import osotnikov.demowebapp.ws.client.DemoWebAppWsEndpointService;
import osotnikov.demowebapp.ws.client.MessageToDemoWebAppWS;
import osotnikov.web.json.JsonResponse;

public class DemoWsRemoteSysInfoServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

			System.out.println("DemoWsRemoteSysInfoServlet.doGet: started for request: "
					+ request.getRequestURL());
			
			ObjectMapper mapper = new ObjectMapper();
			
			// GET THE PORT TO THE REMOTE WS
			DemoWebAppWsEndpointService demoWsService = new DemoWebAppWsEndpointService();
			DemoWebAppWsEndpoint demoWsServicePort = demoWsService.getDemoWebAppWsEndpointPort();
			// RECEIVE THE INFO
			String remoteSysInfo = demoWsServicePort.getSystemInfo();
			remoteSysInfo.replace("\n", "<br/>");
			
			System.out.println("DemoWsRemoteSysInfoServlet.doGet: Received remote system info " + 
				"(remoteSysInfo): " + remoteSysInfo);
			
			JsonResponse jsonRes = new JsonResponse(OperationResult.SUCCESS); // The json response object.
			jsonRes.setData(remoteSysInfo);
			// CREATE THE HTTP RESPONSE BODY
	        mapper = new ObjectMapper();
	        
	        String jsonResponseString = mapper.writeValueAsString(jsonRes);
	        
	        System.out.println("DemoWsRemoteSysInfoServlet.doGet: jsonResponseString: " + 
	        		jsonResponseString);
	       
	        response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter resOut = response.getWriter();
	        resOut.write(jsonResponseString);
	        
	        System.out.println("DemoWsRemoteSysInfoServlet.doGet: ended for request: "
				+ request.getRequestURL());
			    
		}


		protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			
			System.out.println("DemoWsRemoteSysInfoServlet.doPost: started ... just calls doGet ...");
			doGet(request, response);
			
		}
}

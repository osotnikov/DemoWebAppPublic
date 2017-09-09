package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

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

public class DemoWsMsgSenderServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

			System.out.println("DemoWsMsgSenderServlet.doGet: started for request: "
					+ request.getRequestURL());
			
			String jsonData = request.getParameter("jsonData");
			
			System.out.println("DemoWsMsgSenderServlet.doPost: received jsonData: " + jsonData);
			
			ObjectMapper mapper = new ObjectMapper();
			
			MessageToDemoWebAppWS msgToDemoWs = mapper.readValue(jsonData, MessageToDemoWebAppWS.class);
			
			System.out.println("DemoWsMsgSenderServlet.doPost: parsed jsonData to MessageToDemoWebAppWS: " + msgToDemoWs);
			
			// Add some more dummy info.
			msgToDemoWs.setMessageId(new Random().nextInt(1000000000));
			msgToDemoWs.setCallerId(String.valueOf(this.hashCode()));
			msgToDemoWs.getTextMessage().setSender(request.getUserPrincipal().getName());
			
			// GET THE PORT TO THE REMOTE WS
			DemoWebAppWsEndpointService demoWsService = new DemoWebAppWsEndpointService();
			DemoWebAppWsEndpoint demoWsServicePort = demoWsService.getDemoWebAppWsEndpointPort();
			// SEND THE MESSAGE
			demoWsServicePort.sendObjectMessage(msgToDemoWs);
			
			JsonResponse jsonRes = new JsonResponse(OperationResult.SUCCESS); // The json response object.

			// CREATE THE HTTP RESPONSE BODY
	        
	        mapper = new ObjectMapper();
	        
	        String jsonResponseString = mapper.writeValueAsString(jsonRes);
	        
	        System.out.println("DemoWsMsgSenderServlet.doGet: jsonResponseString: " + 
	        		jsonResponseString);
	       
	        response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter resOut = response.getWriter();
	        resOut.write(jsonResponseString);
	        
	        System.out.println("DemoWsMsgSenderServlet.doGet: ended for request: "
				+ request.getRequestURL());
			    
		}


		protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
			
			System.out.println("DemoWsMsgSenderServlet.doPost: started ... just calls doGet ...");
			doGet(request, response);
			
		}
}

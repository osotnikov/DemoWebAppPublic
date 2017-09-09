package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import osotnikov.constants.OperationResult;
import osotnikov.demowebapp.services.user_management.session.UserManagementService;
import osotnikov.demowebapp.services.user_management.vo.User;
import osotnikov.web.json.JsonResponse;

public class RegistrationServlet extends HttpServlet {
    
	@EJB(beanName="UserManagementService")
	UserManagementService userManagementBean;
	
    public RegistrationServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		System.out.println("RegistrationServlet.doPost started for request: "
			+ request.getRequestURL());
		
		String jsonData = request.getParameter("jsonData");
		
		System.out.println("RegistrationServlet.doPost: received jsonData: " + jsonData);
		
		ObjectMapper mapper = new ObjectMapper();
		
		User user = mapper.readValue(jsonData,User.class);
		
		System.out.println("RegistrationServlet.doPost: parsed jsonData to User: " + user);
		
		user = userManagementBean.validateTweakAndRegisterUser(user);
		
		JsonResponse jsonRes = new JsonResponse(OperationResult.ERROR);
				
		if(user != null){
			System.out.println("RegistrationServlet.doPost: Successfully registered user: " + user);
			jsonRes = new JsonResponse(OperationResult.SUCCESS);
		}else{
			System.out.println("RegistrationServlet.doPost: Failed to register the user.");
			// jsonRes = new JsonResponse(ResponseStatus.ERROR);
		}
		
		// RESPONSE
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter resOut = response.getWriter();

        jsonRes.setData(user);
        
        String jsonResString = mapper.writeValueAsString(jsonRes);
        System.out.println("RegistrationServlet.doPost: jsonResString: " + jsonResString);
        
        resOut.write(jsonResString);
        
        System.out.println("RegistrationServlet.doPost ended for request: "
    			+ request.getRequestURL());
		    
	}

}

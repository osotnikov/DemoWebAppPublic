package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import osotnikov.constants.OperationResult;
import osotnikov.demowebapp.constants.SessionAttributeEnum;
import osotnikov.demowebapp.services.user_management.session.UserManagementService;
import osotnikov.demowebapp.services.user_management.vo.User;
import osotnikov.web.json.JsonResponse;

public class LoginServlet extends HttpServlet {
    
	@EJB
	UserManagementService userManagementBean;
	
    public LoginServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		System.out.println("LoginServlet.doPost: started for request: "
			+ request.getRequestURL());
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		System.out.println("LoginServlet.doPost: received parameters, email: " + email + ", password: " + password);
		
		User user = userManagementBean.loginAndFetchUser(email, password, request);
		
		JsonResponse jsonRes = new JsonResponse(OperationResult.ERROR);
		if(user != null){
			jsonRes.setStatus(OperationResult.SUCCESS);
			jsonRes.setData(user);
		}else{
			jsonRes.setStatus(OperationResult.ERROR);
		}
		
		// RESPONSE
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter resOut = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        String jsonResString = mapper.writeValueAsString(jsonRes);
        
        System.out.println("LoginServlet.doPost: jsonResString: " + jsonResString);
        
        //System.out.println("LoginServlet.doPost: is user logged in? : " + request.getUserPrincipal().getName());
        System.out.println("LoginServlet.doPost: request.isUserInRole(\"ADMINISTRATOR\")? : " + request.isUserInRole("ADMINISTRATOR"));
        System.out.println("LoginServlet.doPost: request.isUserInRole(\"USER\")? : " + request.isUserInRole("USER"));
        System.out.println("LoginServlet.doPost: request.isUserInRole(\"users\")? : " + request.isUserInRole("users"));
        System.out.println("LoginServlet.doPost: request.isUserInRole(\"everyone\")? : " + request.isUserInRole("everyone"));
        
        resOut.write(jsonResString);
        
        System.out.println("LoginServlet.doPost: ended for request: "
    		+ request.getRequestURL());
		    
	}

}

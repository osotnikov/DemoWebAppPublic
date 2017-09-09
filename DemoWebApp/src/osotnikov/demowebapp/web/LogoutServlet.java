package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import osotnikov.constants.OperationResult;
import osotnikov.web.json.JsonResponse;

@WebServlet("/services/auth/logout")
public class LogoutServlet extends HttpServlet {
    
    public LogoutServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		System.out.println("LogoutServlet.doGet: started for request: "
			+ request.getRequestURL());

		JsonResponse jsonRes = new JsonResponse(OperationResult.ERROR);

        try {
        	request.logout();
        	jsonRes.setStatus(OperationResult.SUCCESS);
        	request.getSession().invalidate();
        	System.out.println("LogoutServlet.doGet: Logout successfull!");
        } catch (ServletException e) {
            e.printStackTrace();
            jsonRes.setStatus(OperationResult.ERROR);
            jsonRes.setErrorMsg("Logout failed on backend!");
            System.out.println("LogoutServlet.doGet: Logout failed on backend!");
        }
		
		// RESPONSE
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter resOut = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        String jsonResString = mapper.writeValueAsString(jsonRes);
        
        System.out.println("LoginServlet.doGet: jsonResString: " + jsonResString);
        
        System.out.println("LogoutServlet.doGet: is user logged in? : request.getUserPrincipal(): " + request.getUserPrincipal());
        
        resOut.write(jsonResString);
        
        System.out.println("LogoutServlet.doGet: ended for request: "
    		+ request.getRequestURL());
		    
	}

}

package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import osotnikov.constants.OperationResult;
import osotnikov.demowebapp.constants.SessionAttributeEnum;
import osotnikov.utils.StringUtils;
import osotnikov.web.json.JsonResponse;

public class ChooseCommonBackgroundColorServlet extends HttpServlet{
	
	@Inject
	StringUtils stringUtils;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse response) 
			throws ServletException, IOException {
		
		JsonResponse jsonRes = new JsonResponse(OperationResult.ERROR);
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		String chosenColor = stringUtils.getTrimmedOrEmpty(request.getParameter("chosen_bg_color"));
		
		System.out.println("ChooseCommonBackgroundColorServlet.doGet: started for request: "
			+ request.getRequestURL() + "\nchosenColor: " + chosenColor);

		request.getSession().setAttribute(SessionAttributeEnum.COMMON_BG_COLOR_SESSION_ATT.getName(), chosenColor);

		// RESPONSE

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter resOut = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        
        jsonRes.setStatus(OperationResult.SUCCESS);
        String jsonResString = mapper.writeValueAsString(jsonRes);
        
        System.out.println("ChooseCommonBackgroundColorServlet.doPost: jsonResString: " + jsonResString);
          
        resOut.write(jsonResString);
		
		System.out.println("ChooseCommonBackgroundColorServlet.doGet: ended for request: "
				+ request.getRequestURL());
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		System.out.println("ChooseCommonBackgroundColorServlet.doPost: started ... just calls doGet ...");
		doGet(request, response);
		
	}
}

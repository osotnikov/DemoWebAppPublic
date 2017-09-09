package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import osotnikov.constants.OperationResult;
import osotnikov.demowebapp.services.country_info.CountryInfoLocal;
import osotnikov.web.json.JsonResponse;

public class CountryInfoServlet extends HttpServlet {
    
	//@EJB(beanName = "CountryInfoAdaptiveBean")
	//CountryInfoLocal countryInfo = null;
	
    public CountryInfoServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		System.out.println("CountryInfoServlet.doGet ... started");
		// RESPONSE
		
		JsonResponse jsonRes = new JsonResponse(OperationResult.SUCCESS);
		
        String allTheCountriesStr = "";
        try{
        	//allTheCountriesStr = countryInfo.getStringWithAllTheCountries();
        }catch(Exception e){
        	System.out.println("CountryInfoServlet.doGet: ERROR while getting the string with all the countries!");
        	e.printStackTrace();
        	jsonRes.setStatus(OperationResult.ERROR);
        }
        
        ObjectMapper mapper = new ObjectMapper();
        jsonRes.setData(allTheCountriesStr);
        
        String jsonInString = mapper.writeValueAsString(jsonRes);
        
        System.out.println("CountryInfoServlet.doGet, jsonInString: " + jsonInString);
        
        response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter resOut = response.getWriter();
        resOut.write(jsonInString);
		    
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		System.out.println("TimeServlet.doPost: started ... just calls doGet ...");
		doGet(request, response);
		
	}

}

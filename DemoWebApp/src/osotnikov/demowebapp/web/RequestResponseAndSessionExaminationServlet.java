package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.security.Principal;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import osotnikov.demowebapp.constants.SessionAttributeEnum;
import osotnikov.demowebapp.services.user_management.session.Pojo;
import osotnikov.web.utils.WebUtils;

public class RequestResponseAndSessionExaminationServlet extends HttpServlet {
	
    public RequestResponseAndSessionExaminationServlet() {
        super();
    }
    
    //@EJB
    //Pojo pojo = new Pojo();
    //@EJB(beanName="JdbcInsertExamplePojo")
    //JDBCInsertExamplePojo jdbcInsertExamplePojo = new JDBCInsertExamplePojo();
    @Inject
    WebUtils webUtils;
    
	protected void doGet(HttpServletRequest req, HttpServletResponse response) 
		throws ServletException, IOException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		System.out.println("RequestResponseAndSessionExaminationServlet.doGet: started for request: "
			+ request.getRequestURL());
		
		//pojo.func();
		//jdbcInsertExamplePojo.execute();
		
		String reqHeadersStr, reqParamsStr, reqAttsStr, reqCookies, sessAttsStr; 
		HttpSession session = request.getSession();
		
		// CREATE THE RESPONSE

		// SET HEADERS
		
		response.addHeader("testResponseHeader", "testResonseHeader_val");
		
		// CREATE RESPONSE
		
		// SET THE RESPONSE ENCODING
		response.setContentType("text/html"); // From the docs: Note that the character encoding cannot
		// be communicated via HTTP headers if the servlet does not specify a content type.
		response.setCharacterEncoding("UTF-8");
		
		// CREATE THE RESPONSE BODY
		
		PrintWriter resOut = response.getWriter(); // By default in ISO 8859-1 encoding.
		reqHeadersStr = webUtils.getStringWithAllTheRequestHeaders(request, "<br/><br/>");
		reqParamsStr = webUtils.getStringWithAllTheRequestParameters(request, "<br/><br/>");
		reqAttsStr = webUtils.getStringWithAllTheRequestAttributes(request, "<br/><br/>");
		reqCookies = webUtils.getStringWithAllTheRequestCookies(request, "<br/><br/>");
		sessAttsStr = webUtils.getStringWithAllTheSessionAttributes(request.getSession(), "<br/><br/>");
		String commonBgColor = (String)session.getAttribute(SessionAttributeEnum.COMMON_BG_COLOR_SESSION_ATT.getName());
		resOut.write(
		"<html>" +
			"<head>" +
				"<style>" + 
					"body{" +
						"background-color: " + ((commonBgColor==null)?"white":commonBgColor) +
					"}"+
				"</style>" +
				"<title>RequestResponseAndSessionExaminationServlet - javaEE_presentation</title>" +
			"</head>" +
			"<body>" +
				"<h2>RECEIVED REQUEST</h2>" +
	 
	            "<br/>REMOTE ADDRESS: " + request.getRemoteAddr() + "<br/>" +
	            "<br/>REMOTE PORT: "+request.getRemotePort() + "<br/>" +
	            "<br/>REMOTE HOST: "+request.getRemoteHost() + "<br/>" +
	            "<br/>SERVER NAME: "+request.getServerName() + "<br/>" +
	            "<br/>CONTEXT PATH: "+request.getContextPath() + "<br/>" +
	            ((request.getContextPath() != null)?"DECODED CONTEXT PATH: "+
	            URLDecoder.decode(request.getContextPath(),"UTF-8"):"") + "<br/>" +
	            "SERVLET PATH: "+request.getServletPath() + "<br/>" +
	            "PATH INFO: " + request.getPathInfo() + "<br/>" +
	            ((request.getPathInfo() != null)?"DECODED PATH INFO: "
	            + URLDecoder.decode(request.getPathInfo(),"UTF-8"):"") + "<br/>" +
	            "QUERY STRING: " + request.getQueryString() + "<br/>" +
	            ((request.getQueryString() != null)?"DECODED QUERY STRING: "+
	            URLDecoder.decode(request.getQueryString(),"UTF-8"):"") + "<br/>" +
	            "TRANSLATED PATH: " + request.getPathTranslated() + "<br/>" + 
	        	"SCHEME: "+request.getScheme() + "<br/>" + 
	        	"REQUEST URI: "+request.getRequestURI() + "<br/>" + 
	        	"DECODED REQUEST URI: "+URLDecoder.decode(request.getRequestURI(),"UTF-8") + "<br/>" + 
	        	"REQUEST URL: "+request.getRequestURL() + "<br/>" +
	        	"DECODED REQUEST URL: "+URLDecoder.decode(request.getRequestURL().toString(),"UTF-8") + "<br/>" +
	        	"AUTHENTICATION TYPE: "+request.getAuthType() + "<br/>" +
	        	"REMOTE USER: "+request.getRemoteUser() + "<br/>" +
	        	"USER PRINCIPAL: "+request.getUserPrincipal() + "<br/>" +
	        	((request.getUserPrincipal() != null)?"USER PRINCIPAL NAME: "+request.getUserPrincipal().getName() : "") + "<br/>" +
		        "LOCAL ADDRESS: "+request.getLocalAddr() + "<br/>" +
		        "LOCAL PORT: "+request.getLocalPort() + "<br/>" +
		        "LOCAL NAME: "+request.getLocalName() + "<br/>" + 
		        "PROTOCOL USED: "+request.getProtocol() + "<br/>" +
		        "IS SECURE: "+request.isSecure() + "<br/>" +
		        "METHOD: "+request.getMethod() + "<br/>" +
		        "LOCALE: "+request.getLocale() + "<br/>" +
		        "REQUESTED SESSION ID: "+request.getRequestedSessionId() + "<br/>" +
		        ((request.getSession() != null)?"SESSION ID: "+request.getSession().getId():"") + "<br/>" +
		        "CONTENT TYPE: "+request.getContentType() + "<br/>" +
		        "CONTENT LENGTH: "+request.getContentLength() + "<br/>" +
		        "CHARACTER ENCODING: "+request.getCharacterEncoding() + "<br/>" +
			
				"<h2>REQUEST HEADERS</h2>" + 
			    "<p>" + 
			    	reqHeadersStr + 
			    "</p>" + 
			    "<h2>REQUEST PARAMETERS</h2>" +
			    "<p>" + 
			    	reqParamsStr +
			    "</p>"+ 
			    "<h2>REQUEST ATTRIBUTES</h2>" +
			    "<p>" + 
			    	reqAttsStr +
			    "</p>"+ 
			    "<h2>COOKIES THAT CAME WITH THE REQUEST</h2>" +
			    "<p>" + 
			    	reqCookies +
			    "</p>"+ 
			    "<h2>SESSION INFO</h2>" +
			    "<p>" + 
				    "isNew: " + session.isNew() + "<br/>" +
				    "sessionId: " + session.getId() + "<br/>" +
					"creationTime: " + new java.util.Date(session.getCreationTime()) + "<br/>" +
					"lastAccessedTime: " + new java.util.Date(session.getLastAccessedTime()) + "<br/>" +
					"maxInactiveInterval: " + session.getMaxInactiveInterval() + "<br/>" +
			    "</p>"+
			    "<h2>SESSION ATTRIBUTES</h2>" +
			    "<p>" + 
			    	sessAttsStr +
			    "</p>" +
			    "<h2>RESPONSE INFO (before the commit)</h2>" +
			    "<p>" + 
				    "STATUS: "+response.getStatus() + "<br/>" +
					"BUFFER SIZE: "+response.getBufferSize() + "<br/>" +
					"CONTENT TYPE: "+response.getContentType() + "<br/>" +
					"CHARACTER ENCODING: "+response.getCharacterEncoding() + "<br/>" +
					"LOCALE: "+response.getLocale() + "<br/>" +
					"IS COMMITED: "+response.isCommitted() + "<br/>" +
			    "</p>" 
			    );
		
		// Include example with context root as point of reference with a request dispatcher from the ServletContext. 
		resOut.write("<h2>INCLUDE EXAMPLE</h2>");
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/includes/body/include_example.jsp");
		rd.include(request, response);
		// Include example with context root as point of reference. Getting the RequestDispatcher from the request
		// is not enough to make the request relative to the servlet path. It also has to 
		// start with no forward slash at the beginning otherwise it's relative to the context root just like the
		// request dispatcher from ServletContext.
		rd = request.getServletContext().getRequestDispatcher("/WEB-INF/includes/body/include_example.txt");
		rd.include(request, response);
		// Include example with current servlet's previous path element ("inits") as point of reference.
		rd = request.getRequestDispatcher("../WEB-INF/includes/body/include_example.jsp");
		rd.include(request, response);
		
		resOut.write(
			    "<h2>SUBMIT REQUEST PARAMETERS THROUGH A POST REQUEST</h2>" +
			    "<div>"+
				    "<form method=\"POST\" action=\"" + request.getContextPath() + "/actions/RequestResponseAndSessionExaminationServlet?param2=param2_val\">"+
					    "<label for=\"param1\">param1: </label>"+
					    "<input type=\"text\" id=\"param1\" name=\"param1\" style=\"border: 1px solid black\" /><br/><br/>"+
					    "<input type=\"submit\" value=\"Submit\"/>" +
				    "</form>"+
				"</div>"+
				"<h2>Test for UTF-8</h2>"+
			    "<p>"+
					"Το έγγραφο είναι κωδικοποιημένο σε UTF-8 και ο browser client έχει ενημερωθεί σχετικά με την κωδικοποίηση."+
				"</p>"+
			"</body>" +
		"</html>");
		
		// Flush/commit the response.
		response.flushBuffer();
		    
		System.out.println("RequestResponseAndSessionExaminationServlet.doGet: ended for request: "
			+ request.getRequestURL());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		System.out.println("RequestResponseAndSessionExaminationServlet.doPost: started ... just calls doGet ...");
		doGet(request, response);
		
	}

}

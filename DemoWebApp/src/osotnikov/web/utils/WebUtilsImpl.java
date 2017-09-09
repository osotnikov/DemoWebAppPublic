package osotnikov.web.utils;

import java.io.IOException;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WebUtilsImpl implements WebUtils {
	
	/* (non-Javadoc)
	 * @see osotnikov.web.utils.WebUtils#getStringWithAllTheRequestHeaders(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public String getStringWithAllTheRequestHeaders(HttpServletRequest request, String delimiter){
		
		String result = "";
		
		Enumeration<String> headerNames = request.getHeaderNames();
		String headerName, headerValue;
		
		while(headerNames.hasMoreElements()){
			headerName = headerNames.nextElement();
			headerValue = request.getHeader(headerName);
			result += headerName + " : " + headerValue + delimiter;
			
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see osotnikov.web.utils.WebUtils#getStringWithAllTheRequestParameters(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public String getStringWithAllTheRequestParameters(HttpServletRequest request, String delimiter){
		
		String result = "";
		
		Enumeration<String> parameterNames = request.getParameterNames();
		String parameterName, parameterValue;
		
		while(parameterNames.hasMoreElements()){
			parameterName = parameterNames.nextElement();
			parameterValue = request.getParameter(parameterName);
			result += parameterName + " : " + parameterValue + delimiter;
			
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see osotnikov.web.utils.WebUtils#getStringWithAllTheResponseHeaders(javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	@Override
	public String getStringWithAllTheResponseHeaders(HttpServletResponse response, String delimiter){
		
		String result = "";
		
		Collection<String> headerNames = response.getHeaderNames();
		String headerValue;
		
		for(String headerName : headerNames){
			headerValue = response.getHeader(headerName);
			result += headerName + ", contained in response: " + response.containsHeader(headerName)
			+ ", headerValue: " + headerValue + delimiter;
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see osotnikov.web.utils.WebUtils#getStringWithAllTheRequestCookies(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public String getStringWithAllTheRequestCookies(HttpServletRequest request, String delimiter){
		
		String result = "";
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null){
			for(Cookie cookie : cookies){
				result += "domain: " + cookie.getDomain() + ", name: "+ cookie.getName()+ 
				  ", value: " + cookie.getValue() + ", is HttpOnly: " + cookie.isHttpOnly()
				  + ", maxAge: "+ cookie.getMaxAge() + ", path: " + cookie.getPath() + 
				  ", is secure: " + cookie.getSecure() + delimiter;
				
			}
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see osotnikov.web.utils.WebUtils#getStringWithAllTheSessionAttributes(javax.servlet.http.HttpSession, java.lang.String)
	 */
	@Override
	public String getStringWithAllTheSessionAttributes(HttpSession session, String delimiter){
		
		String result = "";
		
		Enumeration<String> attributeNames = session.getAttributeNames();
		String attName;
		Object attValue;
		
		while(attributeNames.hasMoreElements()){
			attName = attributeNames.nextElement();
			attValue = session.getAttribute(attName);
			result += attName + " : " + attValue + delimiter;
			
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see osotnikov.web.utils.WebUtils#getStringWithAllTheRequestAttributes(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public String getStringWithAllTheRequestAttributes(HttpServletRequest request, String delimiter){
		
		String result = "";
		
		Enumeration<String> attributeNames = request.getAttributeNames();
		String attName;
		Object attValue;
		
		while(attributeNames.hasMoreElements()){
			attName = attributeNames.nextElement();
			attValue = request.getAttribute(attName);
			result += attName + " : " + attValue + delimiter;
			
		}
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see osotnikov.web.utils.WebUtils#hostAvailabilityCheck(java.lang.String, int)
	 */
	@Override
	public boolean hostAvailabilityCheck(String serverAddr, int port){ 
		
	    Socket s = null;;
	    boolean available = false; 
	    
		try {
			s = new Socket(serverAddr, port);
			if (s.isConnected()){ 
				available = true; 
	        	s.close();    
	        } 
		} catch (java.net.UnknownHostException e1) {
			available = false;
	        s = null;
			e1.printStackTrace();
		} catch (IOException e1) {
			available = false;
	        s = null;
			e1.printStackTrace();
		}
		
	    return available;   
	} 
	
}

package osotnikov.demowebapp.web.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import osotnikov.web.utils.WebUtils;

//@WebFilter(urlPatterns={"/*"})
public class AuditingFilter2 implements Filter{

	@Inject
	private WebUtils webUtils;
	
	boolean enabled = false;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("AuditingFilter2.init was called.");
		
		enabled = Boolean.valueOf(filterConfig.getInitParameter("enabled"));
		System.out.println("AuditingFilter2.init enabled: " + enabled);
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		
		if(!enabled){
			filterChain.doFilter(req, res);
			return;
		}
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		System.out.println("\nAuditingFilter2.doFilter started, for request url: " + request.getRequestURL() + "\n");
		
        HttpServletResponse response = (HttpServletResponse) res;
		
        // AuditingFilter1 handles the logging of the request.
        
		filterChain.doFilter(request, response);
		
		System.out.println("\nRESPONSE\n");
		System.out.println("STATUS: "+response.getStatus());
		System.out.println("BUFFER SIZE: "+response.getBufferSize());
		System.out.println("CONTENT TYPE: "+response.getContentType());
		System.out.println("CHARACTER ENCODING: "+response.getCharacterEncoding());
		System.out.println("LOCALE: "+response.getLocale());
		System.out.println("IS COMMITED: "+response.isCommitted());
		
		System.out.println("\nRESPONSE HEADERS\n");
		
		String respHeaders = webUtils.getStringWithAllTheResponseHeaders(response, "\n");
		System.out.println(respHeaders);
		
		System.out.println("\nAuditingFilter2.doFilter ended, for request url: " + request.getRequestURL() + "\n");
	}

	@Override
	public void destroy() {
		System.out.println("AuditingFilter2.destroy was called.");
	}
	
}

package osotnikov.demowebapp.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import osotnikov.web.utils.WebUtils;

//@WebFilter(urlPatterns={"/*"})
public class AuditingFilter1 implements Filter{
	
	@Inject
	private WebUtils webUtils;
	
	boolean enabled = false;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		System.out.println("AuditingFilter1.init was called.");
		
		enabled = Boolean.valueOf(filterConfig.getInitParameter("enabled"));
		System.out.println("AuditingFilter1.init enabled: " + enabled);
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		
		if(!enabled){
			filterChain.doFilter(req, res);
			return;
		}
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		System.out.println("\nAuditingFilter1.doFilter started, for request url: " + request.getRequestURL() + "\n");
		
        HttpServletResponse response = (HttpServletResponse) res;
		
        System.out.println("\nRECEIVED REQUEST\n");
        // The address and port of the remote machine that made this request or the last proxy (may
        // also be some gateway).
        System.out.println("REMOTE ADDRESS: "+request.getRemoteAddr());
        System.out.println("REMOTE PORT: "+request.getRemotePort());
        // May return the address again and not translate it...
        System.out.println("REMOTE HOST: "+request.getRemoteHost());
        // Same as the host I guess. It's supposed to favor the Host header value of the request and
        // not the DNS resoluted value...
        System.out.println("SERVER NAME: "+request.getServerName());
        // The context path from the runtime descriptor...
        System.out.println("CONTEXT PATH: "+request.getContextPath());
        if(request.getContextPath() != null){
        	System.out.println("DECODED CONTEXT PATH: "+URLDecoder.decode(request.getContextPath(),"UTF-8"));
        }
        // The part of the request URL that matches the longest possible url-pattern of the
        // Servlet that will service this request.
        System.out.println("SERVLET PATH: "+request.getServletPath());
        System.out.println("PATH INFO: "+request.getPathInfo());
        if(request.getPathInfo() != null){
        	System.out.println("DECODED PATH INFO: "+URLDecoder.decode(request.getPathInfo(),"UTF-8"));
        }
        System.out.println("QUERY STRING: "+request.getQueryString());
        if(request.getQueryString() != null){
        	System.out.println("DECODED QUERY STRING: "+URLDecoder.decode(request.getQueryString(),"UTF-8"));
        }
        // Will return the path of the resource on the server's filesystem. Basically it appends the path
        // info to the expanded directory of the application on the server's filesystem (found by experimenting,
        // java documentation and the servlet specification do not state that clearly). Therefore if the
        // application is archived it will return null.
        System.out.println("TRANSLATED PATH: "+request.getPathTranslated());
        System.out.println("SCHEME: "+request.getScheme());
        // The request URI as inside the first line of the HTTP request. In the majority of times it will
        // be a relative uri but there is a chance that if there is an inbound proxy present that it will
        // be an absolute uri. The javadocs say that it will contain everything, from the protocol (scheme)
        // up to but not including the query string, but if the uri was relative obviously there will be no 
        // scheme or host & port information (which is the majority of the time).
        System.out.println("REQUEST URI: "+request.getRequestURI());
        System.out.println("DECODED REQUEST URI: "+URLDecoder.decode(request.getRequestURI(),"UTF-8"));
        // The full request uri minus the query string.
        System.out.println("REQUEST URL: "+request.getRequestURL());
        System.out.println("DECODED REQUEST URL: "+URLDecoder.decode(request.getRequestURL().toString(),"UTF-8"));
        // Authentication type that is used to protect the target Servlet (maybe generally a resource). 
        // Types are {BASIC, DIGEST, FORM, CLIENT CERTIFICATE}. We'll always use FORM. If none is used
        // the this method returns null.
        System.out.println("AUTHENTICATION TYPE: "+request.getAuthType());
        // With some authentication schemes the remote user may be the user's username only after
        // he is authenticated for the first time and after that it may just be returning null. I
        // guess this applies to the BASIC authentication scheme (must investigate). As far as the 
        // FORM authentication scheme is concerned it will return the user's username as long as he's
        // logged in (just like the getName() of the user's principal). Just always get the user's 
        // username by the getName of the user's principal.
        System.out.println("REMOTE USER: "+request.getRemoteUser());
        Principal princ = request.getUserPrincipal();
        System.out.println("USER PRINCIPAL: "+princ);
        if(princ != null){
        	// Always use this: 
        	System.out.println("USER PRINCIPAL NAME: "+request.getUserPrincipal().getName());
        }
        // The address and port of the system that received this request.
        System.out.println("LOCAL ADDRESS: "+request.getLocalAddr());
        System.out.println("LOCAL PORT: "+request.getLocalPort());
        System.out.println("LOCAL NAME: "+request.getLocalName()); // The host name of the address if it had been resolved.
        System.out.println("PROTOCOL USED: "+request.getProtocol());
        System.out.println("IS SECURE: "+request.isSecure());// Is a secure protocol (like https) used?
        System.out.println("METHOD: "+request.getMethod());// e.g. GET, POST etc.
        System.out.println("LOCALE: "+request.getLocale());// "Accept" header stuff...
        // If the requested session id does not exist in the server then maybe the next if will not
        // enter inside the case... All other times these two will be the same...
        System.out.println("REQUESTED SESSION ID: "+request.getRequestedSessionId());
        if(request.getSession() != null){
        	System.out.println("SESSION ID: "+request.getSession().getId());
        }
        System.out.println("CONTENT TYPE: "+request.getContentType());
        System.out.println("CONTENT LENGTH: "+request.getContentLength());
        System.out.println("CHARACTER ENCODING: "+request.getCharacterEncoding());

        System.out.println("\nREQUEST HEADERS\n");
		String reqHeadersStr = webUtils.getStringWithAllTheRequestHeaders(request, "\n");
		System.out.println(reqHeadersStr);
		System.out.println("\nREQUEST PARAMETERS\n");
		String reqParamsStr = webUtils.getStringWithAllTheRequestParameters(request, "\n");
		System.out.println(reqParamsStr);
		System.out.println("\nREQUEST ATTRIBUTES\n");
		String reqAttsStr = webUtils.getStringWithAllTheRequestAttributes(request, "\n");
		System.out.println(reqAttsStr);
		
		System.out.println("\nREQUEST COOKIES\n");
		String reqCookies = webUtils.getStringWithAllTheRequestCookies(request, "\n");
		System.out.println(reqCookies);
		
		System.out.println("SESSION INFO\n");
		HttpSession session = request.getSession();
		System.out.println("isNew: " + session.isNew());
		System.out.println("sessionId: " + session.getId());
		System.out.println("creationTime: " + new java.util.Date(session.getCreationTime()));
		System.out.println("lastAccessedTime: " + new java.util.Date(session.getLastAccessedTime()));
		System.out.println("maxInactiveInterval: " + session.getMaxInactiveInterval());
		
		System.out.println("\nSESSION ATTRIBUTES\n");
		String sessAttsStr = webUtils.getStringWithAllTheSessionAttributes(request.getSession(), "\n");
		System.out.println(sessAttsStr);
		
		// AuditingFilter2 handles the logging of the response.
		filterChain.doFilter(request, response);
		
		System.out.println("\nAuditingFilter1.doFilter ended, for request url: " + request.getRequestURL() + "\n");
	}

	@Override
	public void destroy() {
		System.out.println("AuditingFilter1.destroy was called.");
	}
}

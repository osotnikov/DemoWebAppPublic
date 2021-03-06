package osotnikov.demowebapp.web.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class FilterForIncludes implements Filter{

	boolean enabled = false;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("FilterForIncludes.init was called.");
		
		enabled = Boolean.valueOf(filterConfig.getInitParameter("enabled"));
		System.out.println("FilterForIncludes.init enabled: " + enabled);
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		
		if(!enabled){
			filterChain.doFilter(req, res);
			return;
		}
		
		HttpServletRequest request = (HttpServletRequest)req;
		
		System.out.println("\nFilterForIncludes.doFilter started, for request url: " + request.getRequestURL() + "\n");
        filterChain.doFilter(req, res);
        System.out.println("\nFilterForIncludes.doFilter ended, for request url: " + request.getRequestURL() + "\n");
	}

	@Override
	public void destroy() {
		System.out.println("FilterForIncludes.destroy was called.");
	}
	
}

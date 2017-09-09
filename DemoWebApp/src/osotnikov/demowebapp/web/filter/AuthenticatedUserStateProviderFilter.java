package osotnikov.demowebapp.web.filter;

import java.io.IOException;
import java.security.Principal;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import osotnikov.demowebapp.constants.SessionAttributeEnum;
import osotnikov.demowebapp.services.user_management.session.UserManagementService;
import osotnikov.demowebapp.services.user_management.vo.User;
import osotnikov.utils.StringUtils;

public class AuthenticatedUserStateProviderFilter implements Filter{

	@EJB
	UserManagementService userManagementBean;
	
	@Inject
	StringUtils stringUtils;
	
	boolean enabled = false;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("AuthenticatedUserStateProviderFilter.init was called.");
		
		enabled = Boolean.valueOf(filterConfig.getInitParameter("enabled"));
		System.out.println("AuthenticatedUserStateProviderFilter.init enabled: " + enabled);
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		
		if(!enabled){
			filterChain.doFilter(req, res);
			return;
		}
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession sess = request.getSession();
		Principal userPrinc = request.getUserPrincipal();
		
		if(userPrinc != null 
			&& sess.getAttribute(SessionAttributeEnum.LOGGED_IN_USER.getName()) == null){

			User user = userManagementBean.fetchUser(userPrinc.getName());
			request.getSession().setAttribute(SessionAttributeEnum.LOGGED_IN_USER.getName(), user);
		}
		
		//System.out.println("AuthenticatedUserStateProviderFilter.doFilter started, for request url: " + request.getRequestURL() + "\n");
        filterChain.doFilter(req, res);
        //System.out.println("AuthenticatedUserStateProviderFilter.doFilter ended, for request url: " + request.getRequestURL() + "\n");
	}

	@Override
	public void destroy() {
		System.out.println("AuthenticatedUserStateProviderFilter.destroy was called.");
	}
	
}



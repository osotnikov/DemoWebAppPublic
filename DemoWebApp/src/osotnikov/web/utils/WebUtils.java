package osotnikov.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface WebUtils {

	String getStringWithAllTheRequestHeaders(HttpServletRequest request, String delimiter);

	String getStringWithAllTheRequestParameters(HttpServletRequest request, String delimiter);

	String getStringWithAllTheResponseHeaders(HttpServletResponse response, String delimiter);

	String getStringWithAllTheRequestCookies(HttpServletRequest request, String delimiter);

	String getStringWithAllTheSessionAttributes(HttpSession session, String delimiter);

	String getStringWithAllTheRequestAttributes(HttpServletRequest request, String delimiter);

	boolean hostAvailabilityCheck(String serverAddr, int port);

}
package osotnikov.demowebapp.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import osotnikov.utils.StringUtils;
import osotnikov.web.enums.TransferMethodEnum;


public class ForwardingAndRedirectionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
     
	@Inject
	StringUtils stringUtils;
	
    public ForwardingAndRedirectionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// PROCESS
		
		System.out.println("ForwardingAndRedirectionServlet.doGet: started for request: "
			+ request.getRequestURL());
		
		String unprocessedInput = stringUtils.getTrimmedOrEmpty(
			request.getParameter("unprocessed_msg"));
		String processedMsg = unprocessedInput.toUpperCase();
		// Processing result:
		request.setAttribute("processed_msg", processedMsg);
		
		// transferringMode parameter:
		String transferMethod = request.getParameter("transferMethod");
		
		if(TransferMethodEnum.FORWARD.getName().equals(transferMethod)){
			
			getServletContext().getRequestDispatcher( 
				"/actions/RequestResponseAndSessionExaminationServlet").forward(request, response);
			
		}else if(TransferMethodEnum.INTERNAL_REDIRECT.getName().equals(transferMethod)){
			
			response.sendRedirect(request.getContextPath() + 
				"/actions/RequestResponseAndSessionExaminationServlet?processed_msg=" + processedMsg+"&param2=param2_val");
			
		}else if(TransferMethodEnum.EXTERNAL_REDIRECT.getName().equals(transferMethod)){
			
			response.sendRedirect("http://www.youtube.com?processed_msg=" + processedMsg);
			
		}else{
			
			throw new RuntimeException("ERROR: NO TRANSFER MODE WAS PROVIDED !!!");
			
		}
		
		System.out.println("ForwardingAndRedirectionServlet.doGet: ended for request: "
			+ request.getRequestURL());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

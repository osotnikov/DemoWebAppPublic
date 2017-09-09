package osotnikov.demowebapp.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import osotnikov.constants.OperationResult;
import osotnikov.demowebapp.services.fibonacci.entity.FibCompHistory;
import osotnikov.demowebapp.services.jdbc.JdbcDaoServiceSingleton;
import osotnikov.web.json.JsonResponse;

public class JdbcDemoServlet extends HttpServlet{
	 
	// JDBC driver name and database URL
    static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";

    //  Database credentials
    static final String USER = "WEB_DEMO";
    static final String PASS = "WEB_DEMO";
    
    //@EJB(beanName="JdbcDaoServiceSingleton")
    //JdbcDaoServiceSingleton jdbcDao;
    
	public JdbcDemoServlet() {
	    super();
	}
	    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		System.out.println("JdbcDemoServlet.doGet ... started." );

		// CREATE THE HTTP RESPONSE BODY
        
		Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    Statement statement = null;
	    ResultSet rs = null;
	    try{
	       	
	       // ==> LOG THE ACCESS ATTEMPT TO THIS SERVLET
	    	
	       // STEP 1: Register JDBC driver.
	       // Class.forName("oracle.jdbc.OracleDriver").newInstance();
	       // Since Java 4 it is not anymore necessary to register JDBC drivers manually.
	       // Any JDBC 4.0 drivers that are found in your class path at runtime are 
	       // automatically loaded by the JVM.
	       // Before Java 4 the registration of the driver with the DriverManager used
	       // to happen inside the static initializer block of the Driver class.
	    	 
	       // STEP 2: Open a connection.
	       conn = DriverManager.getConnection(DB_URL, USER, PASS);
	      
	       // STEP 3: Create the sql statement.
	       String sql = "INSERT INTO JDBC_DEMO_ACCESS_INFO(ACCESS_TIMESTAMP, REMOTE_HOST, "
	       	+ "REMOTE_PORT, SESSION_ID) VALUES (?, ?, ?, ?)";
	       
	       preparedStatement = conn.prepareStatement(sql);
	       preparedStatement.setTimestamp(1,new Timestamp(new java.util.Date().getTime()));
	       preparedStatement.setString(2, request.getRemoteHost());
	       preparedStatement.setInt(3, request.getRemotePort());
	       preparedStatement.setString(4, request.getSession().getId());
	       
	       // STEP 4: Execute the statement.
	       int recordsInserted = preparedStatement.executeUpdate();
	       
	       System.out.println("Inserted records into the table: " + recordsInserted);
	       
	       // conn.commit(); // This is not necessary as by default the connection object is in
	       // auto commit mode, which means that each statement gets committed i.e. is executed 
	       // as an individual transaction.
	       
	       // ==> CREATE AND RETURN THE HTML REPORT OF THE LAST ACCESS ATTEMPTS TO THIS PAGE
	       
	       // Set response content type
	       response.setContentType("text/html");
	       PrintWriter out = response.getWriter();
	       String title = "JDBC Demo Servlet";
	       String docType =
	         "<!doctype html public \"-//w3c//dtd html 4.0 " +
	          "transitional//en\">\n";
	          out.println(docType +
	          "<html>\n" +
	          "<head><title>" + title + "</title></head>\n" +
	          "<body bgcolor=\"#f0f0f0\">\n" +
	          "<h1 align=\"center\">" + title + "</h1>\n" +
	          "<h2 align=\"left\">Link to Homepage</h1>\n" +
	          "<a href=\"" + request.getContextPath() + "\">Home</a>" +
	          "<h2 align=\"left\">Access Logs</h1>\n");

           // Execute SQL query
	       statement = conn.createStatement();
           sql = "SELECT ACCESS_TIMESTAMP, REMOTE_HOST, REMOTE_PORT, SESSION_ID FROM JDBC_DEMO_ACCESS_INFO " + 
	       "ORDER BY ACCESS_TIMESTAMP DESC";
           rs = statement.executeQuery(sql);
           
           String accessTimestamp, remoteHost, remotePort, sessionId;
           
           // Extract data from result set
           while(rs.next()){
             //Retrieve by column name
        	 accessTimestamp  = rs.getString("ACCESS_TIMESTAMP");
        	 remoteHost = rs.getString("REMOTE_HOST");
        	 remotePort = rs.getString("REMOTE_PORT");
        	 sessionId = rs.getString("SESSION_ID");
        	 
             //Display values
        	 out.println("<br/>");
             out.println("Access Timestamp: " + accessTimestamp + "<br/>");
             out.println("Remote Host: " + remoteHost + "<br/>");
             out.println("Remote Port: " + remotePort + "<br/>");
             out.println("Session ID: " + sessionId + "<br/>");
           }
           out.println("</body></html>");
	       

	    }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	    }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	    }finally{
	      //finally block used to close resources
	      try{
	         if(preparedStatement != null){
	        	 preparedStatement.close();
	         }
	         if(statement != null){
	        	 statement.close();
	         }
	         if(rs != null){
	        	 rs.close();
	         }
	         if(conn != null){
		        conn.close();
		     }
	      }catch(SQLException se){
	    	  System.out.println("ERROR: Could not close a JDBC resource! Possible memory leak!");
	      }
	      
	      /*FibCompHistory fibCompHist = new FibCompHistory();
	      try {
	    	  jdbcDao.storeFibCompHistoryWithJDBC(fibCompHist);
		  } catch (Exception e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }*/
	    }
        
        System.out.println("JdbcDemoServlet.doGet: ended for request: "
			+ request.getRequestURL());
		    
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		System.out.println("JdbcDemoServlet.doPost: started ... just calls doGet ...");
		doGet(request, response);
		
	}
}

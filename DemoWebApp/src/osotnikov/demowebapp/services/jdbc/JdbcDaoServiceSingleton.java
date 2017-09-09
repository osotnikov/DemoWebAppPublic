package osotnikov.demowebapp.services.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import osotnikov.demowebapp.services.fibonacci.entity.FibCompHistory;

//@Singleton(name="JdbcDaoServiceSingleton")
//@LocalBean
public class JdbcDaoServiceSingleton {
	
	@Resource(lookup="WEB_DEMO") 
	DataSource webDemo;
	
	public void submitUpdateStatmet(String upStmt){
		
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED) 
	public int storeFibCompHistoryWithJDBC(FibCompHistory fibCompHis) throws Exception{
		
		Connection conn = null;
	    PreparedStatement preparedStatement = null;
	    
	    try{
	       	
	       conn = webDemo.getConnection();
	       conn.setAutoCommit(false);
	       // STEP 3: Create the sql statement.
	       String sql = "Insert into WEB_DEMO.FIB_COMP_HISTORY (ID, COMP_END_TIME, COMP_START_TIME, COMP_STEP, "
	       		+ "JMS_MSG_ID, RECORD_CREATION_TIME, RESULT, TERM_IND, USERNAME, BILL_ID) Values("
	       		+ "?,?,?,?,?,?,?,?,?,?)";
	       
	       preparedStatement = conn.prepareStatement(sql);
	       preparedStatement.setLong(1,new Date().getTime());
	       preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
	       preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
	       preparedStatement.setString(4, "a");
	       preparedStatement.setString(5, "b");
	       preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
	       preparedStatement.setInt(7, 2);
	       preparedStatement.setInt(8, 3);
	       preparedStatement.setString(9, "oleg@mailinator.com");
	       preparedStatement.setLong(10, 101);
	       
	       // STEP 4: Execute the statement.
	       int recordsInserted = preparedStatement.executeUpdate();
	       
	       if(true){throw new Exception("will it rollback?");}
	       System.out.println("Inserted records into the table: " + recordsInserted);
		
	       return recordsInserted;
	       
	    }catch(SQLException se){
	    	//Handle errors for JDBC
	        //se.printStackTrace();
	        throw new RuntimeException(se);
	    }finally{
	    	//finally block used to close resources
	    	try{
	    		if(preparedStatement != null){
	    			preparedStatement.close();
	    		}
		        if(conn != null){
			       conn.close();
			    }
		    }catch(SQLException se){
		    	//System.out.println("ERROR: Could not close a JDBC resource! Possible memory leak!");
		    	throw new RuntimeException("ERROR: Could not close a JDBC resource! Possible memory leak!");
		    }
	    	
	    	
	    }
	    
	    
	}
}

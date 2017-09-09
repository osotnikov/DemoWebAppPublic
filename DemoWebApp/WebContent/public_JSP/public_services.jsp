<%@page import="java.security.Principal"%>
<%@page import="osotnikov.demowebapp.constants.WEB_DEMO_CONSTANTS" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="http_encoding" scope="page" value="<%=WEB_DEMO_CONSTANTS.HTTP_CHARACTER_ENCODING%>"/>
<!-- You can actually reference http_encoding in EL like this: ${http_encoding} but you cannot use
this in the pageEncoding attribute of the next page directive. Eclipse will refuse to save the file
if you do so... -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="osotnikov.demowebapp.services.fibonacci.vo.ComputeFibonacciResponse" %>
<%@ page import = "javax.naming.InitialContext" %>
<%@ page import="osotnikov.demowebapp.constants.SessionAttributeEnum"%>
<%@ page import = "osotnikov.demowebapp.web.constants.LifecycleScopeAndConcurrencyExperimentType" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                       "http://www.w3.org/TR/html4/loose.dtd">
  
<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/auth.css" />
        
        <%@ include file = "/WEB-INF/includes/head/common-style.html" %>
        
        <title>Publicly Accessible Services</title>
        
        <%@ include file="/WEB-INF/includes/head/jquery.txt" %>
         
        <!-- see https://github.com/douglascrockford/JSON-js -->
        <script src="<%=request.getContextPath() %>/js/json2.js" type="text/javascript"></script>
        
        <script type="text/javascript">
        
        	<%@ include file = "/WEB-INF/includes/head/common-scripts.js" %>
        
	        $(function(){
	        	
	            // COUNT BUTTON PRESS
	            
				$("#countButtonPress").click(function(){
	            	
	            	console.log('#countButtonPress button pressed!');
	            	
	                $.ajax({
	                	/* Path parameters that are part of a GET request (as defined by HTTP 1.1) are not
						   exposed by the HttpServletRequest APIs. They must be parsed from the String values 
						   returned by the getRequestURI method or the getPathInfo method.
	                	*/
	                    url: "<%=request.getContextPath() %>/actions/CountButtonPressServlet" + 
	                    "/path_παράμετρος_0/path_παράμετρος_1?query_παράμετρος_0",
	                    type: "GET",
	                    cache: false,
	                    //data: undefined,
	                    dataType: "json",
	                         
	                    success: function (data, textStatus, jqXHR){
	                    	
	                        console.log('countButtonPress response: ' + JSON.stringify(data));
	                        
	                        if (data.status == "SUCCESS" ){
	                        
	                        	console.log('SUCCESS, count: ' + data.data);
		                        $("#actionOutputContent").html('The current button press count (in the queried servlet\'s lifetime) is: ' +
		                        	data.data);
	                        
	                        }else{
	                        	$("#actionOutputContent").html('There was a problem with your request! Please ' + 
                    			'try again later.');
	                        }
	                        
	                    },
	                         
	                    error: function (jqXHR, textStatus, errorThrown){
	                    	$("#actionOutputContent").html('There was a problem with your request! Please ' + 
                			'try again later.');
	                    },
	                         
	                    complete: function(jqXHR, textStatus){
	                        //alert("complete");
	                    }                    
	                });
	            }); 
	            
				// LIFECYCLE, SCOPE AND CONCURRENCY EXPERIMENTS
	            
				$(".concExpsButton").click(function(){
	            	
					var concExpButID = this.id;
					
	            	console.log('A .concExpsButton button pressed with the id: ' + concExpButID);
	            	
	                $.ajax({
	                    url: "<%=request.getContextPath() %>/actions/LifecycleScopeAndConcurrencyServlet",
	                    type: "GET",
	                    cache: false,
	                    data: {"experimentType" : concExpButID},
	                    dataType: "json", // The type of the data that you are expecting back from the server.
	                         
	                    success: function (data, textStatus, jqXHR){
	                    	
	                        console.log(this.id + ' response: ' + JSON.stringify(data));
	                        
	                        if (data.status == "SUCCESS" ){
	                        
	                        	console.log('SUCCESS, report: ' + data.data);
		                        $("#" + concExpButID + "+#actionOutputContent").html(
		                        	data.data);
	                        
	                        }else{
	                        	$("#" + concExpButID + "+#actionOutputContent").html(
	                        		'There was a problem with your request! Please ' + 
                    			'try again later.');
	                        }
	                        
	                    },
	                         
	                    error: function (jqXHR, textStatus, errorThrown){
	                    	$("#" + concExpButID + "+#actionOutputContent").html(
	                    		'There was a problem with your request! Please ' + 
                			'try again later.');
	                    },
	                         
	                    complete: function(jqXHR, textStatus){
	                        //alert("complete");
	                    }                    
	                });
	            }); 
	            
	         });
        </script>
         
  </head>
  
  <body>
  
    <h1>Publicly Available Services</h1>
    
    <%@ include file = "/WEB-INF/includes/body/bg-color-selector.html" %>
    
    <!-- WEB_DEMO_CONSTANTS.HTTP_CHARACTER_ENCODING: ${http_encoding} -->
    
    <h3>JDBC Demo</h3>
    <p>Transfer to the JDBC Demo Page. This page is produced by a Servlet that logs all the
    access attempts to itself in the DB table "JDBC_DEMO_ACCESS_INFO". Its code is an example
    of insert and select operations implemented with JDBC.</p>
    <a href="<%=request.getContextPath() %>/actions/JdbcDemoServlet">JDBC Demo</a>
    
    <h3>Get button press count:</h3>
    <p>This is an example of calling a stateful bean.</p>
    <button id="countButtonPress">
    	Count Button Press
    </button>
     
    <h3>Output:</h3>
    <div id="actionOutputContent"> </div>
    
    <h3>Lifecycle, Scope and Concurrency Experiments</h3>
    <p>These examples have the goal of showing the behaviour of stateless and stateful beans,
     as well as POJOs that have been injected through CDI.<br/>Try pushing the buttons repeatedly
     to test concurrent execution. The request will result in a long running process in the back 
     end (around 3 seconds).</p>
    
    <div>
	   <h4>Stateless Bean with Dependent Scope with 10 second WRITE Lock</h4>
	   
	   <button class="concExpsButton" 
	   	id="<%=LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE_WRITE_LOCK_10_SEC.getName() %>">
	   	
	   	Execute
	   </button>
	   
	   <div id="actionOutputContent">
    	
       </div>
    </div>
    <div>
	   <h4>Stateless Bean with Dependent Scope with 10 Second READ Lock</h4>
	   
	   <button class="concExpsButton" 
	   	id="<%=LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE_READ_LOCK_10_SEC.getName()%>">
	   	
	   	Execute
	   </button>
	   
	   <div id="actionOutputContent">
    	
       </div>
    </div>
    <div>
	   <h4>Stateless Bean with Dependent Scope with Instant WRITE Lock</h4>
	   
	   <button class="concExpsButton" 
	   	id="<%=LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE_WRITE_LOCK_INSTANT.getName()%>">
	   	
	   	Execute
	   </button>
	   
	   <div id="actionOutputContent">
    	
       </div>
    </div>
    
    <div>
	   <h4>Stateless Bean with Dependent Scope Instant READ Lock</h4>
	   
	   <button class="concExpsButton" 
	   	id="<%=LifecycleScopeAndConcurrencyExperimentType.STATELESS_DEPENDENT_SCOPE.getName()%>">
	   	
	   	Execute
	   </button>
	   
	   <div id="actionOutputContent">
    	
       </div>
    </div>
    
    <div>
	   <h4>First Stateful Bean with Dependent Scope</h4>
	   
	   <button class="concExpsButton" 
	   	id="<%=LifecycleScopeAndConcurrencyExperimentType.STATEFUL_DEPENDENT_SCOPE_1.getName()%>">
	   	
	   	Execute
	   </button>
	   
	   <div id="actionOutputContent">
    	
       </div>
    </div>
    
    <div>
	   <h4>Second Stateful Bean with Dependent Scope</h4>
	   
	   <button class="concExpsButton" 
	   	id="<%=LifecycleScopeAndConcurrencyExperimentType.STATEFUL_DEPENDENT_SCOPE_2.getName()%>">
	   	
	   	Execute
	   </button>
	   
	   <div id="actionOutputContent">
    	
       </div>
    </div>
    
    <div>
	   <h4>Stateful Bean Class Injected with CDI with Request Scope (Managed Bean with Request Scope)</h4>
	   
	   <button class="concExpsButton" 
	   	id="<%=LifecycleScopeAndConcurrencyExperimentType.STATEFUL_REQ_SCOPE.getName()%>">
	   	
	   	Execute
	   </button>
	   
	   <div id="actionOutputContent">
    	
       </div>
    </div>
    
    <div>
	   <h4>First POJO with Dependent Scope</h4>
	   
	   <button class="concExpsButton" 
	   	id="<%=LifecycleScopeAndConcurrencyExperimentType.POJO_DEPENDENT_SCOPE_1.getName()%>">
	   	
	   	Execute
	   </button>
	   
	   <div id="actionOutputContent">
    	
       </div>
    </div>
    
    <div>
	   <h4>Second POJO with Dependent Scope</h4>
	   
	   <button class="concExpsButton" 
	   	id="<%=LifecycleScopeAndConcurrencyExperimentType.POJO_DEPENDENT_SCOPE_2.getName()%>">
	   	
	   	Execute
	   </button>
	   
	   <div id="actionOutputContent">
    	
       </div>
    </div>
    
    <div>
	   <h4>POJO with Request Scope</h4>
	   
	   <button class="concExpsButton" 
	   	id="<%=LifecycleScopeAndConcurrencyExperimentType.POJO_REQ_SCOPE.getName()%>">
	   	
	   	Execute
	   </button>
	   
	   <div id="actionOutputContent">
    	
       </div>
    </div>

    
  </body>
  
</html>
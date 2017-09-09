<%@page import="osotnikov.web.enums.TransferMethodEnum"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%-- <%@ page import="osotnikov.utils.DateUtils"%>
<%@ page import="osotnikov.utils.DateUtilsImpl"%> --%>
<%@ page import="osotnikov.web.enums.TransferMethodEnum"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page import="javax.inject.Inject"%>
<%@page import="osotnikov.demowebapp.services.fibonacci.vo.ComputeFibonacciResponse" %>
<%@page import="osotnikov.async.services.AsyncCompStore" %>
<%@page import="osotnikov.demowebapp.services.fibonacci.vo.ComputeFibonacciRequest" %>
<%@ page import = "javax.naming.InitialContext" %>
<%@ page import="osotnikov.demowebapp.constants.SessionAttributeEnum"%>
<%@ page import="osotnikov.async.services.enums.ComputationState"%>


<%! 
//@Inject // Will not work in JSP as it only works in some implementations (but not in WLS).
//DateUtils dateUtils;
%>

<%!
//This will not work inside a JSP. Either use MVC or InitialContext...
//@javax.ejb.EJB(beanName="FibonacciComputationsStoreSingleton")

AsyncCompStore<Integer, ComputeFibonacciRequest, ComputeFibonacciResponse> fibCompsStore;

{
	InitialContext ic = null;
	try{
		ic = new InitialContext();
		fibCompsStore = (AsyncCompStore<Integer, ComputeFibonacciRequest, ComputeFibonacciResponse>)
			ic.lookup("java:module/FibonacciComputationsStoreSingleton");
	}catch(Exception e){
		e.printStackTrace();
	}
		
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                       "http://www.w3.org/TR/html4/loose.dtd">
  
<html>
  <head>
  
  	<title>Privately Accessible Services</title>
  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- 
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/auth.css" /> -->
    
    <%@ include file = "/WEB-INF/includes/head/common-style.html" %>
    
    <%@ include file="/WEB-INF/includes/head/jquery.txt" %>
    
    <script src="<%=request.getContextPath() %>/js/json2.js" type="text/javascript"></script>
    
    <script type="text/javascript">
    
    	<%@ include file = "/WEB-INF/includes/head/common-scripts.js" %>
    
    	// COMPUTE FIBONACCI

        $(function(){
            "use strict";
             
            $(document.forms['remoteSenderForm']).submit(function(event){
                var data = {
                    textMessage: {
                    	subject: this.remoteSysMsgSubj.value,
                    	body: $('#remoteSenderForm #remoteSysMsgBody').val()
                    }
                }; 
                
                console.log('remoteSenderForm: data:'+JSON.stringify(data));
                var jsonData = JSON.stringify(data);
                
                var destinationUrl = this.action; // The action attribute of the registerForm form.
                 
                $.ajax({
                    url: destinationUrl,
                    type: "POST",
                    data: {"jsonData" : jsonData},
                    cache: false,
                    dataType: "json",
                     
                    success: function (data, textStatus, jqXHR){
                        //alert("success");
                        if (data.status == "SUCCESS" ){
                           //redirect to secured page
                           //window.location.replace("https://"+window.location.host+"<%=request.getContextPath() %>/secure/authenticated/ProfileJsp");
                           $('.remoteSender .action-report').text('Your message has been sent.');
                        }else{
                        	$('.remoteSender .action-report').text('Failed to send the message!');
                        }
                    },
                     
                    error: function (jqXHR, textStatus, errorThrown){
                        alert("error - HTTP STATUS: "+jqXHR.status);
                         
                    },
                     
                    complete: function(jqXHR, textStatus){
                        //alert("complete");
                        //i.e. hide loading spinner
                    },
                     
                    statusCode: {
                        404: function() {
                          alert("page not found");
                        },
                    }
                     
                });
                 
                //event.preventDefault();
                return false;
            });
            
            $(document.forms['remoteSysInfoForm']).submit(function(event){
 
                var destinationUrl = this.action; // The action attribute of the registerForm form.
                 
                $.ajax({
                    url: destinationUrl,
                    type: "POST",
                    cache: false,
                    dataType: "json",
                     
                    success: function (data, textStatus, jqXHR){
                        //alert("success");
                        if (data.status == "SUCCESS" ){
                           $('.remoteSysInfo .action-report').text(data.data);
                        }else{
                        	$('.remoteSysInfo .action-report').text('Some kind of error occurred!');
                        }
                    },
                     
                    error: function (jqXHR, textStatus, errorThrown){
                        alert("error - HTTP STATUS: "+jqXHR.status);
                         
                    },
                     
                    complete: function(jqXHR, textStatus){
                        //alert("complete");
                        //i.e. hide loading spinner
                    },
                     
                    statusCode: {
                        404: function() {
                          alert("page not found");
                        },
                    }
                     
                });
                 
                //event.preventDefault();
                return false;
            });
            
            $("#computeFibonacci").click(function(){
            	
            	var termInd = $('#computeFibonacciInput').val();
            	console.log('termInd: ' + termInd);
            	
                $.ajax({
                    url: "<%=request.getContextPath() %>/secure/authenticated/actions/ComputeFibonacciServlet",
                    type: "GET",
                    cache: false,
                    data: {'termInd' : termInd},
                    dataType: "json",
                         
                    success: function (data, textStatus, jqXHR){
                    	
                        console.log('computeFibonacci response: ' + JSON.stringify(data));
                        
                        if (data.status == "SUCCESS" ){
                        	
                        	console.log('SUCCESS, data.data.status: ' + data.data.status);
                        	
                        	if(data.data.compState === 'UNDER_PROCESSING'){
                        		
                        		$('#computeFibonacci').attr("disabled",true);
                        		
                        		$("#computeFibonacciOutput").html('Your request is being processed, please ' + 
                        		 'visit this page at a later instant to view the result of your computation.');
                        		
                        	}else if(data.data.compState === 'FAILED'){
                        		
                        		$("#computeFibonacciOutput").html('There was a problem with your request! Please ' + 
                        			'try again later.');
                        		
                        	}else if(data.data.compState === 'COMPUTED'){
                        		
                        		$("#computeFibonacciOutput").html('The result of your requested fibonacci computation with argument ' + 
                        			data.data.termInd + ' is ' + data.data.result +'.');
                        	}
                            
                        }else{
                        	$("#computeFibonacciOutput").html('There was a problem with your request! Please ' + 
                			'try again later.');
                        }
                    },
                         
                    error: function (jqXHR, textStatus, errorThrown){
                    	$("#computeFibonacciOutput").html('There was a problem with your request! Please ' + 
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
	 
	<h1>Welcome to your privately accessible services!</h1>

    <br/>
   
    <%@ include file = "/WEB-INF/includes/body/bg-color-selector.html" %>
     
    <%-- LINK TO THE PROFILE PAGE --%>
    
    <h3>
    <a href="<%=request.getContextPath() %>/secure/authenticated/ProfileJsp" >
    	Go to the Profile Page (secured, requires authentication)</a>
    </h3>
    
    <%-- REQUEST ASYNCHRONOUS FIBONACCI COMPUTATION --%>
    
    <h3>Compute n-th fibonacci number (term index):</h3>
    <p>This is an example of calling a message driven bean as well as an example of global transaction management.</p>
    <input id="computeFibonacciInput" type="text"/>
    <%
       InitialContext ic = new InitialContext();
       ComputeFibonacciRequest cReq = (ComputeFibonacciRequest)
       	   session.getAttribute(SessionAttributeEnum.LAST_FIB_COMP_REQ.getName());
       
       ComputeFibonacciResponse fibComp = null;
       if(cReq != null && cReq.getKey() != null){
    	   
    	  fibComp = fibCompsStore.requestComputation(cReq);   
       }   
	   
    %>
    
    <button id="computeFibonacci"  
    <%=(fibComp != null && ComputationState.UNDER_PROCESSING.equals(fibComp.getCompState()))
    	?"disabled=\"true\"":"" %>> <%-- If the user has requested a computation that is
    	still being processed he should not be able to request another one until it is finished. --%>
    	
    	Compute Fibonacci
    </button> 
    
    <h3>Output:</h3>
    <div id="computeFibonacciOutput">
    	<%--  --%>
    	   
   	 <% if(fibComp != null){ 
   	
    	  if(ComputationState.COMPUTED.equals(fibComp.getCompState())){%> 
    			
    		<%="The result of your requested fibonacci computation with argument " + 
    		  fibComp.getTermInd() + 
    		  " is " + fibComp.getResult() + "." %>
    		  
	       <% }else if(ComputationState.UNDER_PROCESSING.equals(fibComp.getCompState())){ %>
	    			
	   			  Your request is being processed, please visit this page at a later instant to 
	   			  view the result of your computation.
		                        		 
		   <% }else if(ComputationState.FAILED.equals(fibComp.getCompState())){ %>
	    	
	    		  There was a problem with your request! Please try again later.
	    	
	    	<% } %>
    	
    <% } %>
    </div>
    <br/><br/>
    
    <%-- SEND MESSAGE TO REMOTE SYSTEM FORM --%>
      
    <div class="remoteSender">
      <form id="remoteSenderForm" name="remoteSenderForm" 
      	action="<%=request.getContextPath() %>/secure/user/actions/DemoWsMsgSenderServlet" method="POST">
      	
        <fieldset>
          <legend>Send Message to Remote System</legend>
              
          <div>
            <label for="remoteSysMsgSubj">Subject</label> 
            <input type="text" id="remoteSysMsgSubj" name="remoteSysMsgSubj"/>
          </div>
          <div>
            <label>Message</label> 
            <textarea rows="4" cols="70" id="remoteSysMsgBody"></textarea>
          </div>
              
          <div class="buttonRow">
            <input type="submit" value="Submit Message" />
          </div>
            
        </fieldset>
      </form> 
      <br/><br/>
      <div class="action-report"> </div>

    </div>
    
    <br/><br/>
    <div class="remoteSysInfo">
      <form id="remoteSysInfoForm" name="remoteSysInfoForm" 
      	action="<%=request.getContextPath() %>/secure/user/actions/DemoWsRemoteSysInfoServlet" method="POST">
      	
        <fieldset>
          <legend>Get the information about the runtime environment of the remote system (demoWebAppWs).</legend>
       
          <div class="buttonRow">
            <input type="submit" value="Receive Info" />
          </div>
            
        </fieldset>
      </form> 
      
      <div class="action-report"> </div>

    </div>

  </body>
    
</html>
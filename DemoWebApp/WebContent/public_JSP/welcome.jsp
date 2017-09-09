<%@page import="osotnikov.web.enums.TransferMethodEnum"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%-- <%@ page import="osotnikov.utils.DateUtils"%> <%@ page import="osotnikov.utils.DateUtilsImpl"%> --%>
<%@ page import="osotnikov.web.enums.TransferMethodEnum"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ page import="javax.inject.Inject"%>
<%@ page import="osotnikov.demowebapp.constants.SessionAttributeEnum"%>

<%! 
//@Inject // Will not work in JSP as it only works in some implementations (but not in WLS).
//DateUtils dateUtils;%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  
<%! 
  private int getCurrentHour(){
	  java.util.Calendar rightNow = java.util.Calendar.getInstance();
	  int hour = rightNow.get(java.util.Calendar.HOUR_OF_DAY);
	  return hour;
  } %>
  
<html>
  <head>
  
  	<title>Welcome Page</title>
  
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- 
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/auth.css" /> -->
    
    <%@ include file = "/WEB-INF/includes/head/common-style.html" %>
    
    <%@ include file="/WEB-INF/includes/head/jquery.txt" %>
    
    <script src="<%=request.getContextPath() %>/js/json2.js" type="text/javascript"></script>
    
    <script type="text/javascript">
    
    	<%@ include file = "/WEB-INF/includes/head/common-scripts.js" %>
    
        $(function(){
            "use strict";
             
            $(document.forms['registerForm']).submit(function(event){
                var data = {
                    firstName: this.fname.value,
                    lastName: this.lname.value,
                    email: this.email.value,
                    password: this.password1.value,
                    confirmPasswordValue: this.password2.value
                }; 
                
                console.log('registerForm: data:'+JSON.stringify(data));
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
                           //window.location.replace("https://"+window.location.host +
                           //"<%=request.getContextPath() %>/secure/authenticated/ProfileJsp");
                           $('.register .action-report').text('Registration successful!');
                        }else{
                        	$('.register .action-report').text('Registration failed!');
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
             
            $(document.forms['loginForm']).submit(function(event){
                 
                var data = {
                    email: this.email.value,
                    password: this.password.value
                }; 
                var destinationUrl = this.action;
                     
                $.ajax({
                    url: destinationUrl,
                    type: "POST",
                    data: data,
                    contentType: "application/x-www-form-urlencoded", // This is the default content type.
                    cache: false,
                    dataType: "json",
                         
                    success: function (data, textStatus, jqXHR){
                        //alert("success");
                        if (data.status == "SUCCESS" ){
                            //redirect to secured page
                            window.location.replace(
                              "https://"+window.location.host+
                              "<%=request.getContextPath() %>/secure/authenticated/ProfileJsp");
                        }else{
                        	$('.login .action-report').text('Login failed!');
                        }
                    },
                         
                    error: function (jqXHR, textStatus, errorThrown){
                        alert("error - HTTP STATUS: "+jqXHR.status);
                    },
                         
                    complete: function(jqXHR, textStatus){
                        //alert("complete");
                    }                    
                });
                 
                //event.preventDefault();
                return false;
            });
            
        });
        
        
    </script>
     
  </head>
  
  <body>
  
    <%--
	<c:if test="${pageContext.request.userPrincipal != null}">
		<c:set var = "loggedInUser" scope = "page" value = 
		"${sessionScope[<%=SessionAttributeEnum.LOGGED_IN_USER.getName()>]}"/>
	    <h1>Welcome to our secured Web Application DemoWebApp, ${loggedInUser.firstName}!</h1>
	</c:if>
	<c:if test="${pageContext.request.userPrincipal == null}">
		<h1>Welcome to our secured Web Application DemoWebApp, Guest!</h1>
	</c:if>
	 --%>
	 
	<h1>Welcome to our secured Web Application, DemoWebApp!</h1>

    <br/>
    <p> 
    	
    	Current Date: <%=new java.text.SimpleDateFormat("dd/M/yyyy").format(new java.util.Date()) %>
    </p>
    
    <% if(getCurrentHour() >= 18){%>  
        <div>
    	  Good Evening!
    	</div> 
    <%}else{ %>
      	<div>
    	  Hello!
    	</div>
    <% } %>
    
    <%@ include file = "/WEB-INF/includes/body/bg-color-selector.html" %>
    
    <%-- REQUEST/RESPONSE/SESSION EXAMINATION SERVLET LINK --%>
    <br/><br/>
    <a href="<%=request.getContextPath()%>/actions/RequestResponseAndSessionExaminationServlet" >
    	Go to the Request Response & Session Examination Page</a>
    <br/><br/><br/>
    
    <%-- FORWARDING AND REDIRECTION EXAMPLE REQUEST FORM --%>
    
    <div class="forwardingAndRedirectionExample">
      <form id="forwardingAndRedirectionExampleForm" name="forwardingAndRedirectionExampleForm" 
      	action="<%=request.getContextPath()%>/actions/ForwardingAndRedirectionServlet" method="POST">
      	
        <fieldset>
          <legend>Forwarding and Redirection Example</legend>
              
          <div>
            <label for="unprocessed_msg">Message for intermediate processing:</label> 
            <input type="text" id="unprocessed_msg" name="unprocessed_msg" 
            	value="This is a sample message."/>
          </div>    
        
              
          <div>
            <label for="transferMethodRadioForward">Transfer to the request, response & session 
            	examination page by forwarding:</label> 
            <input type="radio" id="transferMethodRadioForward" name="transferMethod" 
            	value="<%=TransferMethodEnum.FORWARD.getName()%>" checked="checked"/>
          </div>
          <div>
            <label for="transferMethodRadioRedirectInternal">
            	Transfer to the request, response & session examination page by internal redirection:</label> 
            <input type="radio" id="transferMethodRadioRedirectInternal" name="transferMethod"
            	value="<%=TransferMethodEnum.INTERNAL_REDIRECT.getName()%>" />
          </div>
          <div>
          	<label for="transferMethodRadioRedirectExternal">
          		Transfer to youtube by external redirection:</label> 
            <input type="radio" id="transferMethodRadioRedirectExternal" name="transferMethod"
            	value="<%=TransferMethodEnum.EXTERNAL_REDIRECT.getName()%>" />
          </div>
          
          <div class="buttonRow">
            <input type="submit" value="Submit" />
          </div>
            
        </fieldset>
      </form> 

    </div>
      
    <%-- EXAMPLES OF INCLUDE --%>
    <div>
    	<h2>Examples of the Include Action</h2>
    	<p>
    		<jsp:include page="/WEB-INF/includes/body/include_example.jsp"/>
    		<jsp:include page="../WEB-INF/includes/body/include_example.txt"/>
    	</p>
    	
    	<h2>Examples of the Include Directive</h2>
    	<p>
    		<%@ include file = "/WEB-INF/includes/body/include_example.jsp" %>
    		<%@ include file = "../WEB-INF/includes/body/include_example.txt" %>
    	</p>
    	
    	
    </div>
      
    <%-- LINK TO THE PROFILE PAGE --%>
      
    <br/><br/>
    <a href="<%=request.getContextPath() %>/secure/authenticated/ProfileJsp" >
    	Go to the Profile Page (secured, requires authentication)</a>
    <br/><br/><br/>
    
    <%-- REGISTRATION FORM --%>
      
    <div class="register">
      <form id="registerForm" name="registerForm" 
      	action="<%=request.getContextPath() %>/secure/user/actions/RegistrationServlet" method="POST">
      	
        <fieldset>
          <legend>Registration</legend>
              
          <div>
            <label for="fname">First Name</label> 
            <input type="text" id="fname" name="fname"/>
          </div>
          <div>
            <label for="lname">Last Name</label> 
            <input type="text" id="lname" name="lname"/>
          </div>
              
          <div>
            <label for="email">Email</label> 
            <input type="text" id="email" name="email"/>
          </div>
          <div>
            <label for="password1">Password</label> 
            <input type="password" id="password1" name="password1"/>
          </div>
          <div>
            <label for="password2">Password (repeat)</label> 
            <input type="password" id="password2" name="password2"/>
          </div>
              
          <div class="buttonRow">
            <input type="submit" value="Register" />
          </div>
            
        </fieldset>
      </form> 
      
      <div class="action-report"> </div>

    </div>
       
    <br/><br/><br/>
    
    <%-- LOGIN FORM --%>
     
    <div class="login">
      <form id="loginForm" name="loginForm" 
      	action="<%=request.getContextPath()%>/secure/user/actions/LoginServlet" method="POST">
      	
        <fieldset>
          <legend>Login</legend>
              
          <div>
            <label for="email">Email</label> 
            <input type="text" id="email" name="email"/>
          </div>
          <div>
            <label for="password">Password</label> 
            <input type="password" id="password" name="password"/>
          </div>
              
          <div class="buttonRow">
            <input type="submit" value="Login" />
          </div>
            
        </fieldset>
      </form> 
      
      <div class="action-report"></div>
      
      <h3><a href="<%=request.getContextPath() %>/public_JSP/public_services.jsp">Public Services</a></h3>
      
    </div>
    
  </body>
    
</html>
<%@page import="java.security.Principal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="osotnikov.demowebapp.constants.SessionAttributeEnum"%>
<%@ page import="osotnikov.demowebapp.services.user_management.vo.User"%>
<%@ page import="osotnikov.demowebapp.services.user_management.session.UserManagementService"%>
<%@ page import="osotnikov.demowebapp.constants.SessionAttributeEnum"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                       "http://www.w3.org/TR/html4/loose.dtd">
  
<html>
  <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <%@ include file = "/WEB-INF/includes/head/common-style.html" %>
        
        <title>Secured JSP Page</title>
         
        <!-- see https://github.com/douglascrockford/JSON-js -->
        <script src="<%=request.getContextPath() %>/js/json2.js" type="text/javascript"></script>
         
        <%@ include file="/WEB-INF/includes/head/jquery.txt" %>
         
        <script type="text/javascript">
        
        	<%@ include file = "/WEB-INF/includes/head/common-scripts.js" %>
        
	        $(function(){
	            "use strict";
	            $('#logoutLink').click(function(){
	                 
	                var destinationUrl = this.href;
	                 
	                $.ajax({
	                    url: destinationUrl,
	                    type: "GET",
	                    cache: false,
	                    dataType: "json",
	                         
	                    success: function (data, textStatus, jqXHR){
	                        //alert("success");
	                        if (data.status == "SUCCESS" ){
	                            //redirect to welcome page
	                            window.location.replace("https://"+window.location.host+"<%=request.getContextPath() %>/public_JSP/welcome.jsp");
	                        }else{
	                            alert("failed");
	                        }
	                    },
	                         
	                    error: function (jqXHR, textStatus, errorThrown){
	                        alert("error - HTTP STATUS: "+jqXHR.status);
	                    },
	                         
	                    complete: function(jqXHR, textStatus){
	                        //alert("complete");
	                    }                    
	                });
	                 
	                return false;
	            });
	        });
	         
	        $(function(){
	           $("#getListOfCountries").click(function(){
	               $.ajax({
	                   url: "<%=request.getContextPath() %>/secure/authenticated/user/actions/CountryInfoServlet",
	                   type: "GET",
	                   cache: false,
	                   dataType: "json",
	                        
	                   success: function (data, textStatus, jqXHR){
	                       //alert("success");
	                       if (data.status == "SUCCESS" ){
	                           $("#actionOutputContent").html(data.data);
	                       }else{
	                           alert("failed");
	                       }
	                   },
	                        
	                   error: function (jqXHR, textStatus, errorThrown){
	                       //alert("error - HTTP STATUS: "+jqXHR.status);
	                       if (textStatus == "parsererror"){
	                           alert("You session has timed out");
	                           //forward to welcomde page
	                           window.location.replace("https://"+window.location.host+"<%=request.getContextPath() %>/welcome.jsp");
	                       }
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
    
    <a id="logoutLink" href="<%=request.getContextPath() %>/services/auth/logout" >logout</a>
    <br/><br/>
    
    <%@ include file = "/WEB-INF/includes/body/bg-color-selector.html" %>
    
    <%-- <button id="getListOfCountries">Get List of Countries</button>

    <br/><br/>
    <div id="actionOutputContent"></div>--%>
    
    <%
    Principal p = request.getUserPrincipal();
    User user = (User) session.getAttribute(SessionAttributeEnum.LOGGED_IN_USER.getName());

    if (p == null){
        //if you get here the something is really wrong, because
        //you can only see that page if you have been authenticated 
        //and therefore there is a principal available
        %>
        
        <h1>You are not logged in!</h1>
        <h2>Authentication Scheme Information</h2>
        Principal.getName() = NULL <br/>
    <%}else{ %>
    	<h1>You are logged in.</h1>
    	<h2>Personal Information</h2>
    	<p>
	    	First Name: <%=user.getFirstName()%> <br/>
	    	Last Name: <%=user.getLastName()%> <br/>
	    	Email: <%=user.getEmail()%> <br/>
	    	Registered On: <%=user.getRegisteredOn()%> <br/>
    	</p>
    	<h2>Authentication Scheme Information</h2>
        <p>
	        Principal.getName() = <%=p.getName()%> <br/>
	        request.getRemoteUser() = <%=request.getRemoteUser()%><br/>
	        request.getAuthType() = <%=request.getAuthType()%><br/>
	        request.isUserInRole(ADMINISTRATOR) = <%=request.isUserInRole("ADMINISTRATOR")%> <br/>
	        request.isUserInRole(USER) = <%=request.isUserInRole("USER")%> <br/>
	        request.isUserInRole(DEFAULT) = <%=request.isUserInRole("DEFAULT")%> <br/>
	        request.isUserInRole(CUSTOMER) = <%=request.isUserInRole("CUSTOMER")%> <br/>
	    </p>
    <% } %>
    
    <h3>
    	<a href="<%=request.getContextPath()%>/secure/authenticated/private_services.jsp">
    		Link to privately accessible services
    	</a>
   	</h3>
    
  </body>
</html>
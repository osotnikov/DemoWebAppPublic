<%@page import="java.security.Principal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="osotnikov.demowebapp.constants.SessionAttributeEnum"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
                       "http://www.w3.org/TR/html4/loose.dtd">
  
<html>

  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <%@ include file = "/WEB-INF/includes/head/common-style.html" %>
     
     <title>WEB-INF JSP Example</title>
         
     <!-- see https://github.com/douglascrockford/JSON-js ... This is a patch for IE8 basically. -->
     <script src="<%=request.getContextPath() %>/js/json2.js" type="text/javascript"></script>
         
     <%@ include file="/WEB-INF/includes/head/jquery.txt" %>
  </head>
  
  <body>
  
    <h1>You've reached the second (2) example of a JSP that is inside the WEB-INF folder.</h1>
    
  </body>
  
</html>
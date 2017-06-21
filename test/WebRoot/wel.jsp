<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'wel.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <%
  	String username = "赵四";
  	request.setAttribute("username", username);
   %>
  <body>
  <c:choose>
  	<c:when test="${username eq null }">
  		<div>
    		<a href="#">登录</a>
     		<a href="#">注册</a>
     </div>
  	</c:when>
  	<c:otherwise>
  		<c:if test="${username eq '赵四' }">
  			欢迎:贵宾：${username }
  		</c:if>
  		
  	</c:otherwise>
  </c:choose>
  
     
  </body>
</html>

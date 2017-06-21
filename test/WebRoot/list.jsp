<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>客户列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
<h3 align="center">客户列表</h3>
<table border="1" width="70%" align="center">
	<tr>
		<th>客户姓名</th>
		<th>性别</th>
		<th>生日</th>
		<th>手机</th>
		<th>邮箱</th>
		<th>描述</th>
		<th>操作</th>
	</tr>
	
<c:forEach items="${pb.beanList}" var="cstm">
	<tr>
		<td>${cstm.cname }</td>
		<td>${cstm.gender }</td>
		<td>${cstm.birthday }</td>
		<td>${cstm.cellphone }</td>
		<td>${cstm.email }</td>
		<td>${cstm.description }</td>
		<td>
			<a href="<c:url value='/CustomerServlet?method=preEdit&cid=${cstm.cid }'/>">编辑</a>
			<a href="<c:url value='/CustomerServlet?method=delete&cid=${cstm.cid }'/>">删除</a>
		</td>
	</tr>
</c:forEach>
</table>
<center>
	第${pb.pageCode }页/共${pb.totalPage }页
	<a href="<c:url value='/CustomerServlet?${pb.url }&pageCode=1'/>">首页</a>
	<c:choose>
		<c:when test="${pb.pageCode>1 }">
			<a href="<c:url value='/CustomerServlet?${pb.url }&pageCode=${pb.pageCode-1 }'/>">上一页</a>
		</c:when>
		<c:otherwise>
			上一页
		</c:otherwise>
	</c:choose>
	
	<%--
		1.考虑结果不超过10页
		2.当前页小于6，begin<=0
		3.当前页大于总页数-4,end>总页数
	 --%>
	<c:choose>
		<c:when test="${pb.totalPage<10 }">
			<c:set var="begin" value="1"/>
			<c:set var="end" value="${pb.totalPage }"/>
		</c:when>
		<c:otherwise>
			<c:set var="begin" value="${pb.pageCode-5 }"/>
			<c:set var="end" value="${pb.pageCode+4 }"/>
			<c:if test="${pb.pageCode<=5 }">
				<c:set var="begin" value="1"/>
				<c:set var="end" value="10"/>
			</c:if>
			<c:if test="${pb.pageCode>pb.totalPage-4 }">
				<c:set var="end" value="${pb.totalPage }"/>
				<c:set var="begin" value="${pb.totalPage-9 }"/>
			</c:if>
			
		</c:otherwise>
	</c:choose>
	
	
	
	<c:forEach var="i" begin="${begin }" end="${end }">
		
	 <a href="<c:url value='/CustomerServlet?${pb.url }&pageCode=${i }'/>">第${i }页</a>
	</c:forEach>
	
	<c:choose>
		<c:when test="${pb.pageCode<pb.totalPage }">
			<a href="<c:url value='/CustomerServlet?${pb.url }&pageCode=${pb.pageCode+1 }'/>">下一页</a>
		</c:when>
		<c:otherwise>
			下一页
		</c:otherwise>
	</c:choose>
	<a href="<c:url value='/CustomerServlet?${pb.url }&pageCode=${pb.totalPage }'/>">尾页</a>
	
	
</center>
  </body>
</html>

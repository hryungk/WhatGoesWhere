<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>
</head>
<body>
	<h1>Welcome to the Home Page!</h1>
	
	<!-- ${pageContext.request.contextPath } provides the relative path 
	to the app (in this case, "springmvcbasic") -->
	<a href="${pageContext.request.contextPath }/">Index</a> 
</body>
</html>
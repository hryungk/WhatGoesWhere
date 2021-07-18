<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/seattlespringmvc.css">
<title>Index Page</title>
</head>
<body>
	<h1>Welcome to the Index Page!</h1>
	<form action="./search" method="POST">
		<div>
			<label>Enter Employee number</label>
			<input type="text" name="employeeNumber" />			
		</div>
		<div>
			<label>Date</label>
			<input type="date" name="dateInput" />
		</div>
		<input type="submit" value="Search">
	</form>
	<p>Name: <span>${employee.firstName } ${employee.lastName }</span></p>
	<a href="home">Home</a>	
	<!-- Alternatively, ${pageContext.request.contextPath } provides the relative path 
	to the app (in this case, "springmvcbasic") -->
	<a href="${pageContext.request.contextPath }/home">Home</a> 
</body>
</html>
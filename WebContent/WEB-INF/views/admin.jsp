<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<!-- Bootstrap 4 -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
		integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
		crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	
	<link rel="stylesheet" type="text/css" href="css/stylesheet.css">
	<title>Admin Page</title>
</head>
<body>
	<!-- Header -->
	<jsp:include page="header.jsp" />

	<section id="container">		
        <h1>User List</h1>
        <div>
	        <table class="table table-striped table-hover">
	        	<thead>
	        		<tr>
	        			<th></th>
	        			<th>Username</th>
	        			<th>Email</th>
	        			<th>First Name</th>
	        			<th>Last name</th>
	        			<th>User Role</th>
	        			<th>Joined On</th>
	        		</tr>
	        	</thead>
	        	<tbody>
	        		<c:forEach var="credential" items="${credentials }" varStatus="status">	        			
			       		<tr>
			       			<td>
								<form action="./deleteUserById" method="POST" onsubmit="return(confirm('Are you sure you want to delete this user?'))">
									<input type="hidden" name="userId" value="${credential.user.id}">
									<button class="find-btn a-btn">Delete</button>
								</form>
							</td>
			       			<td><c:out value="${credential.username }" /></td>
			       			<td><c:out value="${credential.user.email }" /></td>
			       			<td><c:out value="${credential.user.firstName }" /></td>
			       			<td><c:out value="${credential.user.lastName }" /></td>
			       			<td><c:out value="${credential.userRole }" /></td>
			       			<td><c:out value="${credential.user.joinedDate }" /></td>
			       		</tr>
			   		</c:forEach>
	        	</tbody>        	
	        </table>   
        </div>  
    </section>	
	
	<!-- Footer -->
	<jsp:include page="footer.jsp" />
</body>
</html>
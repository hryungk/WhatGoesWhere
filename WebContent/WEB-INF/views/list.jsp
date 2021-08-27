<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String uri = request.getRequestURI();
	String pageName = uri.substring(uri.lastIndexOf("/")+1);
	pageName = pageName.substring(0, pageName.indexOf("."));
%>
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
	
	<!--  Data table -->	
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/jq-3.3.1/dt-1.11.0/datatables.min.css"/> 
	<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/jq-3.3.1/dt-1.11.0/datatables.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="css/stylesheet.css">
	<title>Item List</title>
</head>
<body>
	<!-- Header -->
	<jsp:include page="header.jsp" />

    <section> 
        <h1>Item List</h1>
        <c:set var="role" scope="page" value="${role }"/>
        <div>
        	<a href="addItem" class="add-btn a-reg-btn" id="a-btn">Add a new Item</a>
	        <table class="table table-striped table-hover" id="list-table">
	        	<thead>
	        		<tr id="thead-tr">
	        			<c:if test = "${role == '[ROLE_ADMIN]' }">
	        				<th></th>
	        				<th></th>
	        			</c:if>
	        			<th>Name</th>
	        			<th>Condition</th>
	        			<th>Best Option</th>
	        			<th>Special Instruction</th>
	        			<th>Notes</th>
	        			<th>Added On</th>
	        		</tr>
	        	</thead>
	        	<tbody>
	        		<c:forEach var="item" items="${items}" varStatus="status">
			       		<tr>
			       			<c:if test="${role == '[ROLE_ADMIN]' }">
			       				<td>
								<form action="./deleteItem" method="POST" onsubmit="return(confirm('Are you sure you want to delete the item?'))" name="myForm">
									<input type="hidden" name="itemId" value="${item.id}">
									<input type="hidden" name="pageName" value="<%=pageName%>"/>
									<button class="find-btn a-btn">Delete</button>
								</form>
								</td>
								<td>
									<form action="./editItem" method="get">
										<input type="hidden" name="itemId" value="${item.id}">
										<input type="hidden" name="pageName" value="<%=pageName%>"/>
										<button class="find-btn a-btn">Edit</button>
									</form>
								</td>
			       			</c:if>
			       			<td><c:out value="${item.name }" /></td>
			       			<td><c:out value="${item.condition }" /></td>
			       			<td><c:out value="${item.bestOption.value }" /></td>
			       			<td><c:out value="${item.specialInstruction }" /></td>
			       			<td><c:out value="${item.notes }" /></td>
			       			<td><c:out value="${item.addedDate }" /></td>
			       		</tr>
			   		</c:forEach>
	        	</tbody>        	
	        </table>   
        </div>     
    </section>    
    <script>
	    $(document).ready(function() {
	        $('#list-table').DataTable({
	        pageLength : 5,
	        lengthMenu: [5, 10, 20, -1]
	      });
	    } );
    </script>
     <!-- Footer -->
	<jsp:include page="footer.jsp" />
	
</body>
</html>
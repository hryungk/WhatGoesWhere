<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	
	<!--  Data table -->	
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs4/jq-3.3.1/dt-1.11.0/datatables.min.css"/> 
	<script type="text/javascript" src="https://cdn.datatables.net/v/bs4/jq-3.3.1/dt-1.11.0/datatables.min.js"></script>	
	
	<!-- Bootstrap CSS 5 
    https://getbootstrap.com/docs/5.0/getting-started/introduction/   -->
	<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script> -->
	
	<link rel="stylesheet" type="text/css" href="css/stylesheet.css">
	<title>What Goes Where in Redmond</title>
</head>
<body>
	<!-- Header -->
	<jsp:include page="header.jsp" /> 
	
 	<section> 
        <h2>Search any item to learn how to dispose of it properly</h2>
        <div>
        	<form action="./find" method="POST">
	            <input type=text placeholder="Search an item" id="find-inp" name="itemName" autofocus />
	            <input type="submit" value="Search" class="find-btn" id="find-btn"/>
            </form>
        </div>
        <div>
        	<a href="list">List of Items</a>
       	</div>
        <div style="margin: 2em;">
	        <table class="table table-striped table-hover" id="find-result-table">
	        	<tbody id="tbody">
			       	<c:forEach var="item" items="${items}" varStatus="status">
			       		<tr>
			       			<td><c:out value="${item.name }" /></td>
			       			<td><c:out value="${item.condition }" /></td>
			       			<td><c:out value="${item.bestOption }" /></td>
			       			<td><c:out value="${item.specialInstruction }" /></td>
			       			<td><c:out value="${item.notes }" /></td>
			       			<td><c:out value="${item.addedDate }" /></td>
			       		</tr>
			   		</c:forEach>
	        	</tbody>        	
	        </table>  
        </div>
    </section>
    
	<!-- Footer -->
	<jsp:include page="footer.jsp" />

    <script src="scripts/add_table_head.js" type="text/javascript"></script>
    <script>
	    $(document).ready(function() {
	        $('#find-result-table').DataTable({
	        pageLength : 5,
	        lengthMenu: [5, 10, 20, -1]
	      });
	    } );
    </script>
</body>
</html>
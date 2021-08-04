 <!-- Footer -->
	<jsp:include page="header.jsp" />	
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String userName = null;
	if (session.getAttribute("userName") == null) {
		response.sendRedirect("login");
	} else {
		userName = session.getAttribute("userName").toString();
	}
%>	

    <section> 
        <h1>My Profile</h1>
        <table id="table-profile">
        	<tr>
        		<th>Name:</th>
        		<td>${user.firstName } ${user.lastName }</td>
        	</tr>
        	<tr>
        		<th>Username:</th>
        		<td>${user.username }</td>
        	</tr>
        	<tr>
	        	<th>Email:</th>
	        	<td>${user.email }</td>        	
        	</tr>
        	<tr>
        		<th></th>        		
		        <td>        		 	
			        <a href="logout" class="a-reg-btn">Log out</a>
		        </td>		        
        	</tr>
        	<tr>
        		<th></th>
        		<td>        		 	
			        <a href="deleteuser">Delete Account</a>
		        </td>
        	</tr>
        	<tr>
        		<th>My Contribution:</th>
        	</tr>
        </table>
       
        
        <div style="height: 4em; position: relative; margin: 1em 0">
        	<a href="additem" class="reg-btn add-btn a-btn">Add a new Item</a>
        </div>
        <div>
	        <table class="table table-striped table-hover">
	        	<thead>
	        		<tr>
	        			<th></th>
	        			<th></th>
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
			       			<td>
								<form action="./deleteitem" method="POST" onsubmit="return(confirm('Are you sure you want to delete the item?'))" name="myForm">
									<input type="hidden" name="itemId" value="${item.id}">
									<button class="find-btn a-btn">Delete</button>
								</form>
							</td>
							<td>
								<form action="./edititem" method="get">
									<input type="hidden" name="itemId" value="${item.id}">
									<button class="find-btn a-btn">Edit</button>
								</form>
							</td>
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
	
	<!-- <script src="scripts/profile.js" type="text/javascript"></script> -->
	
     <!-- Footer -->
	<jsp:include page="footer.jsp" />
	
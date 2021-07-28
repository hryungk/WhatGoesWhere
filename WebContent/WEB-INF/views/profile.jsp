 <!-- Footer -->
	<jsp:include page="header.jsp" />	
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
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
        		<th>My Contribution:</th>
        	</tr>
        </table>
        
        <!-- <div style="height: 4em; position: relative; margin: 1em 0">
        	<a href="additem" class="reg-btn add-btn a-btn">Add a new Item</a>
        </div> -->
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
								<form action="./deleteitem?id=${item.id}" method="POST" onsubmit="return(confirm('Are you sure you want to delete the item?'))" name="myForm">
									<input type="hidden" name="id" value="${item.id}">
									<input type="hidden" name="username" value="${user.username }">
									<button class="find-btn a-btn">Delete</button>
								</form>
							</td>
							<td>
								<form action="./edititem?id=${item.id}" method="get">
									<input type="hidden" name="id" value="${item.id}">
									<input type="hidden" name="username" value="${user.username }">
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
   
    
	<script>
		let lastUl = document.getElementById('footer-support-ul');
		let newLi = document.createElement('li');
		newLi.innerHTML = '<form action="./deleteuser?email=${user.email }" method="get"><input type="hidden" name="email" value="${user.email }"><input type="submit" value="Delete Account" /></form>';		
		lastUl.appendChild(newLi);
	</script>
     <!-- Footer -->
	<jsp:include page="footer.jsp" />
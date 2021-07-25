 <!-- Footer -->
	<jsp:include page="header.jsp" />
    
    <section> 
        <h1>My Profile</h1>
        <table id="table-profile">
        	<tr>
        		<th>Name:</th>
        		<td>FirstName LastName</td>
        	</tr>
        	<tr>
	        	<th>Email:</th>
	        	<td>janedoe@email.com</td>        	
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
	        		<tr>
	        			<td><button class="find-btn a-btn">Delete</button></td>
	        			<td>
	        				<%-- <form action="edititem?id=${item.id}" method="get">
								<input type="hidden" name="id" value="${item.id}"> --%>
								<button class="find-btn a-btn">Edit</button>
							<!-- </form> -->
						</td>
	        			<td>Aerosole Can</td>
	        			<td>Empty</td>
	        			<td>Recycle</td>
	        			<td>Must be empty</td>
	        			<td>If not empty, it is considered hazardous material. Please either empty it or bring it to local drop-off location.</td>
	        			<td>07/21/2021</td>
	        		</tr>
	        		<tr>
	        			<td><button class="find-btn a-btn">Delete</button></td>
	        			<td>
	        				<form action="./edititem" method="get">
								<input type="hidden" name="id" value="">
								<button class="find-btn a-btn">Edit</button>
							</form> 
						</td>
	        			<td>Banana</td>
	        			<td></td>
	        			<td>Food & Yard Waste</td>
	        			<td></td>
	        			<td></td>
	        			<td>06/14/2021</td>
	        		</tr>
	        	</tbody>        	
	        </table>   
        </div>     
    </section>
    
     <!-- Footer -->
	<jsp:include page="footer.jsp" />
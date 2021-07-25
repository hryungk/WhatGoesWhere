 <!-- Footer -->
	<jsp:include page="header.jsp" />
    
    <section> 
        <h1>Item List</h1>
        <div style="height: 4em; position: relative; margin: 1em 0">
        	<a href="additem" class="reg-btn add-btn" id="a-btn">Add a new Item</a>
        </div>
        <div>
	        <table class="table table-striped table-hover">
	        	<thead>
	        		<tr>
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
	        			<td>Aerosole Can</td>
	        			<td>Empty</td>
	        			<td>Recycle</td>
	        			<td>Must be empty</td>
	        			<td>If not empty, it is considered hazardous material. Please either empty it or bring it to local drop-off location.</td>
	        			<td>07/21/2021</td>
	        		</tr>
	        		<tr>
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
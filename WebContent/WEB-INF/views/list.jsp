 <!-- Header -->
<jsp:include page="header.jsp" />
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <section> 
        <h1>Item List</h1>
        <div style="height: 4em; position: relative; margin: 1em 0">
        	<a href="additem" class="a-btn reg-btn add-btn" id="a-btn">Add a new Item</a>
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
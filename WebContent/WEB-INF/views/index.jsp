 <!-- Header -->
<jsp:include page="header.jsp" /> 
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 --%>	
    <section> 
        <h2>Search any item to learn how to dispose of it properly</h2>
        <div>
        	<form action="./find" method="POST">
	            <input type=text placeholder="Search an item" id="find-inp" name="itemName"/>
	            <input type="submit" value="Search" class="find-btn" id="find-btn"/>
            </form>
        </div>
        <a href="list">List of Items</a>
    </section>
    
    <section>
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
    	
    </section>
   <!-- Footer -->
	<jsp:include page="footer.jsp" />
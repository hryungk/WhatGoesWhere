 <!-- Header -->
<jsp:include page="header.jsp" /> 
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 --%>	
<%	
	String userName = (String) session.getAttribute("userName");
	if (userName != null) {
		userName  = session.getAttribute("userName").toString();
	}
%>	
	<h1>Welcome <a href="profile"><%= userName %>!</a> <a href="logout" class="a-reg-btn">Log out</a></h1>
	
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
    </section>
    <script src="scripts/profile.js"></script>
   <!-- Footer -->
	<jsp:include page="footer.jsp" />
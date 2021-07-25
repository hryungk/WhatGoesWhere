 <!-- Header -->
<jsp:include page="header.jsp" />
    
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
    
   <!-- Footer -->
<jsp:include page="footer.jsp" />
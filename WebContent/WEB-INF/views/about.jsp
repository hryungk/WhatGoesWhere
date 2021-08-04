 <!-- Header -->
	<jsp:include page="header.jsp" />
<%	
	String userName = (String) session.getAttribute("userName");
	if (userName != null) {
		userName  = session.getAttribute("userName").toString();
	} 
%>	

    <section id="container"> 
        <h1>About Us</h1>
        <div style="margin: 1em;">
            <p>Please join us growing knowledge of proper disposal of anything!</p>
        </div>
       	<a href="register" id="about-btn" class="reg-btn a-reg-btn">Register Today!</a>
    </section>
    <script>
	    let abtn = document.getElementById('about-btn');
		let name = '<%= userName%>';
    	if (name != 'null') {
    		abtn.innerHTML = 'Start Contributing!';
    		abtn.href = 'list';
    	}
    </script>
    
     <!-- Footer -->
	<jsp:include page="footer.jsp" />
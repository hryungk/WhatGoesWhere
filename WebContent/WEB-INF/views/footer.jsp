<%	
	String userName = (String) session.getAttribute("userName");
	if (userName != null) {
		userName  = session.getAttribute("userName").toString();
	}
%>	
    <footer>
		<div class="footer-nav">
			<p class="footer-nav-title">Site Map</p>
			<hr>
			<ul>
				<li><a href="${pageContext.request.contextPath }/">Home</a></li>
				<li><a href="list">Item List</a></li>
				<li><a href="about">About</a></li>
				<li><a href="register">Register</a></li>					
			</ul>
		</div>
		<div class="footer-nav">	
			<p class="footer-nav-title">You are saving the earth :)</p>
			<hr>			
			<p class="copyright">&copy; 2021. All rights reserved.</p>
		</div>
		<div class="footer-nav">
			<p class="footer-nav-title">Support</p>
			<hr>
			<ul id="footer-support-ul">
				<li><a href="contact">Contact Us</a></li>
			</ul>
		</div>
	</footer>	
	<script>
	    let footer_ul = document.getElementById('footer-support-ul');
		<%-- let username = '<%= userName%>'; --%>
    	if (username != 'null') {
    		let li_deleteuser = document.createElement('li');
    		let a_deleteuser = document.createElement('a');
    		a_deleteuser.innerHTML = 'Delete account';
    		a_deleteuser.href = 'deleteUser';
    		li_deleteuser.appendChild(a_deleteuser);
    		footer_ul.appendChild(li_deleteuser);
    	}
    </script>
</body>
</html>
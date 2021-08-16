<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.core.Authentication"%>	
<%
	boolean isAnonymous = true;
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication != null) {
		Object principal = authentication.getPrincipal();
		isAnonymous = principal.equals("anonymousUser");
	}
%>
<header class="menu_container">
	<div class="menu_item" id="menu_item-1">
		<h1 style="font-weight: bold;">
			<a href="${pageContext.request.contextPath }/">What Goes Where in Redmond?</a>
		</h1>
	</div>
	<div class="menu_item" id="menu_item-2">
		<nav>
			<ul>
				<li><a href="${pageContext.request.contextPath }/">Home</a></li>
				<li><a href="list">Item List</a></li>
				<li><a href="about">About</a></li>
				<li class="nav-item dropdown" id="nav-last-li">
					<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> Sign In/Register </a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" id="ddi-1" href="login">Sign In</a> 
						<a class="dropdown-item" id="ddi-2" href="register">Register</a>
					</div>
				</li>
			</ul>
		</nav>
	</div>
</header>

<script>	    
	let isanonymous = <%=isAnonymous%>;
	if (isanonymous == false) {
  		let aDropdown = document.getElementById('navbarDropdown');
  		aDropdown.innerHTML = 'Account';
  		let addi1 = document.getElementById('ddi-1');
  		addi1.href = 'profile';
  		addi1.innerHTML = 'Profile';
  		let addi2 = document.getElementById('ddi-2');
  		addi2.href = 'logout';
  		addi2.innerHTML = 'Sign Out';
  	}
</script>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.core.Authentication"%>
<%@ page import="org.springframework.security.core.GrantedAuthority"%>
<%@ page import="java.util.Collection"%>
	
<%! boolean isAdmin = false; %>	
<%
	boolean isAnonymous = true;
	/* boolean isAdmin = false; */
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication != null) {
		Object principal = authentication.getPrincipal();
		isAnonymous = principal.equals("anonymousUser");
		if (!isAnonymous) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			isAdmin = authorities.toString().equals("[ROLE_ADMIN]");
		}
	}
%>
<header class="menu_container">
	<%-- <div class="menu_item" id="menu_item-1">
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
					<div class="dropdown-menu" aria-labelledby="navbarDropdown" id="dropdown-menu">
						<a class="dropdown-item" id="ddi-1" href="login">Sign In</a> 
						<a class="dropdown-item" id="ddi-2" href="register">Register</a>
					</div>
				</li>
			</ul>
		</nav> 		
	</div> --%>
	<nav class="navbar navbar-expand-lg navbar-light " style="width: 100%;">          
       <a class="navbar-brand" id="nav-anchor-logo" href="${pageContext.request.contextPath }/">What Goes Where in Redmond?</a>            
       <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
         <span class="navbar-toggler-icon"></span>
       </button>
       <div class="collapse navbar-collapse" id="navbarSupportedContent">
         <ul class="navbar-nav ml-auto" style="margin-left: auto;">
           <li class="nav-item">
             <a class="nav-link" id="nav-anchor-1" href="${pageContext.request.contextPath }/">Home</a>
           </li>
           <li class="nav-item">
             <a class="nav-link" id="nav-anchor-2" href="about">About</a>
           </li>
           <li class="nav-item">
             <a class="nav-link" id="nav-anchor-3" href="list">Item List</a>
           </li>
           <li class="nav-item dropdown">
             <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
               Sign In/Register
             </a>
             <div class="dropdown-menu" aria-labelledby="navbarDropdown" id="dropdown-menu">
               <a class="dropdown-item" id="ddi-1" href="login">Sign In</a>
               <a class="dropdown-item" id="ddi-2" href="register">Register</a>
             </div>
           </li>
         </ul>
         <!--  <form class="d-flex">
           <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
           <button class="btn btn-outline-success" type="submit">Search</button>
         </form> -->
       </div>
   </nav> 
</header>

<script>	    
	let isanonymous = <%=isAnonymous%>;
	let isAdmin = <%=isAdmin%>;
	if (isanonymous == false) {
  		let aDropdown = document.getElementById('navbarDropdown');
  		aDropdown.innerHTML = 'Account';
  		let addi1 = document.getElementById('ddi-1');
  		addi1.href = 'profile';
  		addi1.innerHTML = 'Profile';
  		let addi2 = document.getElementById('ddi-2');
  		addi2.href = 'updatePassword';
  		addi2.innerHTML = 'Update Password';
  		
  		let dropdownMenu = document.getElementById('dropdown-menu');
  		
  		let divider = document.createElement('div');
  		divider.className = 'dropdown-divider';
  		dropdownMenu.appendChild(divider);  		
  		
  		let addi3 = document.createElement('a');
  		addi3.href = 'logout';
  		addi3.innerHTML = 'Sign Out';
  		addi3.id = 'ddi-3';
  		addi3.className = 'dropdown-item';
  		dropdownMenu.appendChild(addi3);		
  		
  		if (isAdmin == true) {
  			let addi4 = document.createElement('a');
  			addi4.href = 'admin';
  			addi4.innerHTML = 'Admin Page';
  			addi4.id = 'ddi-4';
  			addi4.className = 'dropdown-item';
  			
  			dropdownMenu.insertBefore(addi4, dropdownMenu.childNodes[4]);
  		}
  	}
</script>
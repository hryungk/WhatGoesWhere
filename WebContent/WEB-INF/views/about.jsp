<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication"%>	
<%	
	boolean isAnonymous = true;
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication != null) {
		Object principal = authentication.getPrincipal();
		isAnonymous = principal.equals("anonymousUser");
	}
%>	
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<!-- Bootstrap 4 -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
		integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
		crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	
	<link rel="stylesheet" type="text/css" href="css/stylesheet.css">
	<title>About Us</title>
</head>
<body>
	<!-- Header -->
	<jsp:include page="header.jsp" />
	
    <section id="container"> 
        <h1>About Us</h1>
        <div style="margin: 1em;">
            <p>Please join us growing knowledge of proper disposal of anything!</p>
        </div>
       	<a href="register" id="about-btn" class="reg-btn a-reg-btn">Register Today!</a>
    </section>
    <script>
	    let abtn = document.getElementById('about-btn');
    	if (isanonymous == false) {
    		abtn.innerHTML = 'Start Contributing!';
    		abtn.href = 'list';
    	}
    </script>
    
     <!-- Footer -->
	<jsp:include page="footer.jsp" />
</body>
</html>
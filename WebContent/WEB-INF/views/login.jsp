<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<title>Sign In</title>
</head>
<body>
	<!-- Header -->
	<jsp:include page="header.jsp" />

    <section> 
        <h1>Sign In</h1>
        <p style="color: red;">${SPRING_SECURITY_LAST_EXCEPTION.message}</p>
        <c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
        <div>
           <form action="./login" method="POST" name="myForm">
                <fieldset>
                    <legend>Please enter your credentials below:</legend>                    
            		<p style="color: red;" id="msg"><c:out value="${message }" /></p>
                    <div class="r-input">
						<input type="text" name="username" placeholder="Username" required="required" id="input-username" value="${username }" autofocus/>
					</div>
					<div class="r-input">				
						<input type="password" name="password" placeholder="Password" required="required" />
					</div>					
                </fieldset>
                <div>
	            	<a href="register" id="a-reg">Don't have an account yet? Register here</a>
	            </div>                
                <input class="reg-btn" type="submit" name="login" value="Sign In">
            </form>            
        </div>
    </section>
    
    <!-- Footer -->
	<jsp:include page="footer.jsp" />
	
</body>
</html>
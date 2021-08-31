<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
	<title>Register</title>
</head>
<body>
	<!-- Header -->
	<jsp:include page="header.jsp" />
    
    <section> 
        <h1>Register</h1>
        <div>
           <form:form action="./registerNewUser" method="post" modelAttribute="credential"> <!-- name="myForm" onsubmit="return(validate())"> -->
                <fieldset>
                    <legend>Please fill out the form below:</legend>
                    <h6>(all fields are required)</h6>
                    <h6 style="color: red;" ><c:out value="${usernameMessage }" /></h6>
                    <h6 style="color: red;"><c:out value="${emailMessage }" /></h6>
                    <div class="r-input">                    	
						<form:input type="text" path="username" placeholder="Username" required="required" id="username" autofocus="autofocus" />
						<form:errors path="username" class="form-error" />
					</div>
					<div class="r-input">				
						<form:input type="password" id="password" path="password" placeholder="Password" required="required"/>
						<form:errors path="password" class="form-error" />
					</div>
					<div class="r-input" id="div-passConfirm">				
						<input type="password" id="passConfirm" name="passwordConfirm" placeholder="Confirm Password" required="required" />
					</div>
					<span id="passConfirmMsg" style="color:red;"> </span>
					<div class="r-input">						
						<input type="email" name="eMail" placeholder="Email" required="required" value="${user.email }" id="email"/>
					</div>
					<div class="r-input">
						<input type="text" name="firstName" placeholder="First Name" required="required" value="${user.firstName }" />
					</div>
					<div class="r-input">
						<input type="text" id="lastName" name="lastName" placeholder="Last Name" required="required" value="${user.lastName }" />
					</div>
                </fieldset>
                <input class="reg-btn" type="submit" value="Register">
                 <a class="reg-btn a-reg-btn" id="a-btn">Go Back</a>
            </form:form>
        </div>
    </section>
    
    <script src="scripts/go_back.js" type="text/javascript"></script>
    <script>
	    let password = document.getElementById('password');
	    let passwordConfirm = document.getElementById('passConfirm');
	    passwordConfirm.addEventListener('input', function() {
	    	let passwordConfirmMsg = document.getElementById('passConfirmMsg');
	    	if (password.value != passwordConfirm.value) {
		    	passwordConfirmMsg.innerHTML = 'Your password doesn\'t match.';
		    	passwordConfirm.autofocus=true;
		    } else {
		    	passwordConfirmMsg.innerHTML = '';
		    }
	    });
	    
	    let emailInput = document.getElementById('email');
		if (emailInput.value == '') {
			emailInput.autofocus=true;
		}
	    let usernameInput = document.getElementById('username');
	    if (usernameInput.value == '') {
	        emailInput.autofocus=false;
			usernameInput.autofocus=true;
		}
    </script>
	<!-- Footer -->
	<jsp:include page="footer.jsp" />
</body>
</html>
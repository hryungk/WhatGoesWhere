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
	<title>Update Password</title>
</head>
<body>
	<!-- Header -->
	<jsp:include page="header.jsp" />
    
    <section> 
        <h1>Update Password</h1>
        <div>
           <form action="./updatePassword" method="post"> 
                <fieldset>
                    <legend>Please fill out the form below:</legend>
                    <h6>(all fields are required)</h6>
                    <h6 style="color: red;" ><c:out value="${message }" /></h6>
					<div class="r-input">				
						<input type="password" id="oldPass" name="oldPassword" placeholder="Current Password" required="required" autofocus/>
					</div>
					<div class="r-input">				
						<input type="password" id="newPass" name="newPassword" placeholder="New Password" required="required" />
					</div>
					<div class="r-input" id="div-newPassConfirm">				
						<input type="password" id="newPassConfirm" name="newPasswordConfirm" placeholder="Confirm New Password" required="required" />
					</div>
					<span id="newPassConfirmMsg" style="color:red;"> </span>
                </fieldset>
                <input class="reg-btn" type="submit" value="Update">
                 <a class="reg-btn a-reg-btn" id="a-btn">Go Back</a>
            </form>
        </div>
    </section>
    
    <script src="scripts/go_back.js" type="text/javascript"></script>
    <script>
	    let newPass = document.getElementById('newPass');
	    let newPassConfirm = document.getElementById('newPassConfirm');
	    newPassConfirm.addEventListener('input', function() {
	    	let newPassConfirmMsg = document.getElementById('newPassConfirmMsg');
	    	if (newPass.value != newPassConfirm.value) {
		    	newPassConfirmMsg.innerHTML = 'Your password doesn\'t match.';
		    	newPassConfirm.autofocus=true;
		    } else {
		    	newPassConfirmMsg.innerHTML = '';
		    }
	    });
    </script>
	<!-- Footer -->
	<jsp:include page="footer.jsp" />
</body>
</html>
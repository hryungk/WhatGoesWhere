<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
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
	<title>Contact Us</title>
</head>
<body>
	<!-- Header -->
	<jsp:include page="header.jsp" />
    
    <section> 
        <h1>Contact Us</h1>
        <div>
           <form action="contact" method="post">
                <fieldset>                	
                    <legend>Please fill out the form below:</legend>
                    <p>We'll get back to you as soon as possible.</p>
                    <div class="r-input">
						<input type="email" name="eMail" placeholder="Email (required)" required="required" value="${email }"/>
					</div>
					<div class="r-input">
						<input type="text" name="subject" placeholder="Subject (required)" required="required" />
					</div>
					<div class="r-input">	 			
						<textarea rows="10" name="message" placeholder="Enter your message (required)" required="required"></textarea>						
					</div>					
                </fieldset>            
                <input class="reg-btn" type="submit" value="Send">
                <a class="reg-btn a-reg-btn" id="a-btn" href="list">Go Back</a>
            </form>
            
        </div>
    </section>
    <script src="scripts/go_back.js" type="text/javascript"></script>
    <!-- Footer -->
	<jsp:include page="footer.jsp" />
	
</body>
</html>
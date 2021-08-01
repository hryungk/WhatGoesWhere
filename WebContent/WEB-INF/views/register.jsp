 <!-- Header -->
<jsp:include page="header.jsp" />
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
    <section> 
        <h1>Register</h1>
        <div>
           <form action="./register" method="post"> <!-- name="myForm" onsubmit="return(validate())"> -->
                <fieldset>
                    <legend>Please fill out the form below:</legend>
                    <h6>(all fields are required)</h6>
                    <h6 style="color: red;" ><c:out value="${usernameMessage }" /></h6>
                    <h6 style="color: red;"><c:out value="${emailMessage }" /></h6>
                    <div class="r-input">                    	
						<input type="text" name="username" placeholder="Username" required="required" value="${user.username }" />
					</div>
					<div class="r-input">				
						<input type="password" name="password" placeholder="Password" required="required" />
					</div>
					<div class="r-input">						
						<input type="email" name="eMail" placeholder="Email" required="required"  value="${user.email }"/>
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
            </form>
        </div>
    </section>
    <script>
    	function validate() {
        	console.log("validate() method.")
	        if (document.myForm.username.value == "") {
	            alert("Username already exists. Choose a different one.");
	            document.myForm.username.focus();
	            return false;
	        } 
        	return true;
    	}
    	
    	var element = document.getElementById('a-btn');
		element.setAttribute('href', document.referrer);		
		element.onclick = function() {
			history.back();
			return false;
		}
    </script>
    
   <!-- Footer -->
<jsp:include page="footer.jsp" />
 <!-- Header -->
<jsp:include page="header.jsp" />
<!-- JSTL includes -->	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <section> 
        <h1>Sign In</h1>
        <div>
           <form action="./login" method="POST" name="myForm">
                <fieldset>
                    <legend>Please enter your credentials below:</legend>                    
            		<p style="color: red;" id="msg"><c:out value="${message }" /></p>
                    <div class="r-input">
						<input type="text" name="username" placeholder="Username" required="required" id="input-username" value="${username }"/>
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
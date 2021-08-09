 <!-- Header -->
<jsp:include page="header.jsp" />
<!-- JSTL includes -->	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <section> 
        <h1>Sign In</h1>
        <div>
           <form action="login" method="POST">
                <fieldset>
                    <legend>Please enter your credentials below:</legend>                    
            		<p style="color: red;" id="msg"><c:out value="${message }" /></p>
                    <div class="r-input">
						<input type="text" name="userName" placeholder="Username" required="required" value="${username }"/>
					</div>
					<div class="r-input">				
						<input type="password" name="password" placeholder="Password" required="required" />
					</div>					
                </fieldset>
                <div>
	            	<a href="register">Don't have an account yet? Register here</a>
	            </div>                
                <input class="reg-btn" type="submit" name="login" value="Sign In">
            </form>            
        </div>
    </section>
    
   <!-- Footer -->
<jsp:include page="footer.jsp" />
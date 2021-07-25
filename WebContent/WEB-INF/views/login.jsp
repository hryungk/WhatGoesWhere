 <!-- Header -->
<jsp:include page="header.jsp" />
    
    <section> 
        <h1>Sign In</h1>
        <div>
           <form action="#">
                <fieldset>
                    <legend>Please enter your credentials below:</legend>
                    <div class="r-input">
						<input type="email" name="eMail" placeholder="Email" required="required" />
					</div>
					<div class="r-input">				
						<input type="password" name="password" placeholder="Password" required="required" />
					</div>					
                </fieldset>
                <div>
	            	<a href="register">Don't have an account yet? Register here</a>
	            </div>                
                <input class="reg-btn" type="submit" value="Sign In">
            </form>
            
        </div>
    </section>
    
   <!-- Footer -->
<jsp:include page="footer.jsp" />
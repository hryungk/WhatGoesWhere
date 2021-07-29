 <!-- Header -->
<jsp:include page="header.jsp" />
    
    <section> 
        <h1>Delete account</h1>
        <div>
           <form action="./deleteUser" method="post">
                <fieldset>                	
                    <legend>Please let us know why you want to leave:</legend>
                    <div class="r-input">
						<input type="email" name="eMail" placeholder="Email (required)" required="required" value="${user.email}" />
					</div>
					<div class="r-input">	 			
						<textarea rows="10" name="message" placeholder="Enter your message" ></textarea>						
					</div>					
                </fieldset>            
                <input type="hidden" name="email" value="${user.email }">
                <input class="reg-btn" type="submit" value="Send">
            </form>
            
        </div>
    </section>
    
   <!-- Footer -->
<jsp:include page="footer.jsp" />
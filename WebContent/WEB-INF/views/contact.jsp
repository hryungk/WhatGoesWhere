 <!-- Header -->
<jsp:include page="header.jsp" />
<%	
	/* String userName = (String) session.getAttribute("userName");
	String eMail = (String) session.getAttribute("eMail");
	if (userName != null) {
		userName  = session.getAttribute("userName").toString();
	}
	if (eMail != null) {
		eMail  = session.getAttribute("eMail").toString();
	} */
%>	

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
						<textarea rows="10" name="message" placeholder="Enter your message (required)" required="required"></textarea>						
					</div>					
                </fieldset>            
                <input class="reg-btn" type="submit" value="Send">
            </form>
            
        </div>
    </section>
    
   <!-- Footer -->
<jsp:include page="footer.jsp" />
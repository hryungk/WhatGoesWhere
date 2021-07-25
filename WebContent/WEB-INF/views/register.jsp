 <!-- Header -->
<jsp:include page="header.jsp" />
    
    <section> 
        <h1>Register</h1>
        <div>
           <form action="#">
                <fieldset>
                    <legend>Please fill out the form below:</legend>
                    <h6>(all fields are required)</h6>
                    <div class="r-input">
						<input type="email" name="eMail" placeholder="Email" required="required" />
					</div>
					<div class="r-input">				
						<input type="password" name="password" placeholder="Password" required="required" />
					</div>
					<div class="r-input">
						<input type="text" name="firstName" placeholder="First Name" required="required" />
					</div>
					<div class="r-input">
						<input type="text" id="lastName" name="lastName" placeholder="Last Name" required="required" />
					</div>
                </fieldset>
                <input class="reg-btn" type="submit" value="Register">
            </form>
        </div>
    </section>
    
   <!-- Footer -->
<jsp:include page="footer.jsp" />
 <!-- Header -->
<jsp:include page="header.jsp" />
    
    <section> 
        <h1>Edit an Existing Item</h1>
        <div>
           <form action="#">
                <fieldset>
                    <legend>Please modify the data below:</legend>
                    <div class="r-input">
						<input type="text" name="name" placeholder="Item Name (required)" required="required" />
					</div>
					<div class="r-input">				
						<input type="text" name="condition" placeholder="Item Condition" />
					</div>
					<div class="r-input">
						<input type="text" name="bestOption" placeholder="Best Option (required)" required="required" />
					</div>
					<div class="r-input">
						<input type="text" name="specialInstruction" placeholder="Special Instruction" />
					</div>
					<div class="r-input">
						<input type="text" name="notes" placeholder="Notes" />
					</div>
                </fieldset>
                <input class="reg-btn" type="submit" value="Update">
            </form>
        </div>
    </section>
    
   <!-- Footer -->
<jsp:include page="footer.jsp" />
 <!-- Header -->
<jsp:include page="header.jsp" />
    
    <section> 
        <h1>Add a New Item</h1>
        <div>
           <form action="./addNewItem" method="POST">
                <fieldset>
                    <legend>Please fill out the form below:</legend>
                    <div class="r-input">
						<input type="text" name="itemName" placeholder="Item Name (required)" required="required" />
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
                <input type="submit" value="Add" class="reg-btn" />
            </form>
            
        </div>
    </section>
    
   <!-- Footer -->
<jsp:include page="footer.jsp" />
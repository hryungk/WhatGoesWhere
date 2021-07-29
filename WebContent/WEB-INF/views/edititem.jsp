 <!-- Header -->
<jsp:include page="header.jsp" />
    
    <section> 
        <h1>Edit an Existing Item</h1>
        <div>
           <form action="./updateItem" method="POST">
                <fieldset>
                    <legend>Please modify the data below:</legend>
                    <div class="r-input">
						<input type="text" name="itemName" placeholder="Item Name (required)" required="required" value="${item.name }"/>
					</div>
					<div class="r-input">				
						<input type="text" name="condition" placeholder="Item Condition" value="${item.condition }"/>
					</div>
					<div class="r-input">
						<input type="text" name="bestOption" placeholder="Best Option (required)" required="required"  value="${item.bestOption }"/>
					</div>
					<div class="r-input">
						<input type="text" name="specialInstruction" placeholder="Special Instruction" value="${item.specialInstruction }" />
					</div>
					<div class="r-input">
						<input type="text" name="notes" placeholder="Notes"  value="${item.notes }"/>
					</div>
					<div class="r-input">
						<input type="text" name="date" placeholder="Added on MM-DD-YYYY" disabled="disabled" value="${item.addedDate }"/>
					</div>
                </fieldset>
                <input type="hidden" name="username" value="${user.username }">
                <input class="reg-btn" type="submit" value="Update">                
            </form>
            
            <form action="./backToProfile" method="post">
				<input type="hidden" name="username" value="${user.username }">
				<input type="submit" class="reg-btn" value="Go Back">                
			</form>           
       		
        </div>
    </section>
    
   <!-- Footer -->
<jsp:include page="footer.jsp" />
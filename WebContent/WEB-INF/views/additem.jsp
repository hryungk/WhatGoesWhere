 <!-- Header -->
<jsp:include page="header.jsp" />
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
    <section> 
        <h1>Add a New Item</h1>
        <div>
           <form action="./additem" method="POST">
                <fieldset>
                    <legend>Please fill out the form below:</legend>
                    <p style="color: red;" id="msg"><c:out value="${message }" /></p>
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
                <a class="reg-btn a-reg-btn" id="a-btn">Go Back</a>
            </form>            
        </div>
    </section>
    <script>
    	var element = document.getElementById('a-btn');

		 // Provide a standard href to facilitate standard browser features such as 
		 //  - Hover to see link
		 //  - Right click and copy link
		 //  - Right click and open in new tab
		 element.setAttribute('href', document.referrer);
		
		 // We can't let the browser use the above href for navigation. If it does, 
		 // the browser will think that it is a regular link, and place the current 
		 // page on the browser history, so that if the user clicks "back" again,
		 // it'll actually return to this page. We need to perform a native back to
		 // integrate properly into the browser's history behavior
		 element.onclick = function() {
		   history.back();
		   return false;
		 }
    </script>
    
   <!-- Footer -->
<jsp:include page="footer.jsp" />
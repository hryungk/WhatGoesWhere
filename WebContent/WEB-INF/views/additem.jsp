 <!-- Header -->
<jsp:include page="header.jsp" />
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    
    <section> 
        <h1>Add a New Item</h1>
        <div>
            <form:form action="./additem" method="POST" modelAttribute="item" >
                <fieldset>
                    <legend>Please fill out the form below:</legend>
                    <p style="color: red;" id="msg"><c:out value="${message }" /></p>
                    <div class="r-input">
						<form:input type="text" path="name" placeholder="Item Name (required)" required="required" />
						<form:errors path="name" class="form-error" />
					</div>
					<div class="r-input">				
						<form:input type="text" path="condition" placeholder="Item Condition" />
						<form:errors path="condition" class="form-error" />
					</div>
					<div class="r-input">
						<form:select path="bestOption" required="required">
							<form:option value="" label="--- Best Option (required) ---" disabled="true"/>
							<form:options items="${bestOptions }" itemLabel="value" />							
						</form:select>
						<form:errors path="bestOption" class="form-error" />
					</div>
					<div class="r-input">
						<form:input type="text" path="specialInstruction" placeholder="Special Instruction" />
						<form:errors path="specialInstruction" class="form-error" />
					</div>
					<div class="r-input">
						<form:input type="text" path="notes" placeholder="Notes" />
						<form:errors path="notes" class="form-error" />
					</div>
                </fieldset>
                <input type="submit" value="Add" class="reg-btn" />
                <a class="reg-btn a-reg-btn" id="a-btn">Go Back</a>
            </form:form>         
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
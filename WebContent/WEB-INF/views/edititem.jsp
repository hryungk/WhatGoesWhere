 <!-- Header -->
<jsp:include page="header.jsp" />
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    
    <section> 
        <h1>Edit an Existing Item</h1>
        <div>
        	<c:set scope="page" var="sel1">${item.bestOption }</c:set>
        	<form:form action="./edititem" method="POST" modelAttribute="item">
                <fieldset>
                    <legend>Please modify the data below:</legend>
                    <div class="r-input">
						<form:input type="text" path="name" placeholder="Item Name (required)" required="required" value="${item.name }"/>
						<form:errors path="name" class="form-error" />
					</div>
					<div class="r-input">				
						<form:input type="text" path="condition" placeholder="Item Condition" value="${item.condition }"/>
						<form:errors path="condition" class="form-error" />
					</div>
					<div class="r-input">
						<form:input type="text" path="bestOption" placeholder="Best Option (required)" required="required"  value="${item.bestOption }"/>
						<form:select path="bestOption" required="required">
							<form:option value="" label="--- Best Option (required) ---" disabled="true"/>
							<form:options items="${bestOptions }" itemLabel="value" />				
						</form:select>
						<form:errors path="bestOption" class="form-error" />
					</div>
					<div class="r-input">
						<form:input type="text" path="specialInstruction" placeholder="Special Instruction" value="${item.specialInstruction }" />
						<form:errors path="specialInstruction" class="form-error" />
					</div>
					<div class="r-input">
						<form:input type="text" path="notes" placeholder="Notes"  value="${item.notes }"/>
						<form:errors path="notes" class="form-error" />
					</div>
					<div class="r-input">
						<input type="text" name="date" placeholder="Added on MM-DD-YYYY" disabled="disabled" 
						value="<c:out value="${item.addedDate }" />" />
					</div>
                </fieldset>
                <input class="reg-btn" type="submit" value="Update">  
                <a class="reg-btn a-reg-btn" id="a-btn">Go Back</a>              
            </form:form>
        </div>
    </section>
    <script>
    	var element = document.getElementById('a-btn');
		element.setAttribute('href', document.referrer);		
		element.onclick = function() {
			history.back();
			return false;
		}
    </script>
   <!-- Footer -->
<jsp:include page="footer.jsp" />
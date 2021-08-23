<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<!-- JSTL includes -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<!-- Bootstrap 4 -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
		integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
		crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	
	<link rel="stylesheet" type="text/css" href="css/stylesheet.css">
	<title>Edit an Item</title>
</head>
<body>
	<!-- Header -->
	<jsp:include page="header.jsp" />
    
    <section> 
        <h1>Edit an Existing Item</h1>
        <div>
        	<form:form action="./editItem" method="POST" modelAttribute="item">
                <fieldset>
                    <legend>Please modify the item below:</legend>
                    <p style="color: red;" id="msg"><c:out value="${message }" /></p>
                    <div class="r-input">
                    	<form:input type="hidden" path="id" value="${item.id }"/>
						<form:input type="text" path="name" placeholder="Item Name (required)" required="required" value="${item.name }"/>
						<form:errors path="name" class="form-error" />
					</div>
					<div class="r-input">				
						<form:input type="text" path="condition" placeholder="Item Condition" value="${item.condition }"/>
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
                <input type="hidden" name="pageName" value="${pageName }"/>
                <input class="reg-btn" type="submit" value="Update">  
                <a class="reg-btn a-reg-btn" id="a-btn" href="profile">Go Back</a>              
            </form:form>
        </div>
    </section>

    <script src="scripts/go_back.js" type="text/javascript"></script>
    	
	<!-- Footer -->
	<jsp:include page="footer.jsp" />
</body>
</html>
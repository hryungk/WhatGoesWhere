<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- JSTL includes -->	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html>
<html>
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
	<title>What Goes Where in Redmond?</title>
</head>
<body>
	<header class="menu_container">
        <div class="menu_item" id="menu_item-1">
            <h1 style="font-weight: bold;"><a href="${pageContext.request.contextPath }/">What Goes Where in Redmond?</a></h1>
        </div>
        <div class="menu_item" id="menu_item-2">
            <nav>				
                <ul>
                    <li>
                        <a href="${pageContext.request.contextPath }/">Home</a>
                    </li>
                    <li>
                        <a href="list">Item List</a>
                    </li>
                    <li>
                        <a href="about">About</a>
                    </li>
                    <li class="nav-item dropdown" id="nav-last-li">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false"> Sign In/Register </a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" id="ddi-1" href="login">Sign In</a>
							<a class="dropdown-item" id="ddi-2" href="register">Register</a>
						</div>
					</li>
                </ul>
            </nav>
        </div>
    </header>
<%	
	String userName = (String) session.getAttribute("userName");
	if (userName != null) {
		userName  = session.getAttribute("userName").toString();
	}
%>	
    <script>	    
		let username = '<%= userName%>';
    	if (username != 'null') {
    		let aDropdown = document.getElementById('navbarDropdown');
    		aDropdown.innerHTML = 'Account';
    		let addi1 = document.getElementById('ddi-1');
    		addi1.href = 'profile';
    		addi1.innerHTML = 'Profile';
    		let addi2 = document.getElementById('ddi-2');
    		addi2.href = 'logout';
    		addi2.innerHTML = 'Sign Out';
    	}
    </script>
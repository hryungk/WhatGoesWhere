<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- JSTL includes -->	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<!-- Bootstrap CSS 5 -->
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
		crossorigin="anonymous">
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
                    <li>
                        <a href="login">Sign In/Register</a>
                    </li>
                </ul>
            </nav>
        </div>
    </header>
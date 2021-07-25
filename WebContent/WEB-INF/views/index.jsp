<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
            <h1 style="font-weight: bold;"><a href="#">What Goes Where in Redmond?</a></h1>
        </div>
        <div class="menu_item" id="menu_item-2">
            <nav>				
                <ul>
                    <li>
                        <a href="#">Home</a>
                    </li>
                    <li>
                        <a href="list">Item List</a>
                    </li>
                    <li>
                        <a href="about">About</a>
                    </li>
                    <li>
                        <a href="register">Sign In/Register</a>
                    </li>
                </ul>
            </nav>
        </div>
    </header>
    
    <section id="find-item"> 
        <h2>Search any item to learn how to dispose of it properly</h2>
        <div>
            <input type=text placeholder="Search an item" id="find-inp"/>
            <input type="submit" value="Search" class="find-btn" id="find-btn"/>
        </div>
        <a href="list">List of Items</a>
    </section>
    
    <footer>
    		<div>
			<div class="footer-nav">
				<p class="footer-nav-title">Site Map</p>
				<hr>
				<ul>
					<li><a href="index">Home</a></li>
					<li><a href="list">Item List</a></li>
					<li><a href="about">About</a></li>
					<li><a href="register">Register</a></li>					
				</ul>
			</div>
			<div class="footer-nav">	
				<p class="footer-nav-title">You are saving the earth :)</p>
				<hr>			
				<p class="copyright">&copy; 2021. All rights reserved.</p>
			</div>
			<div class="footer-nav">
				<p class="footer-nav-title">Support</p>
				<hr>
				<ul>
					<li><a href="contact">Contact Us</a></li>
				</ul>
			</div>
		</div>
		<!-- <div>
			<p class="copyright">&copy; 2021. All rights reserved.</p>
		</div> -->
	</footer>
</body>
</html>
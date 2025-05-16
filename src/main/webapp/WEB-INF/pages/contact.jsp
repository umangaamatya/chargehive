<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/globals.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  </head>
  <body>
    <div class="contact-page">
      <div class="div">
        <p class="text-wrapper">
          Email, call, or complete the form to learn how ChargeHive can make your EV journey smoother and hassle-free
        </p>
        <p class="p">Our support team is ready to address any concerns or queries you may have.</p>
        <div class="text-wrapper-2">info@chargehive.com</div>
        <div class="text-wrapper-3">010502204</div>
        <div class="text-wrapper-4">Customer Support</div>
        <p class="text-wrapper-5">
          For media-related questions or press inquiries, please contact us at media@chargehive.com
        </p>
        <div class="text-wrapper-6">Media Inquiries</div>
        <div class="overlap">
          <div class="text-wrapper-7">Connect with us</div>
          <div class="text-wrapper-7">Connect with us</div>
        </div>
        <div class="overlap-group">
          <p class="by-contacting-us-you">
            <span class="span">By contacting us, you agree to our </span>
            <span class="text-wrapper-8">Terms of service</span>
            <span class="span"> and </span>
            <span class="text-wrapper-8">Privacy Policy</span>
          </p>
          <div class="text-wrapper-9">Get in Touch</div>
          
          <!-- Updated Input Fields -->
          <div class="div-wrapper">
            <label for="fullName" class="text-wrapper-10"></label>
            <input type="text" id="fullName" placeholder="Full name" />
          </div>
          <div class="overlap-2">
            <label for="phoneNumber" class="text-wrapper-10"></label>
            <input type="text" id="phoneNumber" placeholder="Phone Number"/>
          </div>
          <div class="overlap-3">
            <label for="help" class="text-wrapper-10"></label>
            <textarea id="help" placeholder="How can we help?"></textarea>
          </div>
          <div class="overlap-4">
            <label for="email" class="text-wrapper-11"></label>
            <input type="email" id="email" placeholder="Your email"/>
          </div>
          <!-- Submit Button -->
          <div class="overlap-5">
            <button class="contact-submit-btn">Submit</button>
          </div>
        </div>
        <footer class="footer">
		  <div class="text-wrapper-13">Quick Links</div>
		  <a href="https://maps.app.goo.gl/UHbNqyUCy5yAo7FD9" target="_blank" rel="noopener noreferrer" class="text-wrapper-14">Gairidhara, Kathmandu</a>
		  <a class="text-wrapper-15">010502204</a>
		  <p class="text-wrapper-16">©️ 2025 ChargeHive. All rights reserved.</p>
		  <a href="mailto:support@chargehive.com" target="_blank" rel="noopener noreferrer">
		    <div class="text-wrapper-17">support@chargehive.com</div>
		  </a>
		  <a href="https://maps.app.goo.gl/UHbNqyUCy5yAo7FD9" target="_blank" rel="noopener noreferrer" class="text-wrapper-18">Samata Marg - 4</a>
		  <a href="${pageContext.request.contextPath}/product" class="text-wrapper-19">Charging Stations</a>
		  <a href="${pageContext.request.contextPath}/admin" class="text-wrapper-20">Dashboard</a>
		  <a href="https://www.linkedin.com" target="_blank" rel="noopener noreferrer" class="text-wrapper-21">LinkedIn</a>
		  <a href="" class="text-wrapper-22">Privacy Policy</a>
		  <a href="" class="text-wrapper-23">Cookie Policy</a>
		  <a href="" class="text-wrapper-24">Terms of Service</a>
		  <a href="https://www.facebook.com" target="_blank" rel="noopener noreferrer" class="text-wrapper-25">Facebook</a>
		  <a href="https://www.instagram.com" target="_blank" rel="noopener noreferrer" class="text-wrapper-26">Instagram</a>
		  <a href="https://x.com" target="_blank" rel="noopener noreferrer" class="text-wrapper-27">X</a>
		  <a href="${pageContext.request.contextPath}/about" class="text-wrapper-28">About Us</a>
		  <a href="${pageContext.request.contextPath}/contact" class="text-wrapper-29">Contact Us</a>
		  <div class="text-wrapper-30">Social</div>
		  <div class="text-wrapper-31">Legal</div>
		  <img class="line" src="resources/images/Line 1.png" />
		  <img class="img" src="resources/images/chargeHiveLogoTransparent.png" />
		</footer>
        <div class="navigation">
		  <div class="navbar">

		    <%
		        HttpSession session1 = request.getSession(false);
		        boolean isLoggedIn = (session != null && session.getAttribute("userEmail") != null);
		    %>
		    <div class="overlap-6">
		      <% if (isLoggedIn) { %>
		          <a href="${pageContext.request.contextPath}/logout" class="text-wrapper-32">Logout</a>
		      <% } else { %>
		          <a href="${pageContext.request.contextPath}/login" class="text-wrapper-32">Login</a>
		      <% } %>
		    </div>
		
		    <a href="${pageContext.request.contextPath}/index" class="text-wrapper-33">Home</a>
		    <a href="${pageContext.request.contextPath}/product" class="charging-station">Charging Stations</a>
		    <a href="${pageContext.request.contextPath}/contact" class="about-us">Contact</a>
		    <a href="${pageContext.request.contextPath}/about" class="text-wrapper-34">About Us</a>
		    <a href="${pageContext.request.contextPath}/admin" class="text-wrapper-35">Dashboard</a>
		    <img class="chargehivelogo" src="resources/images/chargehiveLogo.png" />
		
		  </div>
		</div>
      </div>
    </div>
    
  </body>
</html>
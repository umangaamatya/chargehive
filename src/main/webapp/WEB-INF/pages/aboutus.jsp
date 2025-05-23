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
    <div class="about-us-page">
      <div class="div">
        <div class="overlap"><img class="shutterstock" src="resources/images/shutterstock_2553993469.jpg" /></div>
        <footer class="footer">
		  <div class="text-wrapper">Quick Links</div>
		  <a href="https://maps.app.goo.gl/UHbNqyUCy5yAo7FD9" target="_blank" rel="noopener noreferrer" class="text-wrapper-2">Gairidhara, Kathmandu</a>
		  <a class="text-wrapper-3">010502204</a>
		  <p class="p">©️ 2025 ChargeHive. All rights reserved.</p>
		  <a href="mailto:support@chargehive.com" target="_blank" rel="noopener noreferrer">
		    <div class="text-wrapper-4">support@chargehive.com</div>
		  </a>
		  <a href="https://maps.app.goo.gl/UHbNqyUCy5yAo7FD9" target="_blank" rel="noopener noreferrer" class="text-wrapper-5">Samata Marg - 4</a>
		  <a href="${pageContext.request.contextPath}/product" class="text-wrapper-6">Charging Stations</a>
		  <a href="${pageContext.request.contextPath}/admin" class="text-wrapper-7">Dashboard</a>
		  <a href="https://www.linkedin.com" target="_blank" rel="noopener noreferrer" class="text-wrapper-8">LinkedIn</a>
		  <a href="" class="text-wrapper-9">Privacy Policy</a>
		  <a href="" class="text-wrapper-10">Cookie Policy</a>
		  <a href="" class="text-wrapper-11">Terms of Service</a>
		  <a href="https://www.facebook.com" target="_blank" rel="noopener noreferrer" class="text-wrapper-12">Facebook</a>
		  <a href="https://www.instagram.com" target="_blank" rel="noopener noreferrer" class="text-wrapper-13">Instagram</a>
		  <a href="https://x.com" target="_blank" rel="noopener noreferrer" class="text-wrapper-14">X</a>
		  <a href="${pageContext.request.contextPath}/about" class="text-wrapper-15">About Us</a>
		  <a href="${pageContext.request.contextPath}/contact" class="text-wrapper-16">Contact Us</a>
		  <div class="text-wrapper-17">Social</div>
		  <div class="text-wrapper-18">Legal</div>
		  <img class="line" src="resources/images/Line 1.png" />
		  <img class="img" src="resources/images/chargeHiveLogoTransparent.png" />
		</footer>
        <img class="locations-group" src="resources/images/chargeHiveLogoTransparent.png" />
        <p class="as-the-world">
          &nbsp;&nbsp; As the world transitions to sustainable practices, transportation is one of the most significant
          changes to make. Going from combustion based vehicles to EV’s involves various differences such as, mileage,
          charging as opposed quickly fill up the fuel tank, comparatively more electronic systems and technologies.
        </p>
        <p class="established-in">
          Established in 2022, ChargeHive is an EV Charging Station company that provides a platform which enables users
          to view available charging stations and their location, prices, charging speeds, book a time slot, and then
          finally pay once done charging. <br /><br />
          ChargeHive is a mega project that aims to build EV charging stations all over Nepal. Eventually, ChargeHive
          aims to expand globally with a widespread connected network of charging stations. Overall, this project is one
          of the stepping stones to make the transition from fuel based vehicles to electric vehicles a smoother and
          hassle-free experience by providing EV users with a convenient and efficient way to recharge their vehicle on
          the go.
        </p>
        <div class="text-wrapper-19">Our Company</div>
		<div class="navigation">
		  <div class="navbar">
		
		    <%-- Session-based dynamic login/logout --%>
		    <%
		        HttpSession session1 = request.getSession(false);
		        boolean isLoggedIn = (session != null && session.getAttribute("userEmail") != null);
		        String role = (session1 != null && session1.getAttribute("userRole") != null)
		                  ? session1.getAttribute("userRole").toString()
		                  : "";
		    %>
		    <div class="overlap-group">
		        <% if (isLoggedIn) { %>
		            <a href="${pageContext.request.contextPath}/logout" class="text-wrapper-20">Logout</a>
		        <% } else { %>
		            <a href="${pageContext.request.contextPath}/login" class="text-wrapper-20">Login</a>
		        <% } %>
		    </div>
		
		    <a href="${pageContext.request.contextPath}/index" class="text-wrapper-21">Home</a>
		    <a href="${pageContext.request.contextPath}/product" class="charging-station">Charging Stations</a>
		    <a href="${pageContext.request.contextPath}/contact" class="about-us">Contact</a>
		    <a href="${pageContext.request.contextPath}/about" class="text-wrapper-22">About Us</a>
		    <a href="#" onclick="handleDashboardRedirect()" class="text-wrapper-23">Dashboard</a>
			<script>
			  function handleDashboardRedirect() {
			    <% if (!isLoggedIn) { %>
			      alert("You must log in first to access the dashboard.");
			      window.location.href = "<%= request.getContextPath() %>/login";
			    <% } else if ("admin".equalsIgnoreCase(role)) { %>
			      window.location.href = "<%= request.getContextPath() %>/admin";
			    <% } else if ("user".equalsIgnoreCase(role)) { %>
			      window.location.href = "<%= request.getContextPath() %>/user";
			    <% } %>
			  }
			</script>
		    <img class="chargehivelogo" src="resources/images/chargehiveLogo.png" />
		  </div>
		</div>
      </div>
    </div>
  </body>
</html>
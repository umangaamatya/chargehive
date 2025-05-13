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
    <div class="login-page"> 
      <div class="div">
        <div class="overlap">
         <%
		  String errorMessage = (String) request.getAttribute("error");
		  String successMessage = (String) request.getAttribute("success");
		%>
		
		<% if (errorMessage != null && !errorMessage.isEmpty()) { %>
		  <div class="error-message"><%= errorMessage %></div>
		<% } %>
		
		<% if (successMessage != null && !successMessage.isEmpty()) { %>
		  <div class="success-message"><%= successMessage %></div>
		<% } %>
          
          <p class="don-t-have-an">
            <span class="text-wrapper">Don't have an account? </span>
            <a href="${pageContext.request.contextPath}/registration" class="span">Register here</a>
          </p>
        
          <div class="text-wrapper-3">Log in to ChargeHive</div>
          
          <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="text-wrapper-2">Email</div>
            <input type="email" name="user_email" class="user_email" placeholder="username@gmail.com" required />
          
            <div class="text-wrapper-5">Password</div>
            <input type="password" name="user_password" class="user_password" placeholder="•••••••••••" required />
          
            <button type="submit" class="login-button">Log In</button>
          </form>
        
          <img class="vector" src="${pageContext.request.contextPath}/resources/images/Visibility Icon-1.png" alt="Toggle visibility" />
        </div>

        <div class="navigation">
          <div class="navbar">
            <li class="overlap-3"><a href="${pageContext.request.contextPath}/login" class="text-wrapper-8">Login</a></li>
            <a href="${pageContext.request.contextPath}/index" class="text-wrapper-9">Home</a>
            <a href="${pageContext.request.contextPath}/product" class="charging-station">Charging Stations</a>
            <a href="${pageContext.request.contextPath}/contact" class="about-us">Contact</a>
            <a href="${pageContext.request.contextPath}/about" class="text-wrapper-10">About Us</a>
            <a href="${pageContext.request.contextPath}/admin" class="text-wrapper-11">Dashboard</a>
            <img class="chargehivelogo" src="${pageContext.request.contextPath}/resources/images/chargehiveLogo.png" />
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
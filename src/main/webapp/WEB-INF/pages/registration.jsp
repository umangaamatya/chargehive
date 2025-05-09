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
    <div class="registration-page">
      <div class="div">
        <form action="${pageContext.request.contextPath}/registration" method="post" class="overlap" >
            <div class="text-wrapper-2">Create a new account</div>
          
            <div class="form-group" style="top: 166px; left: 641px;">
              <label class="form-label">Email</label>
              <input type="email" name="user_email" placeholder="username@gmail.com" required style="top: 50px; position: absolute;" />
            </div>
          
            <div class="form-group" style="top: 319px; left: 641px;">
              <label class="form-label">Password</label>
              <input type="password" name="user_password" placeholder="************" required style="top: 50px; position: absolute;" />
            </div>
          
            <div class="form-group" style="top: 472px; left: 641px;">
              <label class="form-label">Confirm Password</label>
              <input type="password" name="confirm_password" placeholder="************" required style="top: 50px; position: absolute;" />
            </div>
          
            <div class="form-group" style="top: 166px; left: 156px;">
              <label class="form-label">Full Name</label>
              <input type="text" name="user_name" placeholder="Enter your full name" required style="top: 50px; position: absolute;" />
            </div>
          
            <div class="form-group" style="top: 319px; left: 156px;">
              <label class="form-label">Contact Number</label>
              <input type="tel" name="user_contact" placeholder="98XXXXXXXX" required style="top: 50px; position: absolute;" />
            </div>
          
            <div class="form-group" style="top: 472px; left: 156px;">
              <label class="form-label">Address</label>
              <input type="text" name="user_address" placeholder="City, Street..." required style="top: 50px; position: absolute;" />
            </div>
          
          
          
            <button type="submit" style="top: 662px; left: 366px; position: absolute;">Sign Up</button>
          
            <img class="visibility-icon" src="${pageContext.request.contextPath}/resources/images/Visibility Icon-1.png" />
            <img class="img" src="${pageContext.request.contextPath}/resources/images/Visibility Icon-1.png" />
        </form>
        <div class="navigation">
          <div class="navbar">
            <li class="overlap-4"><a href="${pageContext.request.contextPath}/login.jsp" class="text-wrapper-11">Login</a></li>
            <a href="${pageContext.request.contextPath}/index" class="text-wrapper-12">Home</a>
            <a href="${pageContext.request.contextPath}/product" class="charging-station">Charging Stations</a>
            <a href="${pageContext.request.contextPath}/contact" class="about-us">Contact</a>
            <a href="${pageContext.request.contextPath}/about" class="text-wrapper-13">About Us</a>
            <a href="${pageContext.request.contextPath}/admin" class="text-wrapper-14">Dashboard</a>
            <img class="chargehivelogo" src="${pageContext.request.contextPath}/resources/images/chargehiveLogo.png" />
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
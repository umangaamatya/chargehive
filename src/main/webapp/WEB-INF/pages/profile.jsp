<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>    
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/globals.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  </head>
  <body>
    <div class="user-profile-page">
    <%
	    int userId = (int) session.getAttribute("userId");
	    String fullName = "", email = "", contact = "", address = "";
	
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/chargehive", "root", "");
	        PreparedStatement stmt = conn.prepareStatement("SELECT user_fullName, user_email, user_contact, user_address FROM user WHERE user_id = ?");
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	
	        if (rs.next()) {
	            fullName = rs.getString("user_fullName");
	            email = rs.getString("user_email");
	            contact = rs.getString("user_contact");
	            address = rs.getString("user_address");
	        }
	
	        rs.close();
	        stmt.close();
	        conn.close();
	    } catch (Exception e) {
	        out.println("<p style='color:red;'>Error loading profile data: " + e.getMessage() + "</p>");
	    }
	%>
      <div class="div">
        <div class="overlap"><img class="IMG" src="resources/images/IMG_0663 2.JPG" /></div>
        
        <div class="total-station-group">
            <div class="overlap-group">
              <div class="text-wrapper">Loyalty Points</div>
              <div class="text-wrapper-2">1,220</div>
            </div>
          </div>

        <div class="AC-group">
          <div class="overlap-group">
            <div class="text-wrapper">Membership</div>
            <div class="text-wrapper-2">Silver</div>
          </div>
        </div>
        <div class="overlap-2">
          <div class="overlap-3">
            <div class="rectangle"></div>
            <div class="rectangle-2"></div>
            <div class="text-wrapper-3">258.63</div>
            <div class="text-wrapper-4">8</div>
            <div class="text-wrapper-5">Station Name</div>
            <div class="text-wrapper-6">Rapid Station</div>
            <div class="text-wrapper-7">Location</div>
            <div class="text-wrapper-8">Jhamsikhel</div>
            <div class="text-wrapper-9">Duration</div>
            <div class="text-wrapper-10">00:34:26</div>
            <div class="text-wrapper-11">Total Amount in ₹</div>
            <div class="text-wrapper-12">Port No.</div>
            <div class="text-wrapper-13">1</div>
            <div class="text-wrapper-14">S. No.</div>
            <div class="text-wrapper-15">Type</div>
            <div class="text-wrapper-16">Fast</div>
          </div>
          <div class="overlap-4">
            <div class="text-wrapper-17">9</div>
            <div class="text-wrapper-18">343.03</div>
            <div class="text-wrapper-19">Charge Hub</div>
            <div class="text-wrapper-20">Lazimpat</div>
            <div class="text-wrapper-21">00:45:04</div>
            <div class="text-wrapper-22">3</div>
            <div class="text-wrapper-23">Slow</div>
          </div>
          <div class="overlap-5">
            <div class="text-wrapper-17">3</div>
            <div class="text-wrapper-24">198.21</div>
            <div class="text-wrapper-25">Quick Charge</div>
            <div class="text-wrapper-26">Naxal</div>
            <div class="text-wrapper-27">00:23:21</div>
            <div class="text-wrapper-22">2</div>
            <div class="text-wrapper-23">Fast</div>
          </div>
          <div class="overlap-6">
            <div class="text-wrapper-17">5</div>
            <div class="text-wrapper-18">225.12</div>
            <div class="text-wrapper-28">Amp Station</div>
            <div class="text-wrapper-20">Golfutar</div>
            <div class="text-wrapper-21">00:26:17</div>
            <div class="text-wrapper-22">4</div>
            <div class="text-wrapper-23">Fast</div>
          </div>
        </div>
        <div class="text-wrapper-29">Recent charging sessions</div>
        <div class="text-wrapper-30"><%= fullName %></div>
        <div class="text-wrapper-31">Contact</div>
        <div class="text-wrapper-32">Address</div>
        <div class="text-wrapper-33">Email</div>
        <div class="text-wrapper-34">Password</div>
        <div class="overlap-7">
          <div class="text-wrapper-35"><%= email %></div>
          <img class="vector" src="resources/images/update-vector.png" onclick="openModal('emailModal')"  />
        </div>
        <div class="overlap-8">
          <div class="contact-number-field"></div>
          <div class="text-wrapper-36">...........</div>
          <img class="img" src="resources/images/update-vector.png" />
        </div>
        <div class="overlap-9">
          <img class="vector" src="resources/images/update-vector.png" onclick="openModal('contactModal')"/>
          <div class="text-wrapper-37"><%= contact %></div>
        </div>
        <div class="overlap-10">
          <img class="vector" src="resources/images/update-vector.png" onclick="openModal('addressModal')"/>
          <div class="text-wrapper-38"><%= address %></div>
        </div>
        <div class="navigation">
          <div class="navbar">
            <a href="${pageContext.request.contextPath}/index" class="text-wrapper-39">Home</a>
            <a href="${pageContext.request.contextPath}/product" class="charging-station">Charging Stations</a>
            <a href="${pageContext.request.contextPath}/contact" class="about-us">Contact</a>
            <a href="${pageContext.request.contextPath}/about" class="text-wrapper-40">About Us</a>
            <a href="${pageContext.request.contextPath}/admin" class="text-wrapper-41">Dashboard</a>
            <img class="chargehivelogo" src="resources/images/chargehiveLogo.png" />
            <a href="${pageContext.request.contextPath}/profile"><img class="admin-icon" src="resources/images/Admin icon.png" /></a>
          </div>
        </div>
      </div>
    </div>
    
    <!-- Email Update Modal -->
	<div id="emailModal" class="profile-modal">
	  <div class="profile-modal-content">
	    <span class="profile-close" onclick="closeModal('emailModal')">&times;</span>
	    <h2>Update Email</h2>
	    <form action="${pageContext.request.contextPath}/profile" method="post">
	      <input type="hidden" name="attribute" value="email">
	      <input type="email" name="value" required placeholder="Enter new email">
	      <button type="submit">Update</button>
	    </form>
	  </div>
	</div>
	
	<!-- Contact Update Modal -->
	<div id="contactModal" class="profile-modal">
	  <div class="profile-modal-content">
	    <span class="profile-close" onclick="closeModal('contactModal')">&times;</span>
	    <h2>Update Contact</h2>
	    <form action="${pageContext.request.contextPath}/profile" method="post">
	      <input type="hidden" name="attribute" value="contact">
	      <input type="text" name="value" required placeholder="Enter new contact number">
	      <button type="submit">Update</button>
	    </form>
	  </div>
	</div>
	
	<!-- Address Update Modal -->
	<div id="addressModal" class="profile-modal">
	  <div class="profile-modal-content">
	    <span class="profile-close" onclick="closeModal('addressModal')">&times;</span>
	    <h2>Update Address</h2>
	    <form action="${pageContext.request.contextPath}/profile" method="post">
	      <input type="hidden" name="attribute" value="address">
	      <input type="text" name="value" required placeholder="Enter new address">
	      <button type="submit">Update</button>
	    </form>
	  </div>
	</div>
	
	<script>
	  function openModal(id) {
	    document.getElementById(id).style.display = "block";
	  }
	
	  function closeModal(id) {
	    document.getElementById(id).style.display = "none";
	  }
	
	  // Close modal when clicking outside
	  window.onclick = function(event) {
	    document.querySelectorAll(".modal").forEach(modal => {
	      if (event.target === modal) modal.style.display = "none";
	    });
	  };
	</script>
  </body>
</html>

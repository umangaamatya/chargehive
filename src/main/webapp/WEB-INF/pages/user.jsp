<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/globals.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  </head>
  <body>
    <div class="user-dashboard">
    
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
        <div class="text-wrapper-42">Stations near you</div>
        <div class="tables-container">
          <table class="styled-table">
            <thead>
              <tr>
                <th>Station ID</th>
                <th>Station Name</th>
                <th>Location</th>
                <th>Availability</th>
                <th>Price (₹) per kWh</th>
                <th>Ports</th>
                <th>Type</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              <%
                String url = "jdbc:mysql://localhost:3307/chargehive";
                String username = "root";
                String password = "";
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;

                try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  conn = DriverManager.getConnection(url, username, password);
                  stmt = conn.createStatement();
                  String searchQuery = request.getParameter("searchQuery");
                  String sql = "SELECT * FROM station";
                  if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                    sql += " WHERE station_name LIKE '%" + searchQuery + "%'";
                  }
                  rs = stmt.executeQuery(sql);
                  
                  while(rs.next()) {
              %>
              <tr>
                <td><%= rs.getInt("station_id") %></td>
                <td><%= rs.getString("station_name") %></td>
                <td><%= rs.getString("station_location") %></td>
                <td><%= rs.getString("station_availability") %></td>
                <td><%= rs.getFloat("station_price") %></td>
                <td><%= rs.getInt("station_ports") %></td>
                <td><%= rs.getString("station_type") %></td>
                <td>
                  <form action="bookStation" method="POST">
                    <input type="hidden" name="stationId" value="<%= rs.getInt("station_id") %>" />
                    <button type="button" class="book-btn" onclick="confirmBooking(<%= rs.getInt("station_id") %>)">Book</button>
                  </form>
                </td>
              </tr>
              <%
                  }
                } catch(Exception e) {
                  out.println("<tr><td colspan='8'>Error loading station data: " + e.getMessage() + "</td></tr>");
                } finally {
                  try { if (rs != null) rs.close(); } catch(Exception e) {}
                  try { if (stmt != null) stmt.close(); } catch(Exception e) {}
                  try { if (conn != null) conn.close(); } catch(Exception e) {}
                }
              %>
            </tbody>
          </table>
        </div>
        
        <%
		  Integer currentUserId = (Integer) session.getAttribute("userId");
		  if (currentUserId == null) {
		%>
		  <p style="color: red;">User not logged in. Please login to view your bookings.</p>
		<%
		  } else {
		%>
		  <div class="text-wrapper-200" style="margin-top: 40px;">Your Booking History</div>
		  <table class="booking-styled-table">
		    <thead>
		      <tr>
		        <th>Booking ID</th>
		        <th>Booking Date</th>
		        <th>Station ID</th>
		      </tr>
		    </thead>
		    <tbody>
		      <%
		        Connection bookingConn = null;
		        PreparedStatement bookingStmt = null;
		        ResultSet bookingRs = null;
		
		        try {
		          Class.forName("com.mysql.cj.jdbc.Driver");
		          bookingConn = DriverManager.getConnection("jdbc:mysql://localhost:3307/chargehive", "root", "");
		          bookingStmt = bookingConn.prepareStatement("SELECT * FROM booking WHERE user_id = ?");
		          bookingStmt.setInt(1, currentUserId);
		          bookingRs = bookingStmt.executeQuery();
		
		          boolean hasBookings = false;
		          while (bookingRs.next()) {
		            hasBookings = true;
		      %>
		      <tr>
		        <td><%= bookingRs.getInt("booking_id") %></td>
		        <td><%= bookingRs.getString("booking_date") %></td>
		        <td><%= bookingRs.getInt("station_id") %></td>
		      </tr>
		      <%
		          }
		
		          if (!hasBookings) {
		      %>
		      <tr><td colspan="3">No bookings found for your account.</td></tr>
		      <%
		          }
		        } catch(Exception e) {
		          out.println("<tr><td colspan='3'>Error loading booking data: " + e.getMessage() + "</td></tr>");
		        } finally {
		          try { if (bookingRs != null) bookingRs.close(); } catch(Exception e) {}
		          try { if (bookingStmt != null) bookingStmt.close(); } catch(Exception e) {}
		          try { if (bookingConn != null) bookingConn.close(); } catch(Exception e) {}
		        }
		      %>
		    </tbody>
		  </table>
		<%
		  }
		%>

        <%
		  String firstName = fullName.split(" ")[0];
		%>
		<div class="text-wrapper-43">Hi, <%= firstName %>.</div>
       <div style="display: flex; justify-content: flex-end; margin-bottom: 30px; transform: translate(-30px,-290px)">
		  <form method="GET" action="${pageContext.request.contextPath}/user" style="display: flex; align-items: center; gap: 10px;">
		
		    <!-- Search input with arrow inside -->
		    <div style="display: flex; align-items: center; border-radius: 25px; overflow: hidden; background: white;">
		      <input 
		        type="text" 
		        name="searchQuery" 
		        placeholder="Search station..." 
		        value="<%= request.getParameter("searchQuery") != null ? request.getParameter("searchQuery") : "" %>"
		        style="padding: 10px 15px; border: none; width: 220px; font-size: 14px; outline: none;"
		      />
		      <button type="submit" style="background: none; border: none; padding: 10px; cursor: pointer;">
		        <img src="resources/images/Search Arrow.png" alt="Search" style="height: 20px; width: 20px;" />
		      </button>
		    </div>
		
		    <!-- Clear button outside, dark blue gradient -->
		    <button 
		      type="submit" 
		      name="clear" 
		      value="true"
		      style="padding: 10px 20px; border: none; border-radius: 25px; background: linear-gradient(to right, #0a2342, #274472); color: white; font-weight: bold; cursor: pointer;"
		    >
		      Clear
		    </button>
		  </form>
		</div>

        <div class="navigation">
          <div class="navbar">
            <a href="${pageContext.request.contextPath}/index" class="text-wrapper-45">Home</a>
            <a href="${pageContext.request.contextPath}/product" class="charging-station">Charging Stations</a>
            <a href="${pageContext.request.contextPath}/contact" class="about-us">Contact</a>
            <a href="${pageContext.request.contextPath}/aboutus" class="text-wrapper-46">About Us</a>
            <a href="${pageContext.request.contextPath}/admin" class="text-wrapper-47">Dashboard</a>
            <img class="chargehivelogo" src="resources/images/chargehiveLogo.png" />
            <a href="${pageContext.request.contextPath}/profile"><img class="admin-icon" src="resources/images/Admin icon.png" /></a>
          </div>
        </div>
      </div>
    </div>
    
    <script>
	  function confirmBooking(stationId) {
	    const confirmed = confirm("Do you want to book this station?");
	    if (confirmed) {
	      fetch("bookStation", {
	        method: "POST",
	        headers: {
	          "Content-Type": "application/x-www-form-urlencoded"
	        },
	        body: "stationId=" + stationId
	      })
	      .then(response => response.json())
	      .then(data => {
	        if (data.status === "success") {
	          alert("Booking successful!");
	          location.reload();
	        } else {
	          alert("Error: " + data.message);
	        }
	      })
	      .catch(error => {
	        alert("Request failed: " + error);
	      });
	    }
	  }
	</script>
	
  </body>
  
  
</html>
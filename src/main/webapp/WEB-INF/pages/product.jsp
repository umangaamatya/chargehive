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
    <div class="product-page">
      <div class="div">
        
        
       <div class="text-wrapper">Discover our stations</div>
       
       <!--  Kathmandu -->
       <div class="station-region">
	       <div class="region-title">Kathmandu</div>
	       <div class="station-cards">
			  <!-- Swift Charge -->
			  <div class="station-card">
			    <img src="resources/images/DQ-station.png" alt="Swift Charge Station" />
			    <h3>Swift Charge</h3>
			    <p>Swift Charge houses ChargeHive’s fastest charging stations for EV’s in Nepal.</p>
			    <p><strong>Location:</strong> Baneshwor, Kathmandu</p>
			    <p><strong>Ports:</strong> 8</p>
			    <p><strong>Price:</strong> 45₹ per kWh</p>
			  </div>
			
			  <!-- Quick Charge -->
			  <div class="station-card">
			    <img src="resources/images/02_evbox_business_charger_us_image2.jpg" alt="Quick Charge Station" />
			    <h3>Quick Charge</h3>
			    <p>Quick Station is the latest addition to ChargeHive’s large network of stations.</p>
			    <p><strong>Location:</strong> Naxal, Kathmandu</p>
			    <p><strong>Ports:</strong> 12</p>
			    <p><strong>Price:</strong> 55₹ per kWh</p>
			  </div>
			
			  <!-- Charge Hub -->
			  <div class="station-card">
			    <img src="resources/images/GDN_E-Pit-Charger_GD21_02.jpeg" alt="Charge Hub" />
			    <h3>Charge Hub</h3>
			    <p>Charge Hub in Lazimpat has the most variety of chargers for different vehicles.</p>
			    <p><strong>Location:</strong> Lazimpat, Kathmandu</p>
			    <p><strong>Ports:</strong> 10</p>
			    <p><strong>Price:</strong> 50₹ per kWh</p>
			  </div>
			
			  <!-- Eco Charge -->
			  <div class="station-card">
			    <img src="resources/images/ked202204110035.700x.0.jpg" alt="Eco Charge" />
			    <h3>Eco Charge</h3>
			    <p>Eco Charge is ChargeHive’s most eco-friendly charging station.</p>
			    <p><strong>Location:</strong> Nayabazar, Kathmandu</p>
			    <p><strong>Ports:</strong> 10</p>
			    <p><strong>Price:</strong> 50₹ per kWh</p>
			  </div>
			
			  <!-- Amp Station -->
			  <div class="station-card">
			    <img src="resources/images/360_F_854728069_gAjSaJu4CBSUBwWQSgN3LN1Dp9QM128f.jpg" alt="Amp Station" />
			    <h3>Amp Station</h3>
			    <p>Amp Station is ChargeHive’s busiest charging station in Kathmandu Valley.</p>
			    <p><strong>Location:</strong> Golfutar, Kathmandu</p>
			    <p><strong>Ports:</strong> 8</p>
			    <p><strong>Price:</strong> 60₹ per kWh</p>
			  </div>
			</div>
		</div>	
		
			<div class="station-region lalitpur-region">
				<div class="region-title">Lalitpur</div> 
					<div class="station-cards-and-table">
						<div class="station-cards lalitpur-cards">
						  <!-- Volt Station -->
						  <div class="station-card">
						    <img src="resources/images/208106923_l_normal_none-1.jpg" alt="Volt Station" />
						    <h3>Volt Station</h3>
						    <p>Volt Station has the most high tech and latest charging stations in Nepal.</p>
						    <p><strong>Location:</strong> Sanepa, Lalitpur</p>
						    <p><strong>Ports:</strong> 12</p>
						    <p><strong>Price:</strong> 55₹ per kWh</p>
						  </div>
						
						  <!-- Rapid Station -->
						  <div class="station-card">
						    <img src="resources/images/e89cd3a6-adbd-4f29-ac54-2faecf5548a0.jpeg" alt="Rapid Station" />
						    <h3>Rapid Station</h3>
						    <p>Rapid Station is one of ChargeHive’s first charging stations for EV’s in Lalitpur.</p>
						    <p><strong>Location:</strong> Jhamsikhel, Lalitpur</p>
						    <p><strong>Ports:</strong> 8</p>
						    <p><strong>Price:</strong> 45₹ per kWh</p>
						  </div>
						</div>
						
						<div class="station-table-section" style="margin: 40px 0 0 40px; padding: 20px; max-width: 1200px; width: fit-content; display: flex; flex-direction: column; align-items: flex-start;">
						  <div class="text-wrapper-63" style="color: white; font-size: 38px; font-weight: bold; margin-bottom: -450px; text-align:left;">Station Details</div>
						
						  <div class="tables-container" style="width: fit-content;">
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
						            rs = stmt.executeQuery("SELECT * FROM station");
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
						        </tr>
						        <%
						            }
						          } catch(Exception e) {
						            out.println("<tr><td colspan='7'>Error: " + e.getMessage() + "</td></tr>");
						          } finally {
						            try { if (rs != null) rs.close(); } catch(Exception e) {}
						            try { if (stmt != null) stmt.close(); } catch(Exception e) {}
						            try { if (conn != null) conn.close(); } catch(Exception e) {}
						          }
						        %>
						      </tbody>
						    </table>
						  </div>
						</div>
					  </div>	
			     	</div>
			     	
			     	 
		          </div>
	      
	      <!-- Station Details Section (Read-Only) -->
		
		
        <div class="navigation">
		  <div class="navbar">
		
		    <%-- Conditional Login/Logout link --%>
		   <%
		  	HttpSession session1 = request.getSession(false);
		    boolean isLoggedIn = (session1 != null && session1.getAttribute("userEmail") != null);
		    String role = (session1 != null && session1.getAttribute("userRole") != null)
		                  ? session1.getAttribute("userRole").toString()
		                  : "";
			%>
		    
		    
		    <div class="div-wrapper">
		      <% if (isLoggedIn) { %>
		          <a href="${pageContext.request.contextPath}/logout" class="text-wrapper-28">Logout</a>
		      <% } else { %>
		          <a href="${pageContext.request.contextPath}/login" class="text-wrapper-28">Login</a>
		      <% } %>
		    </div>
		
		    <a href="${pageContext.request.contextPath}/index" class="text-wrapper-29">Home</a>
		    <a href="${pageContext.request.contextPath}/product" class="charging-station">Charging Stations</a>
		    <a href="${pageContext.request.contextPath}/contact" class="about-us">Contact</a>
		    <a href="${pageContext.request.contextPath}/about" class="text-wrapper-30">About Us</a>
		    <% if ("admin".equalsIgnoreCase(role)) { %>
			    <a href="${pageContext.request.contextPath}/admin" class="text-wrapper-31">Dashboard</a>
			<% } else if ("user".equalsIgnoreCase(role)) { %>
			    <a href="${pageContext.request.contextPath}/user" class="text-wrapper-31">Dashboard</a>
			<% } %>
		    <img class="chargehivelogo" src="resources/images/chargehiveLogo.png" />
		
		  </div>
		</div>
		
		
		
		 
      </div>

     
  </body>
</html>

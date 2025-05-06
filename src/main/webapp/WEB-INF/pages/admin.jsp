<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/globals.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  </head>
  <body>
    <div class="admin-dashboard">
      <div class="div">
        <div class="total-station-group">
            <div class="overlap-group">
              <div class="text-wrapper-500">Total Stations</div>
              <div class="text-wrapper-501">3,210</div>
            </div>
          </div>
        <div class="total-chargers-group">
          <div class="overlap-group">
            <div class="text-wrapper">Available Stations</div>
            <div class="text-wrapper-2">2,040</div>
          </div>
        </div>
        <div class="AC-group">
          <div class="overlap">
            <div class="text-wrapper-3">Total Users</div>
            <div class="text-wrapper-4">825</div>
          </div>
        </div>
        <div class="DC-group">
          <div class="overlap">
            <div class="text-wrapper-5">Total Bookings</div>
            <div class="text-wrapper-6">950</div>
          </div>
        </div>
        <div class="overlap-2">
          <div class="rectangle"></div>
          <div class="rectangle-2"></div>
          <div class="div-2"></div>
          <div class="div-3"></div>
          <div class="rectangle-3"></div>
          <div class="div-4"></div>
          <div class="div-5"></div>
          <div class="rectangle-4"></div>
          <div class="text-wrapper-7">Station ID</div>
          <div class="text-wrapper-26">Station Name</div>
          <div class="text-wrapper-33">Location</div>
          <div class="text-wrapper-40">Availability</div>
          <div class="text-wrapper-47">Price (₹) per kWh</div>
          <div class="text-wrapper-48">Ports</div>
          <div class="text-wrapper-49">Type</div>
          <%
			  String url = "jdbc:mysql://localhost:3307/chargehive";
			  String username = "root";
			  String password = ""; // Replace with your MySQL password
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
			      <div class="station-row">
			        <div class="overlap-3">
			          <div class="text-wrapper-56"><%= rs.getInt("station_id") %></div>
			          <div class="text-wrapper-59"><%= rs.getString("station_name") %></div>
			          <div class="text-wrapper-60"><%= rs.getString("station_location") %></div>
			          <div class="text-wrapper-61"><%= rs.getString("station_availability") %></div>
			          <div class="text-wrapper-57"><%= rs.getFloat("station_price") %></div>
			          <div class="text-wrapper-58"><%= rs.getInt("station_ports") %></div>
			          <div class="text-wrapper-62"><%= rs.getString("station_type") %></div>
			        </div>
			        
			      </div>
			<%
			      }
			  } catch(Exception e) {
			      out.println("<p>Error loading station data: " + e.getMessage() + "</p>");
			  } finally {
			      try { if (rs != null) rs.close(); } catch(Exception e) {}
			      try { if (stmt != null) stmt.close(); } catch(Exception e) {}
			      try { if (conn != null) conn.close(); } catch(Exception e) {}
			  }
			%>
          </div>
          <img class="vector" src="resources/images/update-vector.png" />
          <img class="img" src="resources/images/delete-vector.png" />
          <img class="vector-2" src="resources/images/update-vector.png" />
          <img class="vector-3" src="resources/images/delete-vector.png" />
          <img class="vector-4" src="resources/images/update-vector.png" />
          <img class="vector-5" src="resources/images/delete-vector.png" />
          <img class="vector-6" src="resources/images/update-vector.png" />
          <img class="vector-7" src="resources/images/delete-vector.png" />
          <img class="vector-8" src="resources/images/update-vector.png" />
          <img class="vector-9" src="resources/images/delete-vector.png" />
          <img class="vector-10" src="resources/images/update-vector.png" />
          <img class="vector-11" src="resources/images/delete-vector.png" />
          <img class="vector-12" src="resources/images/update-vector.png" />
          <img class="vector-13" src="resources/images/delete-vector.png" />
        </div>
        <div class="text-wrapper-63">Station Details</div>
        <div class="text-wrapper-64">User Details</div>
        <div class="text-wrapper-65">Hi, Ardent.</div>

          <button id="addStationBtn" class="overlap-4 add-btn">
            <img src="resources/images/Vector.png" alt="Add" />
            <span>Add Station</span>
          </button>
          
          <button id="addUserBtn" class="overlap-5 add-btn">
            <img src="resources/images/Vector.png" alt="Add" />
            <span>Add User</span>
          </button>

        <div class="overlap-6">
          <div class="overlap-7">
            <div class="rectangle-2"></div>
            <div class="text-wrapper-67">User ID</div>
            <div class="text-wrapper-68">Full Name</div>
            <div class="text-wrapper-69">Email Address</div>
            <div class="text-wrapper-70">Contact</div>
            <div class="text-wrapper-71">Address</div>
            <div class="text-wrapper-72">Loyalty Points</div>
            <div class="rectangle-5"></div>
            <div class="text-wrapper-73">200001</div>
            <div class="text-wrapper-74">1,460</div>
            <div class="text-wrapper-75">Aarya Paudel</div>
            <div class="text-wrapper-76">apaudel@gmail.com</div>
            <div class="text-wrapper-77">9824432553</div>
            <div class="text-wrapper-78">Bhaisepati</div>
            <img class="vector" src="resources/images/update-vector.png" />
            <img class="img" src="resources/images/delete-vector.png" />
          </div>
          <div class="div-2">
            <div class="text-wrapper-79">955</div>
            <div class="text-wrapper-80">200003</div>
            <div class="text-wrapper-81">Mary Lama</div>
            <div class="text-wrapper-82">mlama@gmail.com</div>
            <div class="text-wrapper-83">Golfutar</div>
            <div class="text-wrapper-84">9843872461</div>
            <img class="vector-14" src="resources/images/update-vector.png" />
            <img class="vector-15" src="resources/images/delete-vector.png" />
          </div>
          <div class="div-3">
            <div class="text-wrapper-85">645</div>
            <div class="text-wrapper-86">200005</div>
            <div class="text-wrapper-87">Sweta Thapa</div>
            <div class="text-wrapper-88">sthapa@gmail.com</div>
            <div class="text-wrapper-89">9834742914</div>
            <img class="vector-16" src="resources/images/update-vector.png" />
            <img class="vector-17" src="resources/images/delete-vector.png" />
            <div class="text-wrapper-90">Balkot</div>
          </div>
          <div class="div-4">
            <div class="text-wrapper-91">865</div>
            <div class="text-wrapper-80">200002</div>
            <div class="text-wrapper-92">Paras Adhikari</div>
            <div class="text-wrapper-93">padhikari@gmail.com</div>
            <div class="text-wrapper-94">Sitapaila</div>
            <div class="text-wrapper-84">9782394322</div>
            <img class="vector-18" src="resources/images/update-vector.png" />
            <img class="vector-19" src="resources/images/delete-vector.png" />
          </div>
          <div class="div-5">
            <div class="text-wrapper-95">8</div>
            <div class="text-wrapper-96">1,220</div>
            <div class="text-wrapper-97">100004</div>
            <div class="text-wrapper-98">Sparsha Shrestha</div>
            <div class="text-wrapper-99">sshrestha@gmail.com</div>
            <div class="text-wrapper-100">9824432553</div>
            <img class="vector-20" src="resources/img/vector-10.svg" />
            <img class="vector-21" src="resources/img/vector.svg" />
            <div class="rectangle-6"></div>
            <div class="text-wrapper-101">1,220</div>
            <div class="text-wrapper-97">200004</div>
            <div class="text-wrapper-102">Sparsha Shrestha</div>
            <div class="text-wrapper-103">sshrestha@gmail.com</div>
            <div class="text-wrapper-104">9705102005</div>
            <img class="vector-22" src="resources/images/update-vector.png" />
            <img class="vector-23" src="resources/images/delete-vector.png" />
            <div class="text-wrapper-105">Nayabazar</div>
          </div>
          <div class="overlap-8">
            <div class="text-wrapper-106">2,410</div>
            <div class="text-wrapper-86">200006</div>
            <div class="text-wrapper-107">Umanga Amatya</div>
            <div class="text-wrapper-88">uamatya@gmail.com</div>
            <div class="text-wrapper-89">9705022004</div>
            <img class="vector-16" src="resources/images/update-vector.png" />
            <img class="vector-17" src="resources/images/delete-vector.png" />
            <div class="text-wrapper-108">Baluwatar</div>
          </div>
        </div>
        <div class="navigation">
          <div class="navbar">
            <a href="${pageContext.request.contextPath}/index" class="text-wrapper-109">Home</a>
            <a href="${pageContext.request.contextPath}/product" class="charging-station">Charging Stations</a>
            <a href="${pageContext.request.contextPath}/contact" class="about-us">Contact</a>
            <a href="${pageContext.request.contextPath}/about" class="text-wrapper-110">About Us</a>
            <a href="${pageContext.request.contextPath}/admin" class="text-wrapper-111">Dashboard</a>
            <img class="chargehivelogo" src="resources/images/chargehiveLogo.png" />
            <img class="admin-icon" src="resources/images/Admin icon.png" />
          </div>
        </div>
      </div>
    </div>
    
    <!-- Add Station Modal -->
	<div id="addStationModal" class="modal">
	  <div class="modal-content">
	    <span class="close">&times;</span>
	    <h2>Add New Station</h2>
	    <form id="addStationForm" method="POST" action="${pageContext.request.contextPath}/admin/addStation">
	      <div class="form-group">
	        <label for="stationName">Station Name</label>
	        <input type="text" id="stationName" name="stationName" required>
	      </div>
	      <div class="form-group">
	        <label for="location">Location</label>
	        <input type="text" id="location" name="location" required>
	      </div>
	      <div class="form-group">
	        <label for="availability">Availability</label>
	        <select id="availability" name="availability" required>
	          <option value="Available">Available</option>
	          <option value="Unavailable">Unavailable</option>
	        </select>
	      </div>
	      <div class="form-group">
	        <label for="price">Price (₹) per kWh</label>
	        <input type="number" id="price" name="price" step="0.01" required>
	      </div>
	      <div class="form-group">
	        <label for="ports">Ports</label>
	        <input type="number" id="ports" name="ports" required>
	      </div>
	      <div class="form-group">
	        <label for="type">Type</label>
	        <select id="type" name="type" required>
	          <option value="Fast">Fast</option>
	          <option value="Slow">Slow</option>
	        </select>
	      </div>
	      <button type="submit" class="submit-btn">Add Station</button>
	    </form>
	  </div>
	</div>
	
	
	<script>
	  document.addEventListener('DOMContentLoaded', function() {
	    // Debugging - check if elements exist
	    console.log("Initializing modal...");
	    
	    // Get the modal elements
	    const modal = document.getElementById("addStationModal");
	    const addStationBtn = document.getElementById("addStationBtn");
	    const closeBtn = document.querySelector(".close");
	    const form = document.getElementById("addStationForm");
	    
	    const stationName = form.querySelector('[name="stationName"]').value;
	    console.log("Station Name:", stationName);
	    
	    
	    // Debug log elements
	    console.log("Modal element:", modal);
	    console.log("Add Station button:", addStationBtn);
	    console.log("Close button:", closeBtn);
	    console.log("Form:", form);
	    
	    // Ensure all elements exist before adding event listeners
	    if (!modal) {
	      console.error("Modal element not found");
	      return;
	    }
	    
	    // Set modal to initially hidden (in case CSS didn't load)
	    modal.style.display = "none";
	    
	    // Add Station button click handler
	    if (addStationBtn) {
	      addStationBtn.addEventListener('click', function(e) {
	        e.preventDefault();
	        console.log("Add Station button clicked");
	        modal.style.display = "block";
	      });
	    } else {
	      console.error("Add Station button not found");
	    }
	    
	    // Close button click handler
	    if (closeBtn) {
	      closeBtn.addEventListener('click', function() {
	        modal.style.display = "none";
	      });
	    }
	    
	    // Close modal when clicking outside
	    window.addEventListener('click', function(event) {
	      if (event.target === modal) {
	        modal.style.display = "none";
	      }
	    });
	    
	    // Form submission handler
	    if (form) {
	    	form.addEventListener("submit", function(e) {
	    	    e.preventDefault();
	    	    
	    	    // Debug: Log all form data before sending
	    	    const formData = new FormData(form);
	    	    for (let [key, value] of formData.entries()) {
	    	    	console.log("Form Data:")
	    	        console.log(key, value);
	    	    }
	    	    
	    	    fetch('${pageContext.request.contextPath}/admin/addStation', {
	    	        method: 'POST',
	    	        body: formData
	    	    })
	    	    .then(response => {
	    	        if (!response.ok) {
	    	            return response.json().then(err => { throw err; });
	    	        }
	    	        return response.json();
	    	    })
	    	    .then(data => {
	    	        if (data.status === "success") {
	    	            alert(data.message);
	    	            form.reset();
	    	            modal.style.display = "none";
	    	            
	    	            // Refresh the page to show the new station
	    	            location.reload();
	    	            
	    	            // Or alternatively, you could add the new station to the table without reloading
	    	            // addStationToTable(data.newId, formData);
	    	        } else {
	    	            throw new Error(data.message);
	    	        }
	    	    })
	    	    .catch(error => {
	    	        console.error('Error:', error);
	    	        alert('Error adding station: ' + error.message);
	    	    });
	    	});

	    	// Optional function to add station to table without page reload
	    	function addStationToTable(newId, formData) {
	    	    const tableBody = document.querySelector(".overlap-2"); // Adjust selector to your table
	    	    
	    	    const newRow = document.createElement("div");
	    	    newRow.className = "station-row";
	    	    newRow.innerHTML = `
	    	        <div class="overlap-3">
	    	            <div class="text-wrapper-56">${newId}</div>
	    	            <div class="text-wrapper-57">${formData.get('price')}</div>
	    	            <div class="text-wrapper-58">${formData.get('ports')}</div>
	    	            <div class="text-wrapper-59">${formData.get('stationName')}</div>
	    	            <div class="text-wrapper-60">${formData.get('location')}</div>
	    	            <div class="text-wrapper-61">${formData.get('availability')}</div>
	    	            <div class="text-wrapper-62">${formData.get('type')}</div>
	    	        </div>
	    	    `;
	    	    
	    	    tableBody.insertBefore(newRow, tableBody.firstChild);
	    	}
	    }
	  });
	</script>
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%
int totalStations = 0;
int availableStations = 0;
int totalUsers = 0;
int totalBookings = 0;

String url = "jdbc:mysql://localhost:3307/chargehive";
String username = "root";
String password = "";

try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection(url, username, password);

    Statement stmt1 = conn.createStatement();
    ResultSet rs1 = stmt1.executeQuery("SELECT COUNT(*) FROM station");
    if (rs1.next()) totalStations = rs1.getInt(1);
    rs1.close();

    ResultSet rs2 = stmt1.executeQuery("SELECT COUNT(*) FROM station WHERE station_availability = 'Available'");
    if (rs2.next()) availableStations = rs2.getInt(1);
    rs2.close();

    ResultSet rs3 = stmt1.executeQuery("SELECT COUNT(*) FROM user");
    if (rs3.next()) totalUsers = rs3.getInt(1);
    rs3.close();

    ResultSet rs4 = stmt1.executeQuery("SELECT COUNT(*) FROM booking");
    if (rs4.next()) totalBookings = rs4.getInt(1);
    rs4.close();

    stmt1.close();
    conn.close();
} catch(Exception e) {
    out.println("Error fetching dashboard counts: " + e.getMessage());
}
%>

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
              <div class="text-wrapper-501"><%= totalStations %></div>
            </div>
          </div>
        <div class="total-chargers-group">
          <div class="overlap-group">
            <div class="text-wrapper">Available Stations</div>
            <div class="text-wrapper-2"><%= availableStations %></div>
          </div>
        </div>
        <div class="AC-group">
          <div class="overlap">
            <div class="text-wrapper-3">Total Users</div>
            <div class="text-wrapper-4"><%= totalUsers %></div>
          </div>
        </div>
        <div class="DC-group">
          <div class="overlap">
            <div class="text-wrapper-5">Total Bookings</div>
            <div class="text-wrapper-6"><%= totalBookings %></div>
          </div>
        </div>
        
        <div class="text-wrapper-63">Station Details</div>
        
        <!-- Station Details Table -->
        
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
		          <td>
					  <img src="resources/images/update-vector.png" alt="Update" class="update-icon" 
					       onclick="openUpdateModal(<%= rs.getInt("station_id") %>, 
					                                 '<%= rs.getString("station_name") %>', 
					                                 '<%= rs.getString("station_location") %>', 
					                                 '<%= rs.getString("station_availability") %>', 
					                                 <%= rs.getFloat("station_price") %>, 
					                                 <%= rs.getInt("station_ports") %>, 
					                                 '<%= rs.getString("station_type") %>')">
				  </td>
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

        <div class="text-wrapper-64">User Details</div>
        
        <!-- User Details Table -->
		<div class="styled-table-container">
		  <table class="styled-table">
		    <thead>
		      <tr>
		        <th>User ID</th>
		        <th>Full Name</th>
		        <th>Email</th>
		        <th>Contact</th>
		        <th>Address</th>
		        <th>Loyalty Points</th>
		        <th>Action</th>
		      </tr>
		    </thead>
		    <tbody>
		      <%
	            Connection connUser = null;
	            Statement stmtUser = null;
	            ResultSet rsUser = null;
		        try {
		          Class.forName("com.mysql.cj.jdbc.Driver");
		          connUser = DriverManager.getConnection(url, username, password);
		          stmtUser = connUser.createStatement();
		          rsUser = stmtUser.executeQuery("SELECT * FROM user");
		          while(rsUser.next()) {
		      %>
		      <tr>
				  <td><%= rsUser.getInt("user_id") %></td>
				  <td><%= rsUser.getString("user_fullName") %></td>
				  <td><%= rsUser.getString("user_email") %></td>
				  <td><%= rsUser.getString("user_contact") %></td>
				  <td><%= rsUser.getString("user_address") %></td>
				  <td><%= rsUser.getInt("user_loyaltyPoints") %></td>
				  <td>
				    <img src="resources/images/update-vector.png" alt="Update" class="update-icon"
				         onclick="openUpdateUserModal(
				           <%= rsUser.getInt("user_id") %>,
				           '<%= rsUser.getString("user_fullName") %>',
				           '<%= rsUser.getString("user_email") %>',
				           '<%= rsUser.getString("user_contact") %>',
				           '<%= rsUser.getString("user_address") %>',
				           <%= rsUser.getInt("user_loyaltyPoints") %>,
				           ''  // Password is not fetched from DB for security; user re-enters it
				         )">
				  </td>
				</tr>
		      <%
		          }
		        } catch(Exception e) {
		            out.println("<tr><td colspan='6'>Error: " + e.getMessage() + "</td></tr>");
		        } finally {
		            try { if (rsUser != null) rsUser.close(); } catch(Exception e) {}
		            try { if (stmtUser != null) stmtUser.close(); } catch(Exception e) {}
		            try { if (connUser != null) connUser.close(); } catch(Exception e) {}
		        }
		      %>
		    </tbody>
		  </table>
		</div>
        
        <div class="text-wrapper-65">Hi, Ardent.</div>

          <button id="addStationBtn" class="overlap-4 add-btn">
            <img src="resources/images/Vector.png" alt="Add" />
            <span>Add Station</span>
          </button>
          
          <button id="deleteStationBtn" class="overlap-4 deleteStation-btn">
			  <img src="resources/images/delete-vector.png" alt="Delete" />
			  <span>Delete Station</span>
		  </button>
          
          <button id="addUserBtn" class="overlap-5 add-btn">
            <img src="resources/images/Vector.png" alt="Add" />
            <span>Add User</span>
          </button>
          
          <button id="deleteUserBtn" class="overlap-4 deleteUser-btn">
			<img src="resources/images/delete-vector.png" alt="Delete" />
			<span>Delete User</span>
		  </button>

        
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
  </body>
  
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
          <button type="submit" class="modal-submit-btn">Add Station</button>
        </form>
        <div id="station-error" class="error-message" style="display: none;"></div>
      </div>
    </div>
    
    <div id="updateStationModal" class="modal">
	  <div class="modal-content update-station-modal">
	    <span class="close-update">&times;</span>
	    <h2>Update Station</h2>
	    <form id="updateStationForm" method="POST" action="${pageContext.request.contextPath}/admin/updateStation">
	      <input type="hidden" id="updateStationId" name="stationId">
	      <div class="form-group">
	        <label for="updateStationName">Station Name</label>
	        <input type="text" id="updateStationName" name="stationName" required>
	      </div>
	      <div class="form-group">
	        <label for="updateLocation">Location</label>
	        <input type="text" id="updateLocation" name="location" required>
	      </div>
	      <div class="form-group">
	        <label for="updateAvailability">Availability</label>
	        <select id="updateAvailability" name="availability" required>
	          <option value="Available">Available</option>
	          <option value="Unavailable">Unavailable</option>
	        </select>
	      </div>
	      <div class="form-group">
	        <label for="updatePrice">Price (₹) per kWh</label>
	        <input type="number" id="updatePrice" name="price" step="0.01" required>
	      </div>
	      <div class="form-group">
	        <label for="updatePorts">Ports</label>
	        <input type="number" id="updatePorts" name="ports" required>
	      </div>
	      <div class="form-group">
	        <label for="updateType">Type</label>
	        <select id="updateType" name="type" required>
	          <option value="Fast">Fast</option>
	          <option value="Slow">Slow</option>
	        </select>
	      </div>
	      <button type="submit" class="modal-submit-btn">Update Station</button>
	    </form>
	    <div id="station-error" class="error-message" style="display: none;"></div>
	  </div>
	</div>
    
    <div id="deleteStationModal" class="modal">
	  <div class="modal-content">
	    <span class="close-delete">&times;</span>
	    <h2>Delete Station</h2>
	    <form id="deleteStationForm" method="POST" action="${pageContext.request.contextPath}/admin/deleteStation">
	      <div class="form-group">
	        <label for="deleteStationId">Enter Station ID to delete</label>
	        <input type="number" id="deleteStationId" name="stationId" required>
	      </div>
	      <button type="submit" class="delete-submit-btn">Delete Station</button>
	    </form>
	  </div>
	</div>
	
	<!-- Add User modal -->
	
	<div id="addUserModal" class="modal">
	  <div class="modal-content">
	    <span class="close-add-user">&times;</span>
	    <h2>Add New User</h2>
	    <form id="addUserForm" method="POST" action="${pageContext.request.contextPath}/admin/addUser">
	      
	      <div class="form-group">
	        <label for="userFullName">Full Name</label>
	        <input type="text" id="userFullName" name="userFullName" required>
	      </div>
	
	      <div class="form-group">
	        <label for="userEmail">Email</label>
	        <input type="email" id="userEmail" name="userEmail" required>
	      </div>
	
	      <div class="form-group">
	        <label for="userPassword">Password</label>
	        <input type="password" id="userPassword" name="userPassword" required>
	      </div>
	
	      <div class="form-group">
	        <label for="confirmPassword">Confirm Password</label>
	        <input type="password" id="confirmPassword" name="confirmPassword" required>
	      </div>
	      
	      <div class="form-group">
	        <label for="userContact">Contact Number</label>
	        <input type="text" id="userContact" name="userContact" required>
	      </div>
	
	      <div class="form-group">
	        <label for="userAddress">Address</label>
	        <input type="text" id="userAddress" name="userAddress" required>
	      </div>
	
	      <div class="form-group">
	        <label for="userLoyaltyPoints">Loyalty Points</label>
	        <input type="number" id="userLoyaltyPoints" name="userLoyaltyPoints" value="0" min="0">
	      </div>
	
	      <button type="submit" class="modal-submit-btn">Add User</button>
	    </form>
	  </div>
	</div>
	
	<!-- Delete User Modal -->
	
	<div id="deleteUserModal" class="modal">
	  <div class="modal-content">
	    <span class="close-delete-user">&times;</span>
	    <h2>Delete User</h2>
	    <form id="deleteUserForm" method="POST" action="${pageContext.request.contextPath}/admin/deleteUser">
	      <div class="form-group">
	        <label for="deleteUserId">Enter User ID to delete</label>
	        <input type="number" id="deleteUserId" name="userId" required>
	      </div>
	      <button type="submit" class="delete-submit-btn">Delete User</button>
	    </form>
	  </div>
	</div>
	
	
	<div id="updateUserModal" class="modal">
	  <div class="modal-content update-user-modal">
	    <span class="close-update-user">&times;</span>
	    <h2>Update User</h2>
	    <form id="updateUserForm" method="POST" action="${pageContext.request.contextPath}/admin/updateUser">
	      <input type="hidden" id="updateUserId" name="userId">
	
	      <div class="form-group">
	        <label for="updateUserFullName">Full Name</label>
	        <input type="text" id="updateUserFullName" name="userFullName" required>
	      </div>
	
	      <div class="form-group">
	        <label for="updateUserEmail">Email</label>
	        <input type="email" id="updateUserEmail" name="userEmail" required>
	      </div>
	
	      <div class="form-group">
	        <label for="updateUserPassword">Password</label>
	        <input type="password" id="updateUserPassword" name="userPassword" required>
	      </div>
	
	      <div class="form-group">
	        <label for="confirmUpdatePassword">Confirm Password</label>
	        <input type="password" id="confirmUpdatePassword" required>
	      </div>
	
	      <div class="form-group">
	        <label for="updateUserContact">Contact</label>
	        <input type="text" id="updateUserContact" name="userContact" required>
	      </div>
	
	      <div class="form-group">
	        <label for="updateUserAddress">Address</label>
	        <input type="text" id="updateUserAddress" name="userAddress" required>
	      </div>
	
	      <div class="form-group">
	        <label for="updateUserLoyaltyPoints">Loyalty Points</label>
	        <input type="number" id="updateUserLoyaltyPoints" name="userLoyaltyPoints" min="0" required>
	      </div>
	
	      <button type="submit" class="modal-submit-btn">Update User</button>
	    </form>
	  </div>
	</div>
    
    <script>
      // Your existing modal and form submission JavaScript code here
      document.addEventListener('DOMContentLoaded', function() {
        const modal = document.getElementById("addStationModal");
        const addStationBtn = document.getElementById("addStationBtn");
        const closeBtn = document.querySelector(".close");
        const form = document.getElementById("addStationForm");
    
        modal.style.display = "none";
    
        if(addStationBtn) {
          addStationBtn.addEventListener('click', function(e) {
            e.preventDefault();
            modal.style.display = "block";
          });
        }
    
        if(closeBtn) {
          closeBtn.addEventListener('click', function() {
            modal.style.display = "none";
          });
        }
    
        window.addEventListener('click', function(event) {
          if (event.target === modal) {
            modal.style.display = "none";
          }
        });
    
        if(form) {
        	form.addEventListener("submit", function(e) {
        		  e.preventDefault();
        		  const formData = new FormData(form);

        		  fetch('${pageContext.request.contextPath}/admin/addStation', {
        		      method: 'POST',
        		      body: formData
        		  })
        		  .then(res => res.json())
        		  .then(data => {
        		    const errorDiv = document.getElementById('station-error');
        		    
        		    if (data.status === 'error') {
        		      errorDiv.style.display = 'block';
        		      errorDiv.innerText = data.message;
        		    } else {
        		      errorDiv.style.display = 'none';
        		      alert('Station added successfully!');
        		      location.reload();
        		    }
        		  })
        		  .catch(err => {
        		    console.error('Unexpected error:', err);
        		    alert('Something went wrong. Please try again.');
        		  });
        		}); // ✅ Close here only
        }
      });
      
      document.addEventListener('DOMContentLoaded', function() {
    	    const deleteModal = document.getElementById("deleteStationModal");
    	    const deleteBtn = document.getElementById("deleteStationBtn");
    	    const closeDelete = document.querySelector(".close-delete");
    	    const deleteForm = document.getElementById("deleteStationForm");

    	    if(deleteBtn) {
    	      deleteBtn.addEventListener('click', function(e) {
    	        e.preventDefault();
    	        deleteModal.style.display = "block";
    	      });
    	    }

    	    if(closeDelete) {
    	      closeDelete.addEventListener('click', function() {
    	        deleteModal.style.display = "none";
    	      });
    	    }

    	    window.addEventListener('click', function(event) {
    	      if (event.target === deleteModal) {
    	        deleteModal.style.display = "none";
    	      }
    	    });

    	    if(deleteForm) {
    	      deleteForm.addEventListener("submit", function(e) {
    	        e.preventDefault();
    	        const formData = new FormData(deleteForm);

    	        fetch('${pageContext.request.contextPath}/admin/deleteStation', {
    	            method: 'POST',
    	            body: formData
    	        })
    	        .then(response => response.json())
    	        .then(data => {
    	            if (data.status === "success") {
    	                alert(data.message);
    	                deleteForm.reset();
    	                deleteModal.style.display = "none";
    	                location.reload();
    	            } else {
    	                throw new Error(data.message);
    	            }
    	        })
    	        .catch(error => {
    	            console.error('Error:', error);
    	            alert('Error deleting station: ' + error.message);
    	        });
    	      });
    	    }
    	  });
      
      function openUpdateModal(id, name, location, availability, price, ports, type) {
    	    document.getElementById("updateStationId").value = id;
    	    document.getElementById("updateStationName").value = name;
    	    document.getElementById("updateLocation").value = location;
    	    document.getElementById("updateAvailability").value = availability;
    	    document.getElementById("updatePrice").value = price;
    	    document.getElementById("updatePorts").value = ports;
    	    document.getElementById("updateType").value = type;

    	    document.getElementById("updateStationModal").style.display = "block";
    	  }

    	  document.addEventListener("DOMContentLoaded", function () {
    	    const updateModal = document.getElementById("updateStationModal");
    	    const closeUpdate = document.querySelector(".close-update");
    	    const updateForm = document.getElementById("updateStationForm");

    	    if (closeUpdate) {
    	      closeUpdate.addEventListener("click", function () {
    	        updateModal.style.display = "none";
    	      });
    	    }

    	    window.addEventListener("click", function (event) {
    	      if (event.target === updateModal) {
    	        updateModal.style.display = "none";
    	      }
    	    });

    	    updateForm.addEventListener("submit", function (e) {
    	      e.preventDefault();
    	      const formData = new FormData(updateForm);

    	      fetch('${pageContext.request.contextPath}/admin/updateStation', {
    	        method: 'POST',
    	        body: formData
    	      })
    	        .then(response => response.json())
    	        .then(data => {
    	          if (data.status === "success") {
    	            alert(data.message);
    	            updateForm.reset();
    	            updateModal.style.display = "none";
    	            location.reload();
    	          } else {
    	            throw new Error(data.message);
    	          }
    	        })
    	        .catch(error => {
    	          console.error("Error:", error);
    	          alert("Error updating station: " + error.message);
    	        });
    	    });
    	  });
    	  
    	  document.addEventListener('DOMContentLoaded', function () {
    		    const addUserModal = document.getElementById("addUserModal");
    		    const addUserBtn = document.getElementById("addUserBtn");
    		    const closeAddUser = document.querySelector(".close-add-user");
    		    const addUserForm = document.getElementById("addUserForm");

    		    if (addUserBtn) {
    		      addUserBtn.addEventListener("click", function (e) {
    		        e.preventDefault();
    		        addUserModal.style.display = "block";
    		      });
    		    }

    		    if (closeAddUser) {
    		      closeAddUser.addEventListener("click", function () {
    		        addUserModal.style.display = "none";
    		      });
    		    }

    		    window.addEventListener("click", function (event) {
    		      if (event.target === addUserModal) {
    		        addUserModal.style.display = "none";
    		      }
    		    });

    		    addUserForm.addEventListener("submit", function (e) {
    		    	  e.preventDefault();

    		    	  const pwd = document.getElementById("userPassword").value;
    		    	  const confirmPwd = document.getElementById("confirmPassword").value;

    		    	  // Validate password match before proceeding
    		    	  if (pwd !== confirmPwd) {
    		    	    alert("Passwords do not match.");
    		    	    return; // Stop the submission
    		    	  }

    		    	  const formData = new FormData(addUserForm);

    		    	  fetch('${pageContext.request.contextPath}/admin/addUser', {
    		    	    method: 'POST',
    		    	    body: formData
    		    	  })
    		    	    .then(response => response.json())
    		    	    .then(data => {
    		    	      if (data.status === "success") {
    		    	        alert(data.message);
    		    	        addUserForm.reset();
    		    	        addUserModal.style.display = "none";
    		    	        location.reload();
    		    	      } else {
    		    	        throw new Error(data.message);
    		    	      }
    		    	    })
    		    	    .catch(error => {
    		    	      console.error("Error:", error);
    		    	      alert("Error adding user: " + error.message);
    		    	    });
    		    	});
    		  });
    	  
    	  document.addEventListener('DOMContentLoaded', function () {
    		  const deleteUserModal = document.getElementById("deleteUserModal");
    		  const deleteUserBtn = document.getElementById("deleteUserBtn");
    		  const closeDeleteUser = document.querySelector(".close-delete-user");
    		  const deleteUserForm = document.getElementById("deleteUserForm");

    		  if (deleteUserBtn) {
    		    deleteUserBtn.addEventListener("click", function (e) {
    		      e.preventDefault();
    		      deleteUserModal.style.display = "block";
    		    });
    		  }

    		  if (closeDeleteUser) {
    		    closeDeleteUser.addEventListener("click", function () {
    		      deleteUserModal.style.display = "none";
    		    });
    		  }

    		  window.addEventListener("click", function (event) {
    		    if (event.target === deleteUserModal) {
    		      deleteUserModal.style.display = "none";
    		    }
    		  });

    		  deleteUserForm.addEventListener("submit", function (e) {
    			  e.preventDefault();

    			  const confirmDelete = confirm("Are you sure you want to delete this user?");
    			  if (!confirmDelete) return;

    			  const formData = new FormData(deleteUserForm);

    		    fetch('${pageContext.request.contextPath}/admin/deleteUser', {
    		      method: 'POST',
    		      body: formData
    		    })
    		      .then(response => response.json())
    		      .then(data => {
    		        if (data.status === "success") {
    		          alert(data.message);
    		          deleteUserForm.reset();
    		          deleteUserModal.style.display = "none";
    		          location.reload();
    		        } else {
    		          throw new Error(data.message);
    		        }
    		      })
    		      .catch(error => {
    		        console.error("Error:", error);
    		        alert("Error deleting user: " + error.message);
    		      });
    		  });
    		});
    	  
    	  function openUpdateUserModal(id, fullName, email, contact, address, loyaltyPoints, password) {
    		  document.getElementById("updateUserId").value = id;
    		  document.getElementById("updateUserFullName").value = fullName;
    		  document.getElementById("updateUserEmail").value = email;
    		  document.getElementById("updateUserContact").value = contact;
    		  document.getElementById("updateUserAddress").value = address;
    		  document.getElementById("updateUserLoyaltyPoints").value = loyaltyPoints;
    		  document.getElementById("updateUserPassword").value = "";
    		  document.getElementById("confirmUpdatePassword").value = "";

    		  document.getElementById("updateUserModal").style.display = "block";
    		}

    		document.addEventListener("DOMContentLoaded", function () {
    		  const updateUserModal = document.getElementById("updateUserModal");
    		  const closeUpdateUser = document.querySelector(".close-update-user");
    		  const updateUserForm = document.getElementById("updateUserForm");

    		  if (closeUpdateUser) {
    		    closeUpdateUser.addEventListener("click", function () {
    		      updateUserModal.style.display = "none";
    		    });
    		  }

    		  window.addEventListener("click", function (event) {
    		    if (event.target === updateUserModal) {
    		      updateUserModal.style.display = "none";
    		    }
    		  });

    		  updateUserForm.addEventListener("submit", function (e) {
    		    e.preventDefault();

    		    const password = document.getElementById("updateUserPassword").value;
    		    const confirmPassword = document.getElementById("confirmUpdatePassword").value;

    		    if (password !== confirmPassword) {
    		      alert("Passwords do not match.");
    		      return;
    		    }

    		    const formData = new FormData(updateUserForm);

    		    fetch('${pageContext.request.contextPath}/admin/updateUser', {
    		      method: 'POST',
    		      body: formData
    		    })
    		    .then(response => response.json())
    		    .then(data => {
    		      if (data.status === "success") {
    		        alert(data.message);
    		        updateUserForm.reset();
    		        updateUserModal.style.display = "none";
    		        location.reload();
    		      } else {
    		        throw new Error(data.message);
    		      }
    		    })
    		    .catch(error => {
    		      console.error("Error:", error);
    		      alert("Error updating user: " + error.message);
    		    });
    		  });
    		});
    		
    		
    </script>
</html>

<%-- <!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/globals.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <style>
      /* Optional CSS for table styling */
      table {
        border-collapse: collapse;
        width: 100%;
        margin-bottom: 2rem;
      }
      th, td {
        border: 1px solid #ccc;
        padding: 0.5rem;
        text-align: left;
      }
      th {
        background-color: #f2f2f2;
      }
      .section-header {
        font-size: 1.5rem;
        margin: 1rem 0;
      }
      
      
  
    </style>
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
      </div>  
      <!-- Existing Dashboard Header and Navigation (if needed) -->

      
      <div class="text-wrapper-65">Hi, Ardent.</div>
      
      <!-- Station Details Table -->
      <div class="station-details">
        <h2 class="text-wrapper-63">Station Details</h2>
        <table id="stationTable">
          <thead>
            <tr>
              <th>Station ID</th>
              <th>Station Name</th>
              <th>Location</th>
              <th>Availability</th>
              <th>Price (₹) per kWh</th>
              <th>Ports</th>
              <th>Type</th>
              <!-- You may add Update/Delete columns if needed -->
            </tr>
          </thead>
          <tbody>
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
                  while(rs.next()){
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
                  out.println("<tr><td colspan='7'>Error loading station data: " + e.getMessage() + "</td></tr>");
              } finally {
                  try { if (rs != null) rs.close(); } catch(Exception e) {}
                  try { if (stmt != null) stmt.close(); } catch(Exception e) {}
                  try { if (conn != null) conn.close(); } catch(Exception e) {}
              }
            %>
          </tbody>
        </table>
      </div>
      
      <!-- User Details Table -->
      <div class="user-details">
        <h2 class="text-wrapper-64">User Details</h2>
        <table id="userTable">
          <thead>
            <tr>
              <th>User ID</th>
              <th>Full Name</th>
              <th>Email Address</th>
              <th>Contact</th>
              <th>Address</th>
              <th>Loyalty Points</th>
              <!-- You may add Update/Delete columns if needed -->
            </tr>
          </thead>
          <tbody>
            <%
              // Open a new connection for user data (alternatively, reuse the connection)
              Connection connUser = null;
              Statement stmtUser = null;
              ResultSet rsUser = null;
              try {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  connUser = DriverManager.getConnection(url, username, password);
                  stmtUser = connUser.createStatement();
                  rsUser = stmtUser.executeQuery("SELECT * FROM user");
                  while(rsUser.next()){
            %>
            <tr>
              <td><%= rsUser.getInt("user_id") %></td>
              <td><%= rsUser.getString("user_fullName") %></td>
              <td><%= rsUser.getString("user_email") %></td>
              <td><%= rsUser.getString("user_contact") %></td>
              <td><%= rsUser.getString("user_address") %></td>
              <td><%= rsUser.getInt("user_loyaltyPoints") %></td>
            </tr>
            <%
                  }
              } catch(Exception e) {
                  out.println("<tr><td colspan='6'>Error loading user data: " + e.getMessage() + "</td></tr>");
              } finally {
                  try { if (rsUser != null) rsUser.close(); } catch(Exception e) {}
                  try { if (stmtUser != null) stmtUser.close(); } catch(Exception e) {}
                  try { if (connUser != null) connUser.close(); } catch(Exception e) {}
              }
            %>
          </tbody>
        </table>
      </div>
      
      <!-- Optional: Add Station and Add User Buttons & Modals here -->
      <button id="addStationBtn" class="add-btn">
        <img src="resources/images/Vector.png" alt="Add" />
        <span>Add Station</span>
      </button>
      <button id="addUserBtn" class="add-btn">
        <img src="resources/images/Vector.png" alt="Add" />
        <span>Add User</span>
      </button>
      
      <!-- Existing Navigation -->
      <div class="navigation">
        <div class="navbar">
          <a href="${pageContext.request.contextPath}/index">Home</a>
          <a href="${pageContext.request.contextPath}/product">Charging Stations</a>
          <a href="${pageContext.request.contextPath}/contact">Contact</a>
          <a href="${pageContext.request.contextPath}/about">About Us</a>
          <a href="${pageContext.request.contextPath}/admin">Dashboard</a>
          <img class="chargehivelogo" src="resources/images/chargehiveLogo.png" />
          <img class="admin-icon" src="resources/images/Admin icon.png" />
        </div>
      </div>
    </div>
    
    <!-- Add Station Modal and associated script remain unchanged -->
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
      // Your existing modal and form submission JavaScript code here
      document.addEventListener('DOMContentLoaded', function() {
        const modal = document.getElementById("addStationModal");
        const addStationBtn = document.getElementById("addStationBtn");
        const closeBtn = document.querySelector(".close");
        const form = document.getElementById("addStationForm");
    
        modal.style.display = "none";
    
        if(addStationBtn) {
          addStationBtn.addEventListener('click', function(e) {
            e.preventDefault();
            modal.style.display = "block";
          });
        }
    
        if(closeBtn) {
          closeBtn.addEventListener('click', function() {
            modal.style.display = "none";
          });
        }
    
        window.addEventListener('click', function(event) {
          if (event.target === modal) {
            modal.style.display = "none";
          }
        });
    
        if(form) {
          form.addEventListener("submit", function(e) {
            e.preventDefault();
            const formData = new FormData(form);
    
            fetch('${pageContext.request.contextPath}/admin/addStation', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    alert(data.message);
                    form.reset();
                    modal.style.display = "none";
                    location.reload();
                } else {
                    throw new Error(data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error adding station: ' + error.message);
            });
          });
        }
      });
    </script>
  </body>
</html> --%>
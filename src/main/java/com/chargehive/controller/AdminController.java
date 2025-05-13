package com.chargehive.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.chargehive.util.PasswordUtil;
import com.chargehive.util.RedirectionUtil;
import com.chargehive.util.ValidationUtil;

@MultipartConfig
@WebServlet(asyncSupported = true, urlPatterns = {
	    "/admin", 
	    "/admin/addStation", 
	    "/admin/deleteStation", 
	    "/admin/updateStation",
	    "/admin/addUser",
	    "/admin/deleteUser",
	    "/admin/updateUser"
	})
public class AdminController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("userRole") : null;

        if (!"admin".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/user"); // redirect unauthorized users
            return;
        }

        request.getRequestDispatcher(RedirectionUtil.adminUrl).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    	String role = (String) request.getSession().getAttribute("userRole");
    	if (role == null || !"admin".equals(role)) {
    	    response.sendRedirect(request.getContextPath() + "/user");
    	    return;
    	}
        String path = request.getServletPath();
        
        if ("/admin/addStation".equals(path)) {
            handleAddStation(request, response);
        } else if (path.equals("/admin/deleteStation")) {
            deleteStation(request, response);
        } else if (path.equals("/admin/updateStation")) {
            updateStation(request, response);
        } else if (path.equals("/admin/addUser")) {
            handleAddUser(request, response);
        } else if (path.equals("/admin/deleteUser")) {
            deleteUser(request, response);
        } else if (path.equals("/admin/updateUser")) {
            updateUser(request, response);
        } else {
            doGet(request, response);
        }
    }
    
    private void handleAddStation(HttpServletRequest req, HttpServletResponse resp) 
        throws IOException {
        
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        
        try {
            // Get and validate all parameters first
            String stationName = getRequiredParameter(req, "stationName");
            String location = getRequiredParameter(req, "location");
            String availability = getRequiredParameter(req, "availability");
            String type = getRequiredParameter(req, "type");
            
            float price = Float.parseFloat(getRequiredParameter(req, "price"));
            int ports = Integer.parseInt(getRequiredParameter(req, "ports"));
            
            if (!ValidationUtil.isNameValid(stationName)) {
                throw new IllegalArgumentException("Station name must only contain alphabets and spaces.");
            }

            if (ValidationUtil.IsEmpty(location)) {
                throw new IllegalArgumentException("Location is required.");
            }

            if (!ValidationUtil.isPriceValid(String.valueOf(price))) {
                throw new IllegalArgumentException("Price must be between 300 and 800.");
            }

            if (!ValidationUtil.isPortsValid(String.valueOf(ports))) {
                throw new IllegalArgumentException("Ports must be between 1 and 12.");
            }

            if (!ValidationUtil.isPortsEnough(String.valueOf(ports))) {
                throw new IllegalArgumentException("Ports cannot exceed 20.");
            }

            if (!ValidationUtil.isTypeValid(type)) {
                throw new IllegalArgumentException("Type must be either 'Fast' or 'Slow'.");
            }
            
            // Database connection details
            String jdbcUrl = "jdbc:mysql://localhost:3307/chargehive";
            String dbUser = "root";
            String dbPassword = "";
            
            try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "INSERT INTO station (station_name, station_location, " +
                           "station_availability, station_price, station_ports, station_type) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";
                
                try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, stationName);
                    stmt.setString(2, location);
                    stmt.setString(3, availability);
                    stmt.setFloat(4, price);
                    stmt.setInt(5, ports);
                    stmt.setString(6, type);
                    
                    int affectedRows = stmt.executeUpdate();
                    
                    if (affectedRows == 0) {
                        throw new SQLException("Creating station failed, no rows affected.");
                    }
                    
                    StringBuilder jsonResponse = new StringBuilder();
                    jsonResponse.append("{\"status\":\"success\",\"message\":\"Station added successfully\"");
                    
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            long newId = generatedKeys.getLong(1);
                            jsonResponse.append(",\"newId\":").append(newId);
                        }
                    }
                    
                    jsonResponse.append("}");
                    out.print(jsonResponse.toString());
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"status\":\"error\",\"message\":\"Invalid number format for price or ports\"}");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"status\":\"error\",\"message\":\"Database error: " + 
                      e.getMessage().replace("\"", "\\\"") + "\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"status\":\"error\",\"message\":\"Error processing request: " + 
                      e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }
    
    
    private void updateStation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int stationId = Integer.parseInt(getRequiredParameter(request, "stationId"));
            String stationName = getRequiredParameter(request, "stationName");
            String location = getRequiredParameter(request, "location");
            String availability = getRequiredParameter(request, "availability");
            float price = Float.parseFloat(getRequiredParameter(request, "price"));
            int ports = Integer.parseInt(getRequiredParameter(request, "ports"));
            String type = getRequiredParameter(request, "type");

            if (!ValidationUtil.isNameValid(stationName)) {
                throw new IllegalArgumentException("Station name must only contain alphabets and spaces.");
            }

            if (ValidationUtil.IsEmpty(location)) {
                throw new IllegalArgumentException("Location is required.");
            }

            if (!ValidationUtil.isPriceValid(String.valueOf(price))) {
                throw new IllegalArgumentException("Price must be between 300 and 800.");
            }

            if (!ValidationUtil.isPortsValid(String.valueOf(ports))) {
                throw new IllegalArgumentException("Ports must be between 1 and 12.");
            }

            if (!ValidationUtil.isPortsEnough(String.valueOf(ports))) {
                throw new IllegalArgumentException("Ports cannot exceed 20.");
            }

            if (!ValidationUtil.isTypeValid(type)) {
                throw new IllegalArgumentException("Type must be either 'Fast' or 'Slow'.");
            }
           
            String url = "jdbc:mysql://localhost:3307/chargehive";
            String username = "root";
            String password = "";

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "UPDATE station SET station_name = ?, station_location = ?, station_availability = ?, station_price = ?, station_ports = ?, station_type = ? WHERE station_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, stationName);
                    stmt.setString(2, location);
                    stmt.setString(3, availability);
                    stmt.setFloat(4, price);
                    stmt.setInt(5, ports);
                    stmt.setString(6, type);
                    stmt.setInt(7, stationId);

                    int rowsUpdated = stmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        out.write("{\"status\":\"success\",\"message\":\"Station updated successfully.\"}");
                    } else {
                        out.write("{\"status\":\"error\",\"message\":\"Station ID not found or no change detected.\"}");
                    }
                }
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"status\":\"error\",\"message\":\"Invalid number format.\"}");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"status\":\"error\",\"message\":\"Database error: " + e.getMessage().replace("\"", "\\\"") + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"status\":\"error\",\"message\":\"Unexpected error: " + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }
    
    private void deleteStation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int stationId = Integer.parseInt(request.getParameter("stationId"));

        String url = "jdbc:mysql://localhost:3307/chargehive";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "DELETE FROM station WHERE station_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, stationId);

            int rowsAffected = stmt.executeUpdate();

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            if (rowsAffected > 0) {
                out.write("{\"status\": \"success\", \"message\": \"Station deleted successfully.\"}");
            } else {
                out.write("{\"status\": \"error\", \"message\": \"Station ID not found.\"}");
            }
        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
        }
    }
    
    private String getRequiredParameter(HttpServletRequest req, String paramName) 
        throws IllegalArgumentException {
        
        String value = req.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Missing or empty parameter: " + paramName);
        }
        return value.trim();
    }
    
    private void handleAddUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Get form parameters
            String fullName = getRequiredParameter(request, "userFullName");
            String email = getRequiredParameter(request, "userEmail");
            String contact = getRequiredParameter(request, "userContact");
            String address = getRequiredParameter(request, "userAddress");
            String password = getRequiredParameter(request, "userPassword");

            // Encrypt password
            String encryptedPassword = PasswordUtil.encrypt(email, password);

            // Default values
            String role = "user";
            int loyaltyPoints = 0;
            String membership = "bronze";
            
            if (!ValidationUtil.isNameValid(fullName)) {
                throw new IllegalArgumentException("Station name must only contain alphabets and spaces.");
            }

            if (ValidationUtil.IsEmpty(email)) {
                throw new IllegalArgumentException("Location is required.");
            }

            if (!ValidationUtil.isNum(String.valueOf(contact))) {
                throw new IllegalArgumentException("Price must be between 300 and 800.");
            }
            
            if (!ValidationUtil.IsEmpty(String.valueOf(address))) {
                throw new IllegalArgumentException("Ports cannot exceed 20.");
            }
            
            if (!ValidationUtil.isPasswordStrong(password)) {
                throw new IllegalArgumentException("Ports must be between 1 and 12.");
            }

            
            // DB connection
            String url = "jdbc:mysql://localhost:3307/chargehive";
            String username = "root";
            String dbPassword = "";

            try (Connection conn = DriverManager.getConnection(url, username, dbPassword)) {
                String sql = "INSERT INTO user (user_fullName, user_email, user_password, user_contact, user_address, user_role, user_loyaltyPoints, user_membership) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, fullName);
                    stmt.setString(2, email);
                    stmt.setString(3, encryptedPassword);
                    stmt.setString(4, contact);
                    stmt.setString(5, address);
                    stmt.setString(6, role);           // ✅ user_role in correct position
                    stmt.setInt(7, loyaltyPoints);
                    stmt.setString(8, membership);

                    int rowsInserted = stmt.executeUpdate();

                    if (rowsInserted > 0) {
                        out.write("{\"status\":\"success\",\"message\":\"User added successfully.\"}");
                    } else {
                        out.write("{\"status\":\"error\",\"message\":\"Failed to add user.\"}");
                    }
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"status\":\"error\",\"message\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }
    
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));

        String url = "jdbc:mysql://localhost:3307/chargehive";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "DELETE FROM user WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                int rowsAffected = stmt.executeUpdate();

                response.setContentType("application/json");
                PrintWriter out = response.getWriter();

                if (rowsAffected > 0) {
                    out.write("{\"status\": \"success\", \"message\": \"User deleted successfully.\"}");
                } else {
                    out.write("{\"status\": \"error\", \"message\": \"User ID not found.\"}");
                }
            }
        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
        }
    }
    
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int userId = Integer.parseInt(getRequiredParameter(request, "userId"));
            String fullName = getRequiredParameter(request, "userFullName");
            String email = getRequiredParameter(request, "userEmail");
            String password = getRequiredParameter(request, "userPassword");
            String contact = getRequiredParameter(request, "userContact");
            String address = getRequiredParameter(request, "userAddress");
            int loyaltyPoints = Integer.parseInt(getRequiredParameter(request, "userLoyaltyPoints"));

            if (!ValidationUtil.isPasswordStrong(password)) {
                out.write("{\"status\":\"error\",\"message\":\"Password must be at least 6 characters long and contain at least 1 uppercase letter, 1 number, and 1 special character (@, $, !, %, *, ?, &).\"}");
                return;
            }
            
            // Encrypt password
            String encryptedPassword = PasswordUtil.encrypt(email, password);

            // DB connection
            String jdbcUrl = "jdbc:mysql://localhost:3307/chargehive";
            String dbUser = "root";
            String dbPassword = "";

            try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                String sql = "UPDATE user SET user_fullName = ?, user_email = ?, user_password = ?, user_contact = ?, user_address = ?, user_loyaltyPoints = ? WHERE user_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, fullName);
                    stmt.setString(2, email);
                    stmt.setString(3, encryptedPassword);
                    stmt.setString(4, contact);
                    stmt.setString(5, address);
                    stmt.setInt(6, loyaltyPoints);
                    stmt.setInt(7, userId);

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        out.write("{\"status\":\"success\",\"message\":\"User updated successfully.\"}");
                    } else {
                        out.write("{\"status\":\"error\",\"message\":\"No user found or no changes made.\"}");
                    }
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"status\":\"error\",\"message\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }
}
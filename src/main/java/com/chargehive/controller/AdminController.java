package com.chargehive.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.chargehive.util.RedirectionUtil;

@MultipartConfig
@WebServlet(asyncSupported = true, urlPatterns = {
	    "/admin", 
	    "/admin/addStation", 
	    "/admin/deleteStation", 
	    "/admin/updateStation"
	})
public class AdminController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        request.getRequestDispatcher(RedirectionUtil.adminUrl).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        String path = request.getServletPath();
        
        if ("/admin/addStation".equals(path)) {
            handleAddStation(request, response);
        } else if (path.equals("/admin/deleteStation")) {
            deleteStation(request, response);
        } else if (path.equals("/admin/updateStation")) {
            updateStation(request, response);
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
}
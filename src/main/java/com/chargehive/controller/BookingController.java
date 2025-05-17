package com.chargehive.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles booking requests from users.
 * Expects a logged-in user (with userId in session).
 * 
 * URL Mapping: /bookStation
 * Method: POST
 * 
 * Required parameter:
 * - stationId (int): ID of the station to book
 * 
 * Session attribute:
 * - userId (int): ID of the currently logged-in user
 * 
 * Inserts a new booking with today's date.
 * Responds with JSON status.
 * 
 * @author Umanga Amatya
 */

@WebServlet("/bookStation")
public class BookingController extends HttpServlet {
	
	// Handles POST request to book a station.
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int stationId = Integer.parseInt(request.getParameter("stationId"));
            int userId = (int) request.getSession().getAttribute("userId"); // assuming login session
            
            // DB connection setup
            String url = "jdbc:mysql://localhost:3307/chargehive";
            String dbUser = "root";
            String dbPassword = "";

            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
            	// Insert booking record
                String sql = "INSERT INTO booking (booking_date, user_id, station_id) VALUES (CURDATE(), ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, userId);
                    stmt.setInt(2, stationId);

                    int result = stmt.executeUpdate();
                    if (result > 0) {
                        out.print("{\"status\":\"success\"}");
                    } else {
                        out.print("{\"status\":\"error\", \"message\":\"Could not book station.\"}");
                    }
                }
            }
        } catch (Exception e) {
        	// Catch all errors and return error response in JSON
            out.print("{\"status\":\"error\", \"message\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }
}

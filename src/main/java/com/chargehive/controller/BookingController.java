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

@WebServlet("/bookStation")
public class BookingController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int stationId = Integer.parseInt(request.getParameter("stationId"));
            int userId = (int) request.getSession().getAttribute("userId"); // assuming login session

            String url = "jdbc:mysql://localhost:3307/chargehive";
            String dbUser = "root";
            String dbPassword = "";

            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
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
            out.print("{\"status\":\"error\", \"message\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }
}

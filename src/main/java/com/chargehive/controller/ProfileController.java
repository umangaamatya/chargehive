package com.chargehive.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(asyncSupported = true, urlPatterns = {"/profile", "/updateProfile"})
public class ProfileController extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/chargehive";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String attribute = request.getParameter("attribute"); // "email", "contact", "address"
        String value = request.getParameter("value");

        // Map input attribute to actual column name in DB
        String columnName;
        switch (attribute) {
            case "email":
                columnName = "user_email";
                break;
            case "contact":
                columnName = "user_contact";
                break;
            case "address":
                columnName = "user_address";
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid attribute");
                return;
        }

        String query = "UPDATE user SET " + columnName + " = ? WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, value);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Check logs for real error
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
            return;
        }

        // Redirect safely back to profile page
        response.sendRedirect(request.getContextPath() + "/profile");
    }
}
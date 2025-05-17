package com.chargehive.controller;

import com.chargehive.util.ImageUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

/**
 * Handles user profile-related operations:
 * - Viewing the profile
 * - Updating email, contact, and address
 * - Uploading a profile picture
 * 
 * URL Mappings: /profile, /updateProfile
 * 
 * @author Umanga Amatya
 */

@WebServlet(asyncSupported = true, urlPatterns = {"/profile", "/updateProfile"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,    // 2MB
                 maxFileSize = 1024 * 1024 * 10,         // 10MB
                 maxRequestSize = 1024 * 1024 * 50)      // 50MB
public class ProfileController extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3307/chargehive";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    private final ImageUtil imageUtil = new ImageUtil();
    
    /**
     * Displays the profile page.
     *
     * @param request  the incoming HTTP request
     * @param response the HTTP response to send back
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
    }
    
    /**
     * Handles profile update POST requests.
     * Supports updating user info (email/contact/address) and profile picture.
     *
     * @param request  must include 'attribute' and 'value' or multipart 'image'
     * @param response result of the update operation
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String attribute = request.getParameter("attribute");
        
        // Handle profile picture upload
        if ("profile_pic".equals(attribute)) {
            handleProfilePictureUpload(request, response, userId);
            return;
        }

        // Handle email/contact/address updates
        String value = request.getParameter("value");
        String columnName;

        switch (attribute) {
            case "email": columnName = "user_email"; break;
            case "contact": columnName = "user_contact"; break;
            case "address": columnName = "user_address"; break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid attribute");
                return;
        }
        
        // Update user info in the database
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement("UPDATE user SET " + columnName + " = ? WHERE user_id = ?")) {

            stmt.setString(1, value);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

            response.sendRedirect(request.getContextPath() + "/profile");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    /**
     * Handles profile picture upload and updates the user's profile picture in the DB.
     *
     * @param req     the HTTP request containing the image part
     * @param resp    the HTTP response
     * @param userId  the current user's ID
     */
    private void handleProfilePictureUpload(HttpServletRequest req, HttpServletResponse resp, int userId)
            throws IOException, ServletException {

        Part image = req.getPart("image");

        if (image == null || image.getSize() == 0) {
            req.setAttribute("error", "No file selected for upload.");
            req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
            return;
        }

        String imageName = imageUtil.getImageNameFromPart(image);
        boolean uploaded = imageUtil.uploadImage(image, req.getServletContext().getRealPath("/"), "profile_pics");

        if (uploaded) {
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                 PreparedStatement stmt = conn.prepareStatement("UPDATE user SET user_profile_pic = ? WHERE user_id = ?")) {

                stmt.setString(1, imageName);
                stmt.setInt(2, userId);
                stmt.executeUpdate();

                resp.sendRedirect(req.getContextPath() + "/profile");

            } catch (SQLException e) {
                e.printStackTrace();
                req.setAttribute("error", "Database error during profile picture update.");
                req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Failed to upload profile picture.");
            req.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(req, resp);
        }
    }
}
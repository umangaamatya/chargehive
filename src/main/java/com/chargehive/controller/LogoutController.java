package com.chargehive.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Handles user logout by invalidating the session.
 * Redirects to the home page after successful logout.
 * 
 * URL Mapping: /logout
 * 
 * @author Umanga Amatya
 */

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
	
	/**
     * Handles GET request for logging out a user.
     * Invalidates the current session if it exists and redirects to the home page.
     *
     * @param request  HttpServletRequest object containing the client request
     * @param response HttpServletResponse object used to send the redirect
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs during redirect
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// Retrieve session if it exists, do not create a new one
        HttpSession session = request.getSession(false); 
        
        // Invalidate session to log the user out
        if (session != null) {
            session.invalidate(); // Kill the session
        }
        
        // Redirect to home page after logout
        response.sendRedirect(request.getContextPath() + "/index");
    }
}
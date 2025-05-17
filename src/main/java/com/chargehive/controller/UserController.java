package com.chargehive.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles requests to the user dashboard.
 * Forwards the user to user.jsp located in /WEB-INF/pages.
 * 
 * URL Mapping: /user
 * 
 * @author Umanga Amatya
 */

@WebServlet(asyncSupported = true, urlPatterns = {"/user"})
public class UserController extends HttpServlet {
	
	/**
     * Handles GET requests to the user dashboard page.
     *
     * @param request  the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object for sending the response
     * @throws ServletException if the servlet fails to forward the request
     * @throws IOException if an I/O error occurs during the request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    	
    	// Forward the request to the user view
        request.getRequestDispatcher("/WEB-INF/pages/user.jsp").forward(request, response);
    }
}

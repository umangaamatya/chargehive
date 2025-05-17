package com.chargehive.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles GET requests for the Contact page.
 * Forwards to contact.jsp under /WEB-INF/pages.
 * 
 * URL Mapping: /contact
 * 
 * @author Umanga Amatya
 */

@WebServlet(asyncSupported = true, urlPatterns = {"/contact"})
public class ContactController extends HttpServlet {
	
	/**
     * Handles GET requests to display the contact page.
     *
     * @param request  HttpServletRequest containing client request
     * @param response HttpServletResponse for sending the response
     * @throws ServletException if forwarding fails
     * @throws IOException if an input/output error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    	
    	// Forward the request to the contact.jsp view
        request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
    }
}

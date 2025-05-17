package com.chargehive.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles requests for the home (landing) page.
 * 
 * URL Mappings: /index, /
 * Forwards requests to index.jsp view.
 * 
 * @author Umanga Amatya
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/index", "/" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Handles GET requests and forwards to the home page view.
     * 
     * @param request  the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object for sending the response
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException if an input/output error occurs
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/pages/index.jsp").forward(request, response);
	}
	
	/**
     * Redirects POST requests to GET handler.
     * Useful if a form submits to "/" or "/index".
     * 
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException if an input/output error occurs
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

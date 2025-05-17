package com.chargehive.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles GET requests for the Product page.
 * Forwards the request to the product.jsp view.
 * 
 * URL Mapping: /product
 * 
 * @author Umanga Amatya
 */

@WebServlet(asyncSupported = true, urlPatterns = {"/product"})
public class ProductController extends HttpServlet {
	
	/**
     * Handles GET request to display the product page.
     *
     * @param request  HttpServletRequest object containing client request details
     * @param response HttpServletResponse object for sending the response
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException if an input/output error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    	
    	// Forward the request to the product JSP view
        request.getRequestDispatcher("/WEB-INF/pages/product.jsp").forward(request, response);
    }
}

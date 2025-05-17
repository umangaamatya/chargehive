package com.chargehive.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles requests for the About Us page.
 * Maps to "/about" and forwards to aboutus.jsp.
 * 
 * @author Umanga Amatya
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/about"})
public class AboutController extends HttpServlet {

    /**
     * Forwards GET request to the About Us JSP view.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        request.getRequestDispatcher("/WEB-INF/pages/aboutus.jsp").forward(request, response);
    }
}
package com.chargehive.controller;

import com.chargehive.model.UserModel;
import com.chargehive.service.LoginService;
import com.chargehive.util.CookiesUtil;
import com.chargehive.util.RedirectionUtil;
import com.chargehive.util.SessionUtil;
import com.chargehive.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Handles user login logic via form submission.
 * Validates credentials and sets session/cookies upon success.
 * 
 * URL Mapping: /login
 * 
 * Expected POST parameters:
 * - user_email
 * - user_password
 * 
 * On success: redirects to /admin or /user based on role.
 * On failure: reloads login page with error message.
 * 
 * @author Umanga Amatya
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ValidationUtil validationUtil;
    private RedirectionUtil redirectionUtil;
    private LoginService loginService;
    
    /**
     * Initializes utility and service instances when the servlet starts.
     */
    @Override
    public void init() throws ServletException {
        this.validationUtil = new ValidationUtil();
        this.redirectionUtil = new RedirectionUtil();
        this.loginService = new LoginService();
    }
    
    /**
     * Displays the login form (GET request).
     * 
     * @param req  incoming request from the client
     * @param resp response to render the login view
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(RedirectionUtil.loginUrl).forward(req, resp);
    }
    
    /**
     * Handles login form submission (POST request).
     * Validates credentials and sets session + cookies.
     * 
     * @param req  must include "user_email" and "user_password"
     * @param resp redirects to appropriate dashboard or shows error
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("user_email");
        String password = req.getParameter("user_password");

        if (email != null) email = email.trim();
        if (password != null) password = password.trim();

        if (!validationUtil.IsEmpty(email) && !validationUtil.IsEmpty(password)) {
            UserModel user = loginService.authenticateUser(email, password);

            if (user != null) {
                // Set session attributes
                SessionUtil.setAttribute(req, "userId", Integer.valueOf(user.getUserId()));
                SessionUtil.setAttribute(req, "userEmail", user.getUserEmail());
                SessionUtil.setAttribute(req, "userRole", user.getUserRole());

                // Set cookies via CookiesUtil
                CookiesUtil.addCookie(resp, "userEmail", user.getUserEmail(), 30 * 60);
                CookiesUtil.addCookie(resp, "userRole", user.getUserRole(), 30 * 60);

                // Redirect based on role
                if ("admin".equalsIgnoreCase(user.getUserRole())) {
                    resp.sendRedirect(req.getContextPath() + "/admin");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/user");
                }
            } else {
                handleLoginFailure(req, resp);
            }
        } else {
        	// Missing email/password input
            redirectionUtil.setMsgAndRedirect(
                req,
                resp,
                "error",
                "Please fill all the fields!",
                RedirectionUtil.loginUrl
            );
        }
    }
    
    /**
     * Handles invalid login attempts by forwarding back to the login page with an error.
     * 
     * @param req  request object to set error attribute
     * @param resp forwards response back to login page
     */
    private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String errorMessage = "Invalid credentials. Please try again!";
        req.setAttribute("error", errorMessage);
        req.getRequestDispatcher(RedirectionUtil.loginUrl).forward(req, resp);
    }
}
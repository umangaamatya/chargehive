package com.chargehive.controller;

import java.io.IOException;

import com.chargehive.model.UserModel;
import com.chargehive.service.RegisterService;
import com.chargehive.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = { "/registration"})

public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final RegisterService registerService = new RegisterService();

    // Default values
    private static final String DEFAULT_ROLE = "user";
    private static final int DEFAULT_LOYALTY_POINTS = 0;
    private static final String DEFAULT_MEMBERSHIP = "bronze";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserModel userModel = extractUserModel(req);
            Boolean isAdded = registerService.addUser(userModel);
        	
            if (isAdded == null) {
                handleError(req, resp, "Our server is under maintenance. Please try again later!");
            } else if(!isAdded) {
                handleError(req, resp, "Could not register your account. Please try again later!");
            } else {
                // Upon successful registration
                //resp.sendRedirect(req.getContextPath() + "/login.jsp");
            	req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            handleError(req, resp, "Invalid contact number format");
        } catch (Exception e) {
            handleError(req, resp, e.getMessage());
            e.printStackTrace();
        }
    }
    
    private UserModel extractUserModel(HttpServletRequest req) throws Exception {
        String userName = req.getParameter("user_name");
        String userContact = req.getParameter("user_contact");
        String userAddress = req.getParameter("user_address");
        String userEmail = req.getParameter("user_email").trim();
        String userPassword = req.getParameter("user_password").trim();
        String retypePassword = req.getParameter("confirm_password");
        
        // Validate required fields
        if (userName == null || userName.trim().isEmpty()) {
            throw new Exception("Full name is required");
        }
        
        if (userAddress == null || userAddress.trim().isEmpty()) {
            throw new Exception("Address is required");
        }
        
        if (userEmail == null || userEmail.trim().isEmpty()) {
            throw new Exception("Email is required");
        }
        
        if (userPassword == null || userPassword.trim().isEmpty()) {
            throw new Exception("Password is required");
        }
        
        if (!userPassword.equals(retypePassword)) {
            throw new Exception("Passwords do not match");
        }
        
        // Encrypt password
        userPassword = PasswordUtil.encrypt(userEmail, userPassword);
        
        // Create user with default values
        UserModel newUser = new UserModel(
            userName, 
            userEmail, 
            userPassword, 
            userContact, 
            userAddress,
            DEFAULT_ROLE, 
            DEFAULT_LOYALTY_POINTS, 
            DEFAULT_MEMBERSHIP
        );
        
        return newUser;
        
//        boolean success = new RegisterService().addUser(newUser);
        
    }
    
    
    
    
    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }
}
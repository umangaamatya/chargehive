package com.chargehive.controller;

import java.io.IOException;

import com.chargehive.model.UserModel;
import com.chargehive.service.RegisterService;
import com.chargehive.util.PasswordUtil;
import com.chargehive.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles user registration logic including validation, password encryption,
 * and saving user data to the database.
 * 
 * URL Mapping: /registration
 * 
 * @author Umanga Amatya
 */

@WebServlet(asyncSupported = true, urlPatterns = { "/registration"})

public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final RegisterService registerService = new RegisterService();

    // Default values
    private static final String DEFAULT_ROLE = "user";
    private static final int DEFAULT_LOYALTY_POINTS = 0;
    private static final String DEFAULT_MEMBERSHIP = "bronze";
    private static final String DEFAULT_PROFILE_PIC = "";
    
    /**
     * Loads the registration form page.
     *
     * @param req  incoming request
     * @param resp response used to forward to registration.jsp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/registration.jsp").forward(req, resp);
    }

    /**
     * Processes the registration form submission.
     * Validates user input, encrypts password, and saves user data.
     *
     * @param req  the registration form submission
     * @param resp response to redirect or show error
     */
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
            	// Forward to login page after successful registration
            	req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            handleError(req, resp, "Invalid contact number format");
        } catch (Exception e) {
            handleError(req, resp, e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Extracts and validates user input fields from the registration form.
     * 
     * @param req HttpServletRequest containing form parameters
     * @return UserModel with sanitized and encrypted data
     * @throws Exception if validation fails
     */
    private UserModel extractUserModel(HttpServletRequest req) throws Exception {
        String userName = req.getParameter("user_name");
        String userContact = req.getParameter("user_contact");
        String userAddress = req.getParameter("user_address");
        String userEmail = req.getParameter("user_email").trim();
        String userPassword = req.getParameter("user_password").trim();
        String retypePassword = req.getParameter("confirm_password");

        // Full Name validation
        if (ValidationUtil.IsEmpty(userName)) {
            throw new Exception("Full name is required");
        }
        if (!ValidationUtil.isNameValid(userName)) {
            throw new Exception("Name must only contain alphabets and spaces");
        }

        // Contact validation
        if (ValidationUtil.IsEmpty(userContact)) {
            throw new Exception("Contact number is required");
        }
        if (!ValidationUtil.isNum(userContact)) {
            throw new Exception("Contact must only contain numbers");
        }

        // Address validation
        if (ValidationUtil.IsEmpty(userAddress)) {
            throw new Exception("Address is required");
        }
        if (ValidationUtil.isNum(userAddress)) {
            throw new Exception("Address must contain valid characters, not just numbers");
        }

        // Email validation
        if (ValidationUtil.IsEmpty(userEmail)) {
            throw new Exception("Email is required");
        }

        // Password validations
     // Password validations
        if (ValidationUtil.IsEmpty(userPassword)) {
            throw new Exception("Password is required");
        }

        if (!userPassword.equals(retypePassword)) {
            throw new Exception("Passwords do not match");
        }

        if (!ValidationUtil.isPasswordStrong(userPassword)) {
            throw new Exception("Password must be at least 6 characters long and contain at least 1 uppercase letter, 1 number, and 1 special character (@, $, !, %, *, ?, &)");
        }

        

        // Encrypt password
        userPassword = PasswordUtil.encrypt(userEmail, userPassword);

        return new UserModel(
            userName,
            userEmail,
            userPassword,
            userContact,
            userAddress,
            DEFAULT_ROLE,
            DEFAULT_LOYALTY_POINTS,
            DEFAULT_MEMBERSHIP,
            DEFAULT_PROFILE_PIC
        );
    }
    
    
    /**
     * Displays the registration form again with an error message.
     *
     * @param req     request object to attach the error
     * @param resp    response used to forward back to the form
     * @param message error message to display
     */
    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
    }
}
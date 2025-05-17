package com.chargehive.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.chargehive.config.DbConfig;
import com.chargehive.model.UserModel;
import com.chargehive.util.PasswordUtil;

/**
 * Handles user authentication logic:
 * - Validates login credentials
 * - Connects to database
 * - Decrypts and verifies passwords
 * 
 * @author Umanga Amatya
 */
public class LoginService {
    private static final Logger logger = Logger.getLogger(LoginService.class.getName());
    private final DbConfig dbConfig;
    
    /**
     * Constructs the LoginService and initializes DB configuration.
     */
    public LoginService() {
        this.dbConfig = new DbConfig();
    }
    
    /**
     * Verifies if the given user exists and password is correct.
     *
     * @param userModel UserModel object containing email and password
     * @return true if login successful, false if credentials invalid, null on DB error
     */
    public Boolean loginUser(UserModel userModel) {
        if (userModel == null || userModel.getUserEmail() == null) {
            logger.warning("Invalid user model or email");
            return false;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Get new connection for each operation
            conn = dbConfig.getDbConnection();
            
            String query = "SELECT user_email, user_password FROM user WHERE user_email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, userModel.getUserEmail().trim());
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return validatePassword(rs, userModel);
            }
            logger.info("No user found with email: " + userModel.getUserEmail());
            return false;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error during login", e);
            return null;
        } finally {
            // Close resources in reverse order
            closeQuietly(rs);
            closeQuietly(stmt);
            closeQuietly(conn);
        }
    }

    /**
     * Closes any AutoCloseable resource quietly.
     *
     * @param resource ResultSet, Statement, or Connection to be closed
     */
    private void closeQuietly(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error closing resource", e);
            }
        }
    }
    
    /**
     * Validates user password against the encrypted value in DB.
     *
     * @param result    ResultSet containing DB-stored user_email and user_password
     * @param userModel UserModel with entered email and password
     * @return true if password matches after decryption
     * @throws SQLException if DB column extraction fails
     */
    private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
        String dbEmail = result.getString("user_email");
        String dbPassword = result.getString("user_password");
        String inputPassword = userModel.getUserPassword();

        if (!dbEmail.equalsIgnoreCase(userModel.getUserEmail())) {
            logger.warning("Email mismatch");
            return false;
        }

        String decryptedPassword = PasswordUtil.decrypt(dbEmail, dbPassword);
        if (decryptedPassword == null) {
            logger.warning("Decryption failed for user: " + dbEmail);
            return false;
        }

        return securePasswordMatch(decryptedPassword, inputPassword);
    }
    
    /**
     * Securely compares the decrypted password with user input.
     *
     * @param decryptedPassword plain text password from DB
     * @param inputPassword     user-entered password
     * @return true if passwords match
     */
    private boolean securePasswordMatch(String decryptedPassword, String inputPassword) {
        // Your existing secure comparison logic
        return Objects.equals(decryptedPassword, inputPassword);
    }
    
    /**
     * Authenticates the user and returns their profile data if successful.
     *
     * @param email    user's email
     * @param password raw password to verify
     * @return UserModel object if authenticated, null otherwise
     */
    public UserModel authenticateUser(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        String query = "SELECT user_id, user_fullName, user_email, user_password, user_role FROM user WHERE user_email = ?";
        try (Connection conn = dbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, email.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String dbEmail = rs.getString("user_email");
                    String dbPassword = rs.getString("user_password");
                    String decryptedPassword = PasswordUtil.decrypt(dbEmail, dbPassword);

                    if (decryptedPassword != null && decryptedPassword.equals(password)) {
                        // Authenticated successfully
                        UserModel user = new UserModel();
                        user.setUserId(rs.getInt("user_id"));
                        user.setUserName(rs.getString("user_fullName"));
                        user.setUserEmail(dbEmail);
                        user.setUserRole(rs.getString("user_role")); // e.g., "admin" or "user"
                        return user;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log better in production
        }

        return null; // Authentication failed
    }
}


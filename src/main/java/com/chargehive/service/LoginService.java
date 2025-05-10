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
 * Service class for handling login operations. Connects to the database,
 * verifies user credentials, and returns login status.
 */
public class LoginService {
    private static final Logger logger = Logger.getLogger(LoginService.class.getName());
    private final DbConfig dbConfig;

    public LoginService() {
        this.dbConfig = new DbConfig();
    }

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

    private void closeQuietly(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error closing resource", e);
            }
        }
    }

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
    

    private boolean securePasswordMatch(String decryptedPassword, String inputPassword) {
        // Your existing secure comparison logic
        return Objects.equals(decryptedPassword, inputPassword);
    }
    
    
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


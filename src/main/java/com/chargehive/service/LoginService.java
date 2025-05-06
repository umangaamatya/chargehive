//package com.chargehive.service;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import com.chargehive.config.DbConfig;
//import com.chargehive.model.UserModel;
//import com.chargehive.util.PasswordUtil;
//
///**
// * Service class for handling login operations. Connects to the database,
// * verifies user credentials, and returns login status.
// */
//public class LoginService {
//
//	private Connection dbConn;
//	private boolean isConnectionError = false;
//
//	/**
//	 * Constructor initializes the database connection. Sets the connection error
//	 * flag if the connection fails.
//	 */
//	public LoginService() {
//		try {
//			dbConn = DbConfig.getDbConnection();
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//			isConnectionError = true;
//		}
//	}
//
//	/**
//	 * Validates the user credentials against the database records.
//	 *
//	 * @param userModel the UserModel object containing user credentials
//	 * @return true if the user credentials are valid, false otherwise; null if a
//	 *         connection error occurs
//	 */
//	public Boolean loginUser(UserModel userModel) {
//		System.out.println(">>> loginUser() called with email: " + userModel.getUserEmail());
//		if (isConnectionError) {
//			System.out.println("Connection Error!");
//			return null;
//		}
//
//		String query = "SELECT user_email, user_password FROM user WHERE user_email = ?";
//		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
//			stmt.setString(1, userModel.getUserEmail());
//			ResultSet result = stmt.executeQuery();
//
//			if (result.next()) {
//				return validatePassword(result, userModel);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		return false;
//	}
//
//	/**
//	 * Validates the password retrieved from the database.
//	 *
//	 * @param result       the ResultSet containing the email and password from
//	 *                     the database
//	 * @param userModel the UserModel object containing user credentials
//	 * @return true if the passwords match, false otherwise
//	 * @throws SQLException if a database access error occurs
//	 */
//	private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
//		String dbEmail = result.getString("user_email");
//		String dbPassword = result.getString("user_password");
//		
//		System.out.println("Decrypted password: " + PasswordUtil.decrypt(dbPassword, dbEmail));
//		System.out.println("Entered password: " + userModel.getUserPassword());
//		return dbEmail.equals(userModel.getUserEmail())
//				&& PasswordUtil.decrypt(dbPassword, dbEmail).equals(userModel.getUserPassword());
//	}
//}
//


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
}
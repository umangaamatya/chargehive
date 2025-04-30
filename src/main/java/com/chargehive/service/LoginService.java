package com.chargehive.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chargehive.config.DbConfig;
import com.chargehive.model.UserModel;
import com.chargehive.util.PasswordUtil;

/**
 * Service class for handling login operations. Connects to the database,
 * verifies user credentials, and returns login status.
 */
public class LoginService {

	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Constructor initializes the database connection. Sets the connection error
	 * flag if the connection fails.
	 */
	public LoginService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
	}

	/**
	 * Validates the user credentials against the database records.
	 *
	 * @param userModel the UserModel object containing user credentials
	 * @return true if the user credentials are valid, false otherwise; null if a
	 *         connection error occurs
	 */
	public Boolean loginUser(UserModel userModel) {
		System.out.println(">>> loginUser() called with email: " + userModel.getUserEmail());
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT user_email, user_password FROM user WHERE user_email = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, userModel.getUserEmail());
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				return validatePassword(result, userModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return false;
	}

	/**
	 * Validates the password retrieved from the database.
	 *
	 * @param result       the ResultSet containing the email and password from
	 *                     the database
	 * @param userModel the UserModel object containing user credentials
	 * @return true if the passwords match, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
		String dbEmail = result.getString("user_email");
		String dbPassword = result.getString("user_password");
		
		System.out.println("Decrypted password: " + PasswordUtil.decrypt(dbPassword, dbEmail));
		System.out.println("Entered password: " + userModel.getUserPassword());
		return dbEmail.equals(userModel.getUserEmail())
				&& PasswordUtil.decrypt(dbPassword, dbEmail).equals(userModel.getUserPassword());
	}
}


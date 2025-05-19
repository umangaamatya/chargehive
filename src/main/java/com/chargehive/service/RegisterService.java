package com.chargehive.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.chargehive.config.DbConfig;
import com.chargehive.model.UserModel;


/**
 * Handles the logic for registering new users.
 * Connects to the database and inserts user data into the `user` table.
 * 
 * @author Umanga Amatya
 */

public class RegisterService {
	
	/**
     * Adds a new user to the database using the provided UserModel.
     *
     * @param userModel the user data to be inserted (name, email, password, etc.)
     * @return true if insertion is successful, false otherwise
     */
	public Boolean addUser(UserModel userModel) {
	    System.out.println("Attempting to insert user: " + userModel.toString()); // Debug
	    
	    String sql = "INSERT INTO user (user_fullName, user_email, user_password, user_contact, user_address, user_role, user_loyaltyPoints, user_membership, user_profile_pic) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DbConfig.getDbConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        // Set parameters
	        stmt.setString(1, userModel.getUserName());
	        stmt.setString(2, userModel.getUserEmail());
	        stmt.setString(3, userModel.getUserPassword());
	        stmt.setString(4, userModel.getUserContact());
	        stmt.setString(5, userModel.getUserAddress());
	        stmt.setString(6, userModel.getUserRole());
	        stmt.setInt(7, userModel.getUserLoyaltyPoints());
	        stmt.setString(8, userModel.getUserMembership());
	        stmt.setString(9, userModel.getUserProfilePic());

	        // Debug - print the actual SQL that will execute
	        System.out.println("Executing: " + stmt.toString()); 
	        
	        int rows = stmt.executeUpdate();
	        System.out.println(rows + " row(s) affected"); // Debug
	        
	        return rows > 0;
	    } catch (Exception e) {
	        System.err.println("INSERT ERROR:");
	        e.printStackTrace();
	        return false;
	    }
	}
}
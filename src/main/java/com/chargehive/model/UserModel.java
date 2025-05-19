package com.chargehive.model;

/**
 * @author Umanga Amatya
 */

public class UserModel {
	private int userId;
	private String userName;
	private String userContact;
	private String userAddress;
	private String userEmail;
	private String userPassword;
	private String userRole;
	private int userLoyaltyPoints;
	private String userMembership;
	private String userProfilePic;
	
	
	
	public UserModel() {
		
	}
	
	public UserModel(String userEmail, String pasword) {
		this.userEmail = userEmail;
		this.userPassword = pasword;
	}
	
	public UserModel(String userName, String userEmail, String userPassword, String userContact, String userAddress, String userRole, int userLoyaltyPoints, String userMembership, String userProfilePic) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userContact = userContact;
		this.userAddress = userAddress;
		this.userRole = userRole;
		this.userLoyaltyPoints = userLoyaltyPoints;
		this.userMembership = userMembership;
		this.userProfilePic = userProfilePic;
	}
	
	public UserModel(String userName) {
		this.userName = userName;
	}

	public int getUserId() {
		return userId;
	} 

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserContact() {
		return userContact;
	}

	public void setUserContact(String userContact) {
		this.userContact = userContact;
	}
	
	public String getUserAddress() {
		return userAddress;
	}
	
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	public int getUserLoyaltyPoints() {
		return userLoyaltyPoints;
	}
	
	public void setUserLoyaltyPoints(int userLoyaltyPoints) {
		this.userLoyaltyPoints = userLoyaltyPoints;
	}
	
	public String getUserMembership() {
		return userMembership;
	}
	
	public void setUserMembership(String userMembership) {
		this.userMembership = userMembership;
	}
	
	public String getUserProfilePic() {
		return userProfilePic;
	}
	
	public void setUserProfilePic(String userProfilePic) {
		this.userProfilePic = userProfilePic;
	}

	
	

}

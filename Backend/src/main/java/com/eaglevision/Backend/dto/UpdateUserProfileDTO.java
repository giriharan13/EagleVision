package com.eaglevision.Backend.dto;

public class UpdateUserProfileDTO {
	
	private String userName;
	
	private String phoneNumber;
	
	
	public UpdateUserProfileDTO() {
		
	}
	
	public UpdateUserProfileDTO(String userName,String phoneNumber) {
		this.userName = userName;
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	

}

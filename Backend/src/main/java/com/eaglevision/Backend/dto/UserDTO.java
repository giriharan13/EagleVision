package com.eaglevision.Backend.dto;

import java.util.Date;

import com.eaglevision.Backend.model.Buyer;
import com.eaglevision.Backend.model.User;

public class UserDTO {
	
	private Integer userId;

	private String userName;

	private String phoneNumber;

	private Date dateOfBirth;
	
	private String profilePictureImageData;
	
	private String profilePicureImageType;
	
	private String profilePictureImageName;
	
	private Boolean isVendor;
	
	public UserDTO() {
		super();
	}

	public UserDTO(Integer userId, String userName, String phoneNumber, Date dateOfBirth) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
	}
	
	public UserDTO(User user) {
		super();
		this.userId=user.getUserId();
		this.userName=user.getUserName();
		this.phoneNumber=user.getPhoneNumber();
		this.dateOfBirth=user.getDateOfBirth();
		this.profilePictureImageData = user.getProfilePictureDataB64();
		this.profilePicureImageType = user.getProfilePictureType();
		this.profilePictureImageName = user.getProfilePictureName();
		if(user instanceof Buyer) isVendor=false;
		else isVendor=true;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getProfilePictureImageData() {
		return profilePictureImageData;
	}

	public void setProfilePictureImageData(String profilePictureImageData) {
		this.profilePictureImageData = profilePictureImageData;
	}

	public String getProfilePicureImageType() {
		return profilePicureImageType;
	}

	public void setProfilePicureImageType(String profilePicureImageType) {
		this.profilePicureImageType = profilePicureImageType;
	}

	public String getProfilePictureImageName() {
		return profilePictureImageName;
	}

	public void setProfilePictureImageName(String profilePictureImageName) {
		this.profilePictureImageName = profilePictureImageName;
	}

	public Boolean getIsVendor() {
		return isVendor;
	}

	public void setIsVendor(Boolean isVendor) {
		this.isVendor = isVendor;
	}
	

}

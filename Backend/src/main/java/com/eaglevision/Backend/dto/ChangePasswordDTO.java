package com.eaglevision.Backend.dto;

public class ChangePasswordDTO {
	
	private String oldPassword;
	
	private String newPassword;
	
	private String confirmNewPassword;

	public ChangePasswordDTO() {
		super();
	}

	public ChangePasswordDTO(String oldPassword, String newPassword, String confirmNewPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	
	
	
	

}

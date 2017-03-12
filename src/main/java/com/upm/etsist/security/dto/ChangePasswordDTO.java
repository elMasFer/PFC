package com.upm.etsist.security.dto;

public class ChangePasswordDTO {
	private String newPassword;
	private String oldPassword;
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public ChangePasswordDTO(String newPassword, String oldPassword) {
		super();
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
	}
	public ChangePasswordDTO() {
		super();
	}
	@Override
	public String toString() {
		return "ChangePasswordDTO [newPassword=" + newPassword + ", oldPassword=" + oldPassword + "]";
	}


	
}

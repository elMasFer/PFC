package com.upm.etsist.dto;

import com.upm.etsist.security.model.User;

public class AjaxRequestUDTO {

	String row;
	
	User user;


	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AjaxRequestUDTO() {
		super();
	}

	public AjaxRequestUDTO(String row, User user) {
		super();
		this.row = row;
		this.user = user;
	}

	@Override
	public String toString() {
		return "AjaxRequestUDTO [row=" + row + ", user=" + user + "]";
	}


	
}

package com.example.jwtdemo.bean;

public enum NotificationsEnum {
	
	PLAYER_SIGNUP("PLAYER_SIGNUP");
	
	private String type;

	private NotificationsEnum() {
		
	}

	private NotificationsEnum(String type) {
		this.type = type;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}

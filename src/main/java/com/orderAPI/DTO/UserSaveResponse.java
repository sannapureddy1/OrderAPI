package com.orderAPI.DTO;

public class UserSaveResponse {
	private String message;
	private Long id;
	private String userName;
	private String email;
	
	public UserSaveResponse() {
		super();
	}
	
	public UserSaveResponse(String message, Long id, String userName, String email) {
		super();
		this.message = message;
		this.id = id;
		this.userName = userName;
		this.email = email;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return userName;
	}
	public void setUsername(String username) {
		this.userName = username;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

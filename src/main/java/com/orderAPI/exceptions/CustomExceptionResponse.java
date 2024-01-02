package com.orderAPI.exceptions;

import org.springframework.http.HttpStatus;

public class CustomExceptionResponse {
	
	private final String message;
	private final HttpStatus status;
	
	public CustomExceptionResponse(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
}

package com.orderAPI.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private final HttpStatus status;
	
	public CustomException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
	
	public CustomException(String message, Throwable cause, HttpStatus status) {
		super(message, cause);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
}

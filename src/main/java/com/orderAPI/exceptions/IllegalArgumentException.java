package com.orderAPI.exceptions;

import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends CustomException{

	private static final long serialVersionUID = 1L;

	public IllegalArgumentException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}

}

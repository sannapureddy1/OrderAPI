package com.orderAPI.exceptions;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends CustomException{

	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}

}

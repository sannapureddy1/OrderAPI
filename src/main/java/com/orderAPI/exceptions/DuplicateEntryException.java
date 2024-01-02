package com.orderAPI.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateEntryException extends CustomException{

	private static final long serialVersionUID = 1L;

	public DuplicateEntryException(String message) {
		super(message, HttpStatus.CONFLICT);
	}
	
}

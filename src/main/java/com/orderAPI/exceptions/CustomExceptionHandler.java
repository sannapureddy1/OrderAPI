package com.orderAPI.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(value = { CustomException.class, MissingServletRequestParameterException.class })
	public ResponseEntity<Object> handler(CustomException ex){
		CustomExceptionResponse response = new CustomExceptionResponse(ex.getMessage(), ex.getStatus());
		return new ResponseEntity<>(response, ex.getStatus());
	}
	
}

package com.annotation.services.exceptions;

public class UserDoesNotExistsException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public UserDoesNotExistsException(String msg) {
		super(msg);
	}
	
	

}

package com.annotation.services.exceptions;

public class UserDoesNotExistException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public UserDoesNotExistException(String msg) {
		super(msg);
	}
	
	

}

package com.annotation.services.exceptions;

public class AlreadyExistsException extends Exception{

	
	private static final long serialVersionUID = 1L;
	
	public AlreadyExistsException() {
	}
	
	public AlreadyExistsException(String msg) {
		super(msg);
	}
}

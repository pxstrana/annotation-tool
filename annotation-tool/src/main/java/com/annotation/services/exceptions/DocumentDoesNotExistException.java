package com.annotation.services.exceptions;

public class DocumentDoesNotExistException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public DocumentDoesNotExistException(String msg) {
		super(msg);
	}
}

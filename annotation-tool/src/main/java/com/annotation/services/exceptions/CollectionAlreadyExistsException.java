package com.annotation.services.exceptions;

public class CollectionAlreadyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public CollectionAlreadyExistsException() {
		super();
	}
	
	public CollectionAlreadyExistsException(String msg) {
		super(msg);
		
	}
}

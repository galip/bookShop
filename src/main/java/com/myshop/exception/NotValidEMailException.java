package com.myshop.exception;

public class NotValidEMailException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public NotValidEMailException(String message) {
		super(message);
	}
}
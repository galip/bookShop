package com.myshop.exception;

public class NameCanNotBeEmptyException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public NameCanNotBeEmptyException(String message) {
		super(message);
	}
}
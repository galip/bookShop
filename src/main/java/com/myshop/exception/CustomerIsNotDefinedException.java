package com.myshop.exception;

public class CustomerIsNotDefinedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public CustomerIsNotDefinedException(String message) {
		super(message);
	}
}
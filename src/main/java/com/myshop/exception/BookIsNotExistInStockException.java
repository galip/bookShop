package com.myshop.exception;

public class BookIsNotExistInStockException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public BookIsNotExistInStockException(String message) {
		super(message);
	}
}
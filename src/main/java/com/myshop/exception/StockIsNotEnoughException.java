package com.myshop.exception;

public class StockIsNotEnoughException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public StockIsNotEnoughException(String message) {
		super(message);
	}
}
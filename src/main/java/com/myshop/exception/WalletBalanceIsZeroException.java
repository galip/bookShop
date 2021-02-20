package com.myshop.exception;

public class WalletBalanceIsZeroException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public WalletBalanceIsZeroException(String message) {
		super(message);
	}
}
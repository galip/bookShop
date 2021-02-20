package com.myshop.exception;

public class WalletCreditIsNotEnoughException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public WalletCreditIsNotEnoughException(String message) {
		super(message);
	}
}
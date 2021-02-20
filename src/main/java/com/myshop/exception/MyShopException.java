package com.myshop.exception;

/**
 * @author galip
 *
 * 2021-01-01
 */

/*
 * throwing Exception in not the best practise.
 */
public class MyShopException extends Exception {

	public MyShopException (String message) {
		super(message);
	}
}
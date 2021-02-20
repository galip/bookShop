package com.myshop.model;

import lombok.Data;

@Data
public class Stock {

	private long id;
	private long bookId;
	private int quantity;
}
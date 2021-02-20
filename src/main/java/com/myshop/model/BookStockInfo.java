package com.myshop.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BookStockInfo {

	private long bookId;
	private int quantity;
	private int orderQuantity; 
	private BigDecimal price;
	private String bookName;
	private long ISBN;
}
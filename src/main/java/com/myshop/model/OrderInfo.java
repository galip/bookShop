package com.myshop.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderInfo {
	private long id;
	private long customerId;
	private BigDecimal customerWalletBalance;
	private long bookId;
	private String bookTitle;
	private int orderQuantity;
	private BigDecimal bookPrice;
	private long ISBN;
	private int stockQuantity;
	private BigDecimal totalOrderFee;
}
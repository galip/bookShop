package com.myshop.model;

import lombok.Data;

@Data
public class OrderDetail {
	private long id;
	private long orderId;
	private long bookId;
	private int quantity;
	private String createdDate;
}
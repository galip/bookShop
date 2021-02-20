package com.myshop.model;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Data
public class Order {
	private long id;
	private long customerId;
	private BigDecimal totalFee;
	private String createdDate;
	
	@Autowired
	private List<OrderDetail> orderDetails;
	
}
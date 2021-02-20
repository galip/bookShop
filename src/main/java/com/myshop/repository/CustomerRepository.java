package com.myshop.repository;

import java.math.BigDecimal;
import java.util.List;

import com.myshop.exception.MyShopException;
import com.myshop.model.Customer;

public interface CustomerRepository {

	public List<Customer> findAll() throws MyShopException;
	
	public List<Customer> getCustomerById(long customerId) throws MyShopException;
	
	public void insertCustomer(Customer customer) throws MyShopException;
	
	public void updateCustomerWalletBalance(long customerId, BigDecimal currentWalletBalance) throws MyShopException;
}
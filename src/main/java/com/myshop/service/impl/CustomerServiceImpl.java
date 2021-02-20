package com.myshop.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myshop.exception.MyShopException;
import com.myshop.model.Customer;
import com.myshop.repository.impl.CustomerRepositoryImpl;
import com.myshop.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepositoryImpl customerRepository;

	public List<Customer> getAllCustomer() throws MyShopException {
		return customerRepository.findAll();
	}

	public List<Customer> getCustomerById(long customerId) throws MyShopException {
		return customerRepository.getCustomerById(customerId);
	}

	public void insertCustomer(Customer customer) throws MyShopException {
		customerRepository.insertCustomer(customer);
	}

	public void updateCustomerWalletBalance(long customerId, BigDecimal currentWalletBalance)
			throws MyShopException {
		customerRepository.updateCustomerWalletBalance(customerId, currentWalletBalance);
	}
}

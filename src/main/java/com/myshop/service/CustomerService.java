package com.myshop.service;

import java.math.BigDecimal;
import java.util.List;

import com.myshop.exception.MyShopException;
import com.myshop.model.Customer;

public interface CustomerService {

	/**
	 * @return all customer lists
	 * @throws MyShopException
	 */
	public List<Customer> getAllCustomer() throws MyShopException;

	/**
	 * @param customerId
	 * @return customer
	 * @throws MyShopException
	 */
	public List<Customer> getCustomerById(long customerId) throws MyShopException;

	/**
	 * @param customer inserts a customer object with the auto incremented id in
	 *                 database.
	 * @throws MyShopException
	 */
	public void insertCustomer(Customer customer) throws MyShopException;

	/**
	 * @param customerId
	 * @param currentWalletBalance, customer wallet balance after subtracting order
	 *                              fee
	 * @throws MyShopException
	 */
	public void updateCustomerWalletBalance(long customerId, BigDecimal currentWalletBalance)
			throws MyShopException;;

}

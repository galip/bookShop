package com.myshop.validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myshop.constant.Constants;
import com.myshop.exception.CustomerIsNotDefinedException;
import com.myshop.exception.MyShopException;
import com.myshop.exception.NameCanNotBeEmptyException;
import com.myshop.exception.NotValidEMailException;
import com.myshop.exception.WalletBalanceIsZeroException;
import com.myshop.model.Customer;
import com.myshop.service.CustomerService;

@Service
public class CustomerValidator {
	
	@Autowired
	CustomerService customerService;
	
	public void validateIfExists(Long customerId) throws CustomerIsNotDefinedException, MyShopException {
		List<Customer> customers = customerService.getCustomerById(customerId);
		if (customers.size() == 0) {
			 throw new CustomerIsNotDefinedException(Constants.CUSTOMER_NOT_EXIST);
		}
	}
	
	public void validateWalletBalanceIsZero(Customer customer) throws WalletBalanceIsZeroException {
		BigDecimal customerWalletCredit = customer.getWalletBalance();
		 if (customerWalletCredit.compareTo(BigDecimal.ZERO) == 0) {
			 throw new WalletBalanceIsZeroException(Constants.WALLET_BALANCE_ZERO);
		 }
	}
	
	public void validateRegistration(Customer customer) throws NameCanNotBeEmptyException, WalletBalanceIsZeroException, NotValidEMailException {
		if(customer.getName().isBlank() || customer.getEmail().isBlank() || customer.getWalletBalance() == null) {
	    	 throw new NameCanNotBeEmptyException(Constants.CUSTOMER_FIELDS_NULL);
	    }
		
		if(customer.getWalletBalance().compareTo(BigDecimal.ZERO) <= 0) {
			throw new WalletBalanceIsZeroException(Constants.WALLET_BALANCE_ZERO);
		}
		
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(customer.getEmail());
		if(!matcher.matches()) {
			throw new NotValidEMailException(Constants.EMAIL_NOT_VALID);
		}
		
	}
}
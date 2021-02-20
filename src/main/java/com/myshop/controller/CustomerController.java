package com.myshop.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.constant.Constants;
import com.myshop.exception.MyShopException;
import com.myshop.model.Customer;
import com.myshop.responsemodel.ResponseModel;
import com.myshop.service.CustomerService;
import com.myshop.validator.CustomerValidator;

/**
 * @author galip
 *
 */

@RestController
@RequestMapping("/customer")
@Component
public class CustomerController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerValidator customerValidator;
	
	@GetMapping("/list")
	public @ResponseBody ResponseModel<List<Customer>> getAllCustomer() throws MyShopException {
		
		try {
			List<Customer> customers = customerService.getAllCustomer();
			return new ResponseModel<>(customers);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new ResponseModel<>(e);
		}
	}
	
	@GetMapping("/{customerId}")
	public @ResponseBody ResponseModel<List<Customer>> getCustomerById(@PathVariable("customerId") long customerId) throws MyShopException {
		
		try {
			customerValidator.validateIfExists(customerId);
			List<Customer> customer = customerService.getCustomerById(customerId);
			return new ResponseModel<>(customer);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new ResponseModel<>(e);
		}
	}
	
	@PostMapping("/register")
	public @ResponseBody ResponseModel<String> addCustomer(@RequestBody Customer customer) throws MyShopException {
		String response = Constants.REGISTER_SUCCEED;
		try {
			customerValidator.validateRegistration(customer);
			customerService.insertCustomer(customer);
			return new ResponseModel<String>(response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new ResponseModel<>(e);
		}
	}

}

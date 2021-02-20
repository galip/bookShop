package com.myshop.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.exception.MyShopException;
import com.myshop.model.BookStockInfo;
import com.myshop.responsemodel.ResponseModel;
import com.myshop.service.StockService;

/**
 * @author galip
 *
 */

@RestController
@RequestMapping("/stock")
public class StockController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);
	
	@Autowired
	StockService stockService;
	
	@GetMapping("/list")
	public @ResponseBody ResponseModel<List<BookStockInfo>> getAllCustomer() throws MyShopException {
		
		try {
			List<BookStockInfo> stocks = stockService.getAllStock();
			return new ResponseModel<>(stocks);
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
			return new ResponseModel<>(e);
		}
	}

}

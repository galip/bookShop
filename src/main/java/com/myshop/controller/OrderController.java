package com.myshop.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.constant.Constants;
import com.myshop.exception.OrderNotFoundException;
import com.myshop.model.BasketItem;
import com.myshop.model.Customer;
import com.myshop.model.Order;
import com.myshop.model.OrderDetail;
import com.myshop.model.OrderInfo;
import com.myshop.model.Stock;
import com.myshop.responsemodel.ResponseModel;
import com.myshop.service.CustomerService;
import com.myshop.service.OrderService;
import com.myshop.service.StockService;
import com.myshop.validator.CustomerValidator;
import com.myshop.validator.OrderValidator;

/**
 * @author galip
 *
 */

@RestController
@RequestMapping("/order")
public class OrderController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderService orderService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerValidator customerValidator;
	
	@Autowired
	OrderValidator orderValidator;
	
	@GetMapping("/detail/{orderId}")
	public @ResponseBody ResponseModel<List<Order>> getOrderDetails(@PathVariable("orderId") long orderId) {
		
		try {
			List<Order> orderDetails = Optional.ofNullable(orderService.getOrderDetails(orderId))
												.orElseThrow(() -> new OrderNotFoundException(Constants.ORDER_NOT_FOUND));

			if(orderDetails.size() == 0) throw new OrderNotFoundException(Constants.ORDER_NOT_FOUND);
			return new ResponseModel<>(orderDetails);
		} catch (Exception e) {
			return new ResponseModel<>(e);
		}
	}


	@PostMapping("/place/{customerId}")
	public @ResponseBody ResponseModel<String> placeOrder(@PathVariable("customerId") long customerId, @RequestBody List<BasketItem> basketItems) {
		try {
			customerValidator.validateIfExists(customerId);
			
			List<Customer> customers = customerService.getCustomerById(customerId);
			customerValidator.validateWalletBalanceIsZero(customers.get(0));
			
			BigDecimal customerWalletCredit = customers.get(0).getWalletBalance();
			
			List<OrderInfo> orderInfoList = orderService.getOrderInfoListByBasketItems(basketItems, customerId);
			orderValidator.validateIfOrderAvailableInStock(orderInfoList);
			
			BigDecimal totalFee = BigDecimal.ZERO;
			totalFee = orderService.calculateBasketTotalFee(orderInfoList);
			orderValidator.validateCustomerWalletBalanceEnoughForOrderTotalFee(customerWalletCredit, totalFee);
			
			Order order = new Order();
			long sequence = orderService.getOrderSequence();
			order.setId(sequence);
			order.setCustomerId(customerId); // get it from session, token
			order.setTotalFee(totalFee);
			
			List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
			List<Stock> stocks = new ArrayList<Stock>();
			for(OrderInfo item : orderInfoList) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrderId(sequence);
				orderDetail.setBookId(item.getBookId());
				orderDetail.setQuantity(item.getOrderQuantity());
				orderDetails.add(orderDetail);
				
				Stock stock = new Stock();
				stock.setBookId(item.getBookId());
				stock.setQuantity(item.getStockQuantity() - item.getOrderQuantity());
				stocks.add(stock);
			}
			
			orderService.batchProcess(order, orderDetails, customers.get(0), stocks);
			return new ResponseModel<String>(Constants.ORDER_SUCCEED);
		} catch (Exception e) {
			LOGGER.error("/order/place/" + customerId + " Exception: " + e.getMessage());
			return new ResponseModel<>(e);
		}
	}
}
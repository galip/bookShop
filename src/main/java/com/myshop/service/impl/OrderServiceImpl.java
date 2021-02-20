package com.myshop.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.exception.MyShopException;
import com.myshop.model.BasketItem;
import com.myshop.model.Customer;
import com.myshop.model.Order;
import com.myshop.model.OrderDetail;
import com.myshop.model.OrderInfo;
import com.myshop.model.Stock;
import com.myshop.repository.OrderRepository;
import com.myshop.service.CustomerService;
import com.myshop.service.OrderService;
import com.myshop.service.StockService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	CustomerService customerService;

	@Autowired
	StockService stockService;

	@Override
	public BigDecimal calculateBasketTotalFee(List<OrderInfo> orderInfoList) {
		BigDecimal totalFee = BigDecimal.ZERO;
		orderInfoList = orderInfoList.stream().filter(p -> p.getOrderQuantity() <= p.getStockQuantity())
				.collect(Collectors.toList());
		for (OrderInfo o : orderInfoList) {
			totalFee = totalFee.add(o.getBookPrice().multiply(BigDecimal.valueOf(o.getOrderQuantity())));
		}
		return totalFee;
	}

	public List<OrderInfo> getOrderInfoListByBasketItems(List<BasketItem> basketItems, long customerId)
			throws MyShopException {
		return orderRepository.getOrderInfoListByBasketItems(basketItems, customerId);
	}

	@Transactional
	public void placeOrder(Order order) throws MyShopException {
		orderRepository.placeOrder(order);
	}

	public List<OrderInfo> getBooksWithZeroStockInBasket(List<OrderInfo> orderInfoList) throws MyShopException {

		return orderInfoList.stream().filter(p -> p.getStockQuantity() == 0).collect(Collectors.toList());

	}

	public Long getOrderSequence() {
		return orderRepository.getOrderSequence();
	}

	public void placeOrderDetail(List<OrderDetail> orderDetails) throws MyShopException {
		orderRepository.placeOrderDetail(orderDetails);
	}

	@Transactional
	public void batchProcess(Order order, List<OrderDetail> orderDetails, Customer customer, List<Stock> stocks)
			throws MyShopException {
		orderRepository.placeOrder(order);
		orderRepository.placeOrderDetail(orderDetails);
		customerService.updateCustomerWalletBalance(customer.getId(),
				customer.getWalletBalance().subtract(order.getTotalFee()));
		stockService.updateStock(stocks);
	}

	public List<OrderInfo> getBooksQuantityInOrderMoreThanStock(List<OrderInfo> orderInfoList)
			throws MyShopException {
		return orderInfoList.stream().filter(p -> p.getOrderQuantity() > p.getStockQuantity())
				.collect(Collectors.toList());
	}

	public List<Order> getOrderDetails(long orderId) {
		return orderRepository.getOrderDetails(orderId);
	}

}
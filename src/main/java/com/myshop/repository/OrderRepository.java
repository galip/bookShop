package com.myshop.repository;

import java.util.List;

import com.myshop.exception.MyShopException;
import com.myshop.model.BasketItem;
import com.myshop.model.Order;
import com.myshop.model.OrderDetail;
import com.myshop.model.OrderInfo;

public interface OrderRepository {

	public void placeOrder(Order order) throws MyShopException;
	
	public Long getOrderSequence();
	
	public void placeOrderDetail(List<OrderDetail> orderDetails) throws MyShopException;
	
	public List<OrderInfo> getOrderInfoListByBasketItems(List<BasketItem> basketItems, long customerId) throws MyShopException;
	
	public List<Order> getOrderDetails(long orderId);
}
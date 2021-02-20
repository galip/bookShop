package com.myshop.service;

import java.math.BigDecimal;
import java.util.List;

import com.myshop.exception.MyShopException;
import com.myshop.model.BasketItem;
import com.myshop.model.Customer;
import com.myshop.model.Order;
import com.myshop.model.OrderDetail;
import com.myshop.model.OrderInfo;
import com.myshop.model.Stock;

public interface OrderService {

	/**
	 * @param orderInfoList
	 * @return total fee of orders.
	 * @throws MyShopException
	 */
	public BigDecimal calculateBasketTotalFee(List<OrderInfo> orderInfoList) throws MyShopException;

	/**
	 * @param basketItems
	 * @param customerId
	 * @return list of order info with then given basket items.
	 * @throws MyShopException
	 */
	public List<OrderInfo> getOrderInfoListByBasketItems(List<BasketItem> basketItems, long customerId)
			throws MyShopException;

	/**
	 * @param orderInfoList
	 * @return lists of the zero count books in stock.
	 * @throws MyShopException
	 */
	public List<OrderInfo> getBooksWithZeroStockInBasket(List<OrderInfo> orderInfoList) throws MyShopException;

	/**
	 * @param orderInfoList
	 * @return lists of if book quantities in order is more than stock.
	 * @throws MyShopException
	 */
	public List<OrderInfo> getBooksQuantityInOrderMoreThanStock(List<OrderInfo> orderInfoList)
			throws MyShopException;

	/**
	 * @return will query order details
	 */
	public List<Order> getOrderDetails(long orderId);

	/**
	 * @param order
	 * @throws MyShopException
	 */
	public void placeOrder(Order order) throws MyShopException;

	/**
	 * @param orderDetail
	 * @throws MyShopException
	 */
	public void placeOrderDetail(List<OrderDetail> orderDetail) throws MyShopException;

	/**
	 * @return sequence to use it for place an order and order details.
	 */
	public Long getOrderSequence();

	/**
	 * @param order
	 * @param orderDetails
	 * @param customer
	 * @param stocks       Transactional batch process.
	 * @throws MyShopException
	 */
	public void batchProcess(Order order, List<OrderDetail> orderDetails, Customer customer, List<Stock> stocks)
			throws MyShopException;

}
package com.myshop.validator;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myshop.constant.Constants;
import com.myshop.exception.BookIsNotExistInStockException;
import com.myshop.exception.MyShopException;
import com.myshop.exception.StockIsNotEnoughException;
import com.myshop.exception.WalletCreditIsNotEnoughException;
import com.myshop.model.OrderInfo;
import com.myshop.service.OrderService;

@Service
public class OrderValidator {
	
	@Autowired
	OrderService orderService;

	public void validateIfOrderAvailableInStock(List<OrderInfo> orderInfoList) throws MyShopException, BookIsNotExistInStockException, StockIsNotEnoughException {
		List<OrderInfo> zeroStockBooksInOrder =  orderService.getBooksWithZeroStockInBasket(orderInfoList);
		if(zeroStockBooksInOrder.size() > 0) {
			throw new BookIsNotExistInStockException(Constants.BOOKS_NOT_AVAILABLE_IN_STOCK);
		}
		
		List<OrderInfo> booksInOrderMoreThanStockList = orderService.getBooksQuantityInOrderMoreThanStock(orderInfoList);
		if(booksInOrderMoreThanStockList.size() > 0) {
			throw new StockIsNotEnoughException(Constants.STOCK_NOT_ENOUGH);
		}
	}
	
	public void validateCustomerWalletBalanceEnoughForOrderTotalFee(BigDecimal customerWalletCredit, BigDecimal totalFee) throws WalletCreditIsNotEnoughException {
		if (customerWalletCredit.compareTo(totalFee) < 0) 
			throw new WalletCreditIsNotEnoughException(Constants.WALLET_BALANCE_NOT_ENOUGH);
	}
}
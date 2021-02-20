package com.myshop.service;

import java.util.List;

import com.myshop.exception.MyShopException;
import com.myshop.model.BasketItem;
import com.myshop.model.BookStockInfo;
import com.myshop.model.Stock;

public interface StockService {
	/**
	 * @return lists of book stock info if quantity > 0 in stock table.
	 * @throws MyShopException
	 */
	public List<BookStockInfo> getAllStock() throws MyShopException;

	/**
	 * @param basketItems
	 * @return lists of book stock info with the given basket items.
	 * @throws MyShopException
	 */
	public List<BookStockInfo> getBookStockInfoByBasketItems(List<BasketItem> basketItems) throws MyShopException;

	/**
	 * @param stocks updates the stock with the given book ids.
	 * @throws MyShopException
	 */
	public void updateStock(List<Stock> stocks) throws MyShopException;

}
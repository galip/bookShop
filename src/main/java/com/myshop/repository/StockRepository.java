package com.myshop.repository;

import java.util.List;

import com.myshop.exception.MyShopException;
import com.myshop.model.BookStockInfo;
import com.myshop.model.Stock;

public interface StockRepository {

	public List<BookStockInfo> getAllStock();
	
	public List<BookStockInfo> getBookStockInfoByBookIds(List<Long> bookIds) throws MyShopException;
	
	public void updateStock(List<Stock> stocks) throws MyShopException;
	}
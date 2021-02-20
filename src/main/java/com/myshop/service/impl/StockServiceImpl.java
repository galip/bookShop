package com.myshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myshop.exception.MyShopException;
import com.myshop.model.BasketItem;
import com.myshop.model.BookStockInfo;
import com.myshop.model.Stock;
import com.myshop.repository.StockRepository;
import com.myshop.service.StockService;

@Service
public class StockServiceImpl implements StockService {

	@Autowired
	StockRepository stockRepository;

	public List<BookStockInfo> getAllStock() {
		return stockRepository.getAllStock();
	}

	public List<BookStockInfo> getBookStockInfoByBasketItems(List<BasketItem> basketItems) throws MyShopException {
		Map<Long, Integer> bookIdQuantityMapInBasket = basketItems.stream()
				.collect(Collectors.groupingBy(BasketItem::getBookId, Collectors.summingInt(BasketItem::getQuantity)));
		List<Long> bookIds = new ArrayList<Long>();
		// gets the id list to go database only one 1 time.
		for (Map.Entry<Long, Integer> map : bookIdQuantityMapInBasket.entrySet()) {
			bookIds.add(map.getKey());
		}
		List<BookStockInfo> bookStockInfoList = stockRepository.getBookStockInfoByBookIds(bookIds);

		// set orderStock
		for (Map.Entry<Long, Integer> map : bookIdQuantityMapInBasket.entrySet()) {
			bookStockInfoList.stream().filter(p -> p.getBookId() == map.getKey())
					.forEach(f -> f.setOrderQuantity(map.getValue()));
		}
		return bookStockInfoList;
	}

	public void updateStock(List<Stock> stocks) throws MyShopException {
		stockRepository.updateStock(stocks);
	}

	public static <T> Predicate<T> distinctById(Function<? super T, Object> keyExtractor) {
		Set<Object> setObject = ConcurrentHashMap.newKeySet();
		return t -> setObject.add(keyExtractor.apply(t));
	}
}
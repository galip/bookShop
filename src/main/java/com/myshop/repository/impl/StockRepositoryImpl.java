package com.myshop.repository.impl;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.myshop.exception.MyShopException;
import com.myshop.model.BookStockInfo;
import com.myshop.model.Stock;
import com.myshop.repository.StockRepository;

@Repository
public class StockRepositoryImpl implements StockRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final String GET_ALL_STOCK = "select s.book_id, s.quantity, b.price, b.title, b.isbn from stock s, book b where s.book_id = b.id and s.quantity > 0";
	private final String GET_ALL_STOCK_BY_IDS = "select s.book_id, s.quantity, b.price, b.title, b.isbn from stock s, book b where s.book_id = b.id and s.book_id in (:ids)";
	private final String UPDATE_STOCK_QUANTITY = "UPDATE stock s set s.quantity = ? where s.book_id = ?";

	public List<BookStockInfo> getAllStock() {
		return jdbcTemplate.query(GET_ALL_STOCK, rowMapper);
	}
	
	private RowMapper<BookStockInfo> rowMapper = (ResultSet rs, int rowNum) -> {
		BookStockInfo bookStockInfo = new BookStockInfo();

		bookStockInfo.setBookId(rs.getLong(1));
		bookStockInfo.setQuantity(rs.getInt(2));
		bookStockInfo.setPrice(rs.getBigDecimal(3));
		bookStockInfo.setBookName(rs.getString(4));
		bookStockInfo.setISBN(rs.getLong(5));
		return bookStockInfo;
	};
	
	public List<BookStockInfo> getBookStockInfoByBookIds(List<Long> bookIds) throws MyShopException {
		MapSqlParameterSource idParams = new MapSqlParameterSource();
	    idParams.addValue("ids", bookIds);
		
		List<BookStockInfo> bookStockInfoList = namedParameterJdbcTemplate.query(GET_ALL_STOCK_BY_IDS, idParams, rowMapper);
		
		return bookStockInfoList;
	}
	
	public void updateStock(List<Stock> stocks) throws MyShopException {
	    
	    for (Stock s : stocks)
		jdbcTemplate.update(UPDATE_STOCK_QUANTITY, s.getQuantity(), s.getBookId());
	}
}
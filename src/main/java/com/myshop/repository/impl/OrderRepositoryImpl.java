package com.myshop.repository.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.myshop.exception.MyShopException;
import com.myshop.model.BasketItem;
import com.myshop.model.Order;
import com.myshop.model.OrderDetail;
import com.myshop.model.OrderInfo;
import com.myshop.repository.OrderRepository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final String INSERT_PLACE_ORDER = "INSERT INTO orders (id,customer_id,total_fee) values (?,?,?)";
	private final String INSERT_PLACE_ORDER_DETAILS = "INSERT INTO order_details (order_id,book_id,quantity) values (?,?,?)";

	private final String SELECT_ORDER_DETAILS = "select o.id,o.customer_id,o.total_fee,o.created_time,d.id,d.order_id,d.book_id,d.quantity,d.created_time "
			+ "from orders o, order_details d where o.id = d.order_id and o.id = :orderId ";

	private final String SELECT_ORDER_INFO = "select b.id, b.title, b.price, b.isbn, s.quantity, c.id, c.wallet_balance from stock s, book b, customer c "
			+ "where s.book_id = b.id and s.book_id = ? and c.id = ?";
	private final String SELECT_ORDER_SEQ = "select SEQ_ORDER.NEXTVAL from dual";

	@Override
	public void placeOrder(Order order) throws MyShopException {
		jdbcTemplate.update(INSERT_PLACE_ORDER, order.getId(), order.getCustomerId(), order.getTotalFee());
	}

	public Long getOrderSequence() {
		Long seq;
		seq = jdbcTemplate.queryForObject(SELECT_ORDER_SEQ, new Object[] {}, Long.class);
		return seq;
	}

	public void placeOrderDetail(List<OrderDetail> orderDetail) throws MyShopException {
		for (OrderDetail o : orderDetail)
			jdbcTemplate.update(INSERT_PLACE_ORDER_DETAILS, o.getOrderId(), o.getBookId(), o.getQuantity());
	}

	public List<OrderInfo> getOrderInfoListByBasketItems(List<BasketItem> basketItems, long customerId)
			throws MyShopException {
		Map<Long, Integer> bookIdQuantityMapInBasket = basketItems.stream()
				.collect(Collectors.groupingBy(BasketItem::getBookId, Collectors.summingInt(BasketItem::getQuantity)));
		List<Long> bookIds = new ArrayList<Long>();

		MapSqlParameterSource idParam = new MapSqlParameterSource();
		MapSqlParameterSource customerIdParam = new MapSqlParameterSource();
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		for (Map.Entry<Long, Integer> map : bookIdQuantityMapInBasket.entrySet()) {
			bookIds.add(map.getKey());
			idParam.addValue("bookId", map.getKey());
			customerIdParam.addValue("customerId", customerId);

			OrderInfo orderInfo = new OrderInfo();
			try {
				orderInfo = (OrderInfo) jdbcTemplate.queryForObject(SELECT_ORDER_INFO,
						new Object[] { map.getKey(), customerId }, ordersInfoRowMapper);
			} catch (Exception e) {
				throw new MyShopException("Book id : " + map.getKey() + " not found!");
			}
			
			orderInfo.setOrderQuantity(map.getValue());
			orderInfoList.add(orderInfo);
		}
		return orderInfoList;
	}

	private RowMapper<OrderInfo> ordersInfoRowMapper = (ResultSet rs, int rowNum) -> {
		OrderInfo orderInfo = new OrderInfo();

		orderInfo.setBookId(rs.getLong(1));
		orderInfo.setBookTitle(rs.getString(2));
		orderInfo.setBookPrice(rs.getBigDecimal(3));
		orderInfo.setISBN(rs.getLong(4));
		orderInfo.setStockQuantity(rs.getInt(5));
		orderInfo.setCustomerId(rs.getLong(6));
		orderInfo.setCustomerWalletBalance(rs.getBigDecimal(7));

		return orderInfo;

	};
	
	public List<Order> getOrderDetails(long orderId) {
		MapSqlParameterSource idParam = new MapSqlParameterSource();
	    idParam.addValue("orderId", orderId);
		
		List<Order> orderDetails = namedParameterJdbcTemplate.query(SELECT_ORDER_DETAILS, idParam, orderDetailsRowMapper);
		return orderDetails;
	}
	
	private RowMapper<Order> orderDetailsRowMapper = (ResultSet rs, int rowNum) -> {
		Order order = new Order();
		
		order.setId(rs.getLong(1));
		order.setCustomerId(rs.getLong(2));
		order.setTotalFee(rs.getBigDecimal(3));
		order.setCreatedDate(rs.getTimestamp(4).toString());
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setId(rs.getLong(5));
		orderDetail.setOrderId(rs.getLong(6));
		orderDetail.setBookId(rs.getLong(7));
		orderDetail.setQuantity(rs.getInt(8));
		orderDetail.setCreatedDate(rs.getTimestamp(9).toString());
		
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		orderDetails.add(orderDetail);
		
		order.setOrderDetails(orderDetails);
		
		return order;
		
	};

}
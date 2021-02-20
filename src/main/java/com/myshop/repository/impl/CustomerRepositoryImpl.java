package com.myshop.repository.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.myshop.exception.MyShopException;
import com.myshop.model.Customer;
import com.myshop.repository.CustomerRepository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private final String SELECT_ALL_CUSTOMER = "select c.id, c.name, c.surname, c.email, c.phone, "
			+ "c.address, c.wallet_balance, c.created_time " + "from customer c";
	private final String SELECT_CUSTOMER = "select * from customer c where c.id = :id";
	private final String INSERT_CUSTOMER = "INSERT INTO customer (NAME,SURNAME,EMAIL,PHONE,ADDRESS,WALLET_BALANCE) values (?,?,?,?,?,?) ";
	private final String UPDATE_CUSTOMER_WALLET_BALANCE = "UPDATE customer c set c.wallet_balance = ? where c.id = ?";

	public List<Customer> findAll() throws MyShopException {
		return jdbcTemplate.query(SELECT_ALL_CUSTOMER, allCustomersRowMapper);
	}

	private RowMapper<Customer> allCustomersRowMapper = (ResultSet rs, int rowNum) -> {
		Customer customer = new Customer();

		customer.setId(rs.getInt(1));
		customer.setName(rs.getString(2));
		customer.setSurname(rs.getString(3));
		customer.setEmail(rs.getString(4));
		customer.setPhoneNumber(rs.getString(5));
		customer.setAddress(rs.getString(6));
		customer.setWalletBalance(rs.getBigDecimal(7));
		customer.setCreatedDate(rs.getString(8));
		return customer;

	};

	public List<Customer> getCustomerById(long customerId) {
		MapSqlParameterSource idParam = new MapSqlParameterSource();
		idParam.addValue("id", customerId);

		List<Customer> customers = namedParameterJdbcTemplate.query(SELECT_CUSTOMER, idParam, customersRowMapper);
		return customers;
	}

	private RowMapper<Customer> customersRowMapper = (ResultSet rs, int rowNum) -> {
		Customer customer = new Customer();

		customer.setId(rs.getInt(1));
		customer.setName(rs.getString(2));
		customer.setSurname(rs.getString(3));
		customer.setEmail(rs.getString(4));
		customer.setPhoneNumber(rs.getString(5));
		customer.setAddress(rs.getString(6));
		customer.setWalletBalance(rs.getBigDecimal(7));
		customer.setCreatedDate(rs.getString(8));
		return customer;

	};

	public void insertCustomer(Customer customer) throws MyShopException {
		jdbcTemplate.update(INSERT_CUSTOMER, customer.getName(), customer.getSurname(), customer.getEmail(),
				customer.getPhoneNumber(), customer.getAddress(), customer.getWalletBalance());
	}

	public void updateCustomerWalletBalance(long customerId, BigDecimal currentWalletBalance)
			throws MyShopException {
		jdbcTemplate.update(UPDATE_CUSTOMER_WALLET_BALANCE, currentWalletBalance, customerId);
	}
}
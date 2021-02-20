DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS orders;

-- TABLE --

-- CUSTOMER
CREATE TABLE customer (
id bigint not null primary key auto_increment,
name varchar(255),
surname varchar(255),
email varchar(50),
phone varchar(20),
address varchar(255),
wallet_balance double,
created_time timestamp default(CURRENT_TIME)
);

-- BOOK
CREATE TABLE book (
id bigint not null primary key auto_increment,
title varchar(255),
author varchar(255),
number_of_pages int,
edited_language varchar(30),
ISBN bigint,
published_year int,
price double,
created_time timestamp default(CURRENT_TIME)
);

-- STOCK
CREATE TABLE stock (
id bigint not null primary key auto_increment,
 book_id bigint,
  foreign key (book_id) references book(id),
  unique key book_id (book_id),
quantity int,
created_time timestamp default(CURRENT_TIME)
);

-- ORDERS
CREATE TABLE orders (
id bigint not null primary key,
customer_id bigint,
  foreign key (customer_id) references customer (id),
total_fee double,
created_time timestamp default(CURRENT_TIME)
);

-- ORDER_DETAIL
CREATE TABLE order_details (
id bigint not null primary key auto_increment,
order_id bigint not null,
  foreign key (order_id) references orders(id),
book_id bigint not null,
  foreign key (book_id) references book(id),
quantity int,
created_time timestamp default(CURRENT_TIME)
);


-- SEQUENCE

-- seq_order
create sequence seq_order;

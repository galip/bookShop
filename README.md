# bookShop
Book shop stock control Java backend project.

Please consider master branch as production.

# Authentication
Authenticates with the username and password.
UserName: admin
Password: 12345678

POST 

	/http://localhost:8080/myshop/authenticate
Json request:


	{
		"userName" : "admin",
		"password" : "12345678"
	}

Sample:
Get the response:

	{
	    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZXRpciIsImV4cCI6MTYwOTg0MjE4NCwiaWF0IjoxNjA5ODA2MTg0fQ.2x62sSvBW_bx8zjAPjKqgNXlnidNdAbrpEqD7szFIBs"
	}

When you request in postman, please use your own generated token.

Sample:
		
		Key: Authorization 
		Value: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZXRpciIsImV4cCI6MTYwOTg0MjE4NCwiaWF0IjoxNjA5ODA2MTg0fQ.2x62sSvBW_bx8zjAPjKqgNXlnidNdAbrpEqD7szFIBs

# Customer
Lists all customers.
GET 

	/http://localhost:8080/myshop/customer/list

Gets Customer by customer Id.
GET 

	/http://localhost:8080/myshop/customer/1

Registers a customer.
POST 

	/http://localhost:8080/myshop/customer/register

Sample json request.


		[{
			"name" : "Mehmet",
			"surname" : "Not given",
			"email" : "jfjfjf@gmail.com",
			"phoneNumber" : "(+90) 222222222222",
			"address" : "Sarıyer, İstanbul",
			"walletBalance" : 123.4
		}]

Details:  Id is auto incremented in database. 
          
Validations: 
	     
	         Name, surname, email, walletBalance can not be null.
             WalletBalance must be more than o at registration.
             Email must be valid. (Basic regex is used here, ...@...)

# Stock
Gets all stock with quantity > 0 in stock table.


	http://localhost:8080/myshop/stock/list

# Order
Gets order details with the given order id.
GET 

	/http://localhost:8080/myshop/order/detail/1

POST 

	/http://localhost:8080/myshop/order/place/1 --> customer Id: 1
Places an order with customer Id.
Sample json request:


		[{
			"bookId" : 2,
			"quantity" : 0
		},
		{
			"bookId" : 3,
			"quantity" : 1
		},
		{
			"bookId" : 2,
			"quantity" : 1
		}]

Details:  Customer Id: 1, places an order.
          Even same bookId is ordered in json more than 1 time like bookId: 2, it will be handled and calculated as total quantity:1.
          
          If all validations are fine to process:
          a) 1 order data will be inserted in orders table.
          b) Order details data will be inserted equal number of unique bookId in requested json. In this example 2 order detail datas will be inserted in order_details table.
          c) Update wallet_Balance in Customer. Subtract total_fee from wallet_balance.
          d) Update quantity in stock table. Subtact total order quantity for each item from stock quantity and update stock quantity.
          e) a,b,c and d processes are transactional. If one of them fails, rollback will work.

Validations:

	     
	     	  Customer must be existed.
             Customer wallet balance must be more than zero.
             Order items must be available at stock table.
             Order items must be more than at stock table.
             Customer wallet balance must be enough for the order total fee.

# H2 DATABASE


	http://localhost:8080/myshop/h2-console/login.do

# SWAGGER


	http://localhost:8080/myshop/swagger-ui/#

# Log File


	Log file path: C:/temp/MyShop/application.log


CREATE TABLE product
(
	id                 INT AUTO_INCREMENT PRIMARY KEY,
	name               VARCHAR(256) NOT NULL,
	description        VARCHAR(1024),
	price DOUBLE NOT NULL,
	available_quantity INT          NOT NULL
);

CREATE TABLE order_info
(
	id              INT AUTO_INCREMENT PRIMARY KEY,
	first_name      VARCHAR(64)  NOT NULL,
	last_name       VARCHAR(64)  NOT NULL,
	address         VARCHAR(256) NOT NULL,
	postal_code     CHAR(6)      NOT NULL,
	city            VARCHAR(64)  NOT NULL,
	total_cost DOUBLE NOT NULL,
	shipping_cost DOUBLE NOT NULL,
	shipping_method VARCHAR(32)  NOT NULL
);

CREATE TABLE order_product
(
	order_id   INT,
	product_id INT,
	quantity   INT NOT NULL,
	PRIMARY KEY (order_id, product_id),
	FOREIGN KEY (order_id) REFERENCES order_info (id),
	FOREIGN KEY (product_id) REFERENCES product (id)
);

-- Mocking data
INSERT INTO product (name, description, price, available_quantity)
VALUES ('Product A', 'Description for Product A', 10.99, 5),
	   ('Product B', 'Description for Product B', 19.99, 10),
	   ('Product C', 'Description for Product C', 29.99, 3);

INSERT INTO order_info (first_name, last_name, address, postal_code, city, total_cost, shipping_cost, shipping_method)
VALUES
	('John', 'Doe', '123 Main St', '123456', 'Cityville', 150.00, 10.00, 'POSTAL'),
	('Jane', 'Smith', '456 Oak St', '654321', 'Townton', 200.00, 15.00, 'COURIER'),
	('Alice', 'Johnson', '789 Pine St', '987654', 'Villagetown', 100.00, 8.00, 'POSTAL');


INSERT INTO order_product (order_id, product_id, quantity)
VALUES (1, 1, 2),
	   (1, 2, 3),
	   (2, 2, 1),
	   (3, 3, 2),
	   (3, 1, 1);

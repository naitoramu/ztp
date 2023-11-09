CREATE TABLE Product
(
	id          INT AUTO_INCREMENT PRIMARY KEY,
	name        VARCHAR(256) NOT NULL,
	description VARCHAR(1024),
	price DOUBLE NOT NULL,
	available_quantity   INT NOT NULL
);

CREATE TABLE OrderInfo
(
	id          INT AUTO_INCREMENT PRIMARY KEY,
	first_name  VARCHAR(64)  NOT NULL,
	last_name   VARCHAR(64)  NOT NULL,
	address     VARCHAR(256) NOT NULL,
	postal_code CHAR(6)      NOT NULL,
	city        VARCHAR(64)  NOT NULL,
	total_price DOUBLE NOT NULL,
	placed_at   TIMESTAMP    NOT NULL,
	sent_at     TIMESTAMP,
	ordered_at  TIMESTAMP
);

CREATE TABLE OrderProduct
(
	order_id   INT,
	product_id INT,
	quantity   INT NOT NULL,
	PRIMARY KEY (order_id, product_id),
	FOREIGN KEY (order_id) REFERENCES OrderInfo (id),
	FOREIGN KEY (product_id) REFERENCES Product (id)
);

-- Mocking data
INSERT INTO Product (name, description, price, available_quantity)
VALUES ('Product A', 'Description for Product A', 10.99, 5),
	   ('Product B', 'Description for Product B', 19.99, 10),
	   ('Product C', 'Description for Product C', 29.99, 3);

INSERT INTO OrderInfo (first_name, last_name, address, postal_code, city, total_price, placed_at, sent_at, ordered_at)
VALUES ('John', 'Doe', '123 Main St', '123456', 'New York', 50.99, '2023-01-01 10:00:00', '2023-01-02 15:30:00','2023-01-01 10:00:00'),
	   ('Jane', 'Smith', '456 Elm St', '789012', 'Los Angeles', 35.75, '2023-01-02 11:30:00', '2023-01-03 09:45:00','2023-01-02 11:30:00'),
	   ('Mike', 'Johnson', '789 Oak St', '345678', 'Chicago', 75.50, '2023-01-03 14:45:00', NULL, '2023-01-03 14:45:00');

INSERT INTO OrderProduct (order_id, product_id, quantity)
VALUES (1, 1, 2),
	   (1, 2, 3),
	   (2, 2, 1),
	   (3, 3, 2),
	   (3, 1, 1);

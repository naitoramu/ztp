package io.nightovis.ztp.repository;

import io.nightovis.ztp.model.domain.Order;
import io.nightovis.ztp.model.domain.OrderProduct;
import io.nightovis.ztp.model.mapper.OrderMapper;
import io.nightovis.ztp.model.mapper.OrderProductMapper;
import io.nightovis.ztp.util.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class OrderRepository {
	private static final String SELECT_ALL_ORDERS_QUERY = "SELECT * FROM order_info";
	private static final String SELECT_PRODUCTS_BY_ORDER_ID_QUERY = """
		SELECT product.*, order_product.quantity
		FROM product
		JOIN order_product ON product.id = order_product.product_id
		WHERE order_product.order_id = ?
		""";

	public static Set<Order> fetchAll() {
		try (Statement statement = Database.getConnection().createStatement()) {
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_ORDERS_QUERY);
			Set<Order> orders = OrderMapper.toDomainSet(resultSet);

			orders.forEach((order -> order.orderProducts().addAll(fetchProductsByOrderId(order.id()))));
			return orders;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static Set<OrderProduct> fetchProductsByOrderId(long id) {
		try (PreparedStatement statement = Database.getConnection().prepareStatement(SELECT_PRODUCTS_BY_ORDER_ID_QUERY)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			return OrderProductMapper.toDomainSet(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

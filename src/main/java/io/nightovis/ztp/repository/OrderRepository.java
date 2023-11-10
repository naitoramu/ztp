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
import java.util.Optional;
import java.util.Set;

public class OrderRepository {
	private static final String SELECT_ALL_ORDERS_QUERY = "SELECT * FROM order_info";
	private static final String SELECT_ORDER_BY_ID_QUERY = "SELECT * FROM order_info WHERE id = ?";
	private static final String SELECT_PRODUCTS_BY_ORDER_ID_QUERY = """
		SELECT product.*, order_product.quantity
		FROM product
		JOIN order_product ON product.id = order_product.product_id
		WHERE order_product.order_id = ?
		""";

	private static final String INSERT_ORDER_QUERY = """
		INSERT INTO order_info (first_name, last_name, address, postal_code, city, total_cost, shipping_cost, shipping_method)
		VALUES (?, ?, ?, ?, ?, ?, ?, ?)
		""";

	private static final String INSERT_ORDER_PRODUCT_QUERY = """
		INSERT INTO order_product (order_id, product_id, quantity)
		VALUES (?, ?, ?)
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

	public static Optional<Order> fetchOrderById(long id) {
		try (PreparedStatement statement = Database.getConnection().prepareStatement(SELECT_ORDER_BY_ID_QUERY)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next()) {
				return Optional.empty();
			}
			Order order = OrderMapper.toDomain(resultSet);
			order.orderProducts().addAll(fetchProductsByOrderId(id));
			return Optional.of(order);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static Set<OrderProduct> fetchProductsByOrderId(long id) {
		try (PreparedStatement statement = Database.getConnection().prepareStatement(
			SELECT_PRODUCTS_BY_ORDER_ID_QUERY)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			return OrderProductMapper.toDomainSet(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static Order create(Order order) {
		long orderId;
		try (PreparedStatement insertOrderStm = Database.getConnection().prepareStatement(
			INSERT_ORDER_QUERY,
			Statement.RETURN_GENERATED_KEYS
		)) {
			setOrderParameters(insertOrderStm, order);
			int affectedRows = insertOrderStm.executeUpdate();
			orderId = retrieveGeneratedOrderId(insertOrderStm, affectedRows);
			try (PreparedStatement statement = Database.getConnection().prepareStatement(INSERT_ORDER_PRODUCT_QUERY)) {

				for (OrderProduct product : order.orderProducts()) {
					statement.setLong(1, orderId);
					statement.setLong(2, product.id());
					statement.setLong(3, product.quantity());
					statement.addBatch();
				}

				statement.executeBatch();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return fetchOrderById(orderId).orElseThrow(
			() -> new RuntimeException("Cannot fetch order with ID  " + orderId));
	}

	private static void setOrderParameters(PreparedStatement statement, Order order) throws SQLException {
		statement.setString(1, order.addressDetails().firstName());
		statement.setString(2, order.addressDetails().lastName());
		statement.setString(3, order.addressDetails().address());
		statement.setString(4, order.addressDetails().postalCode());
		statement.setString(5, order.addressDetails().city());
		statement.setDouble(6, order.totalCost());
		statement.setDouble(7, order.shippingCost());
		statement.setString(8, order.deliverer().method().toString());
	}

	private static long retrieveGeneratedOrderId(PreparedStatement statement, int affectedRows) throws SQLException {
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (affectedRows != 0 && generatedKeys.next()) {
				return generatedKeys.getLong(1);
			} else {
				throw new SQLException("Operation failed, no ID obtained.");
			}
		}
	}
}

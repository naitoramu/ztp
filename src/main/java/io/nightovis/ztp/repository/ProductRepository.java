package io.nightovis.ztp.repository;

import io.nightovis.ztp.util.Database;
import io.nightovis.ztp.model.domain.Product;
import io.nightovis.ztp.model.mapper.ProductMapper;

import java.sql.*;
import java.util.Optional;
import java.util.Set;

public class ProductRepository {

	private static final String SELECT_ALL_PRODUCTS_QUERY = "SELECT id, name, description, price, available_quantity FROM product";
	private static final String SELECT_PRODUCT_BY_ID_QUERY = "SELECT id, name, description, price, available_quantity FROM product WHERE id = ?";
	private static final String INSERT_PRODUCT_QUERY = "INSERT INTO product (name, description, price, available_quantity) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_PRODUCT_QUERY = "UPDATE product SET name = ?, description = ?, price = ?, available_quantity = ? WHERE id = ?";
	private static final String DELETE_PRODUCT_QUERY = "DELETE FROM product WHERE id = ?";

	public static Set<Product> fetchAllProducts() {
		try (Statement statement = Database.getConnection().createStatement()) {
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_PRODUCTS_QUERY);
			return ProductMapper.toDomainSet(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static Optional<Product> fetchProductById(long id) {
		try (PreparedStatement statement = Database.getConnection().prepareStatement(SELECT_PRODUCT_BY_ID_QUERY)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			return resultSet.next() ? Optional.of(ProductMapper.toDomain(resultSet)) : Optional.empty();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static Product create(Product product) {
		try (PreparedStatement statement = Database.getConnection().prepareStatement(
			INSERT_PRODUCT_QUERY,
			Statement.RETURN_GENERATED_KEYS)) {
			setProductParameters(statement, product);
			int affectedRows = statement.executeUpdate();
			return retrieveGeneratedProduct(statement, affectedRows);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static Product update(long id, Product product) {
		try (PreparedStatement statement = Database.getConnection().prepareStatement(
			UPDATE_PRODUCT_QUERY,
			Statement.RETURN_GENERATED_KEYS)) {
			setProductParameters(statement, product);
			statement.setLong(5, id);
			int affectedRows = statement.executeUpdate();
			return retrieveGeneratedProduct(statement, affectedRows);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void delete(long id) {
		try (PreparedStatement statement = Database.getConnection().prepareStatement(DELETE_PRODUCT_QUERY)) {
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void setProductParameters(PreparedStatement statement, Product product) throws SQLException {
		statement.setString(1, product.name());
		statement.setString(2, product.description());
		statement.setDouble(3, product.price());
		statement.setDouble(4, product.availableQuantity());
	}

	private static Product retrieveGeneratedProduct(PreparedStatement statement, int affectedRows) throws SQLException {
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (affectedRows != 0 && generatedKeys.next()) {
				return fetchProductById(generatedKeys.getLong(1))
					.orElseThrow(IllegalStateException::new);
			} else {
				throw new SQLException("Operation failed, no ID obtained.");
			}
		}
	}
}

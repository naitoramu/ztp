package io.nightovis.ztp.repository;

import io.nightovis.ztp.Database;
import io.nightovis.ztp.domain.Product;
import io.nightovis.ztp.mapper.ProductMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Set;

public class ProductRepository {

	public static Set<Product> fetchAllProducts() {
		ResultSet resultSet;
		try (Statement statement = Database.getConnection().createStatement()) {
			resultSet = statement.executeQuery("SELECT id, name, description, price, available_quantity FROM product");
			return ProductMapper.toDomainSet(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static Optional<Product> fetchProductById(long id) {
		ResultSet resultSet;
		try (PreparedStatement statement = Database.getConnection().prepareStatement(
			"SELECT id, name, description, price, available_quantity FROM product WHERE id=?")) {
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return Optional.of(ProductMapper.toDomain(resultSet));
			}
			return Optional.empty();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static Product create(Product product) {
		try (PreparedStatement statement = Database.getConnection().prepareStatement(
			"INSERT INTO product (name, description, price, available_quantity) VALUES (?, ?, ?, ?)",
			new String[]{"id"}
		)) {
			statement.setString(1, product.name());
			statement.setString(2, product.description());
			statement.setDouble(3, product.price());
			statement.setDouble(4, product.availableQuantity());

			int affectedRows = statement.executeUpdate();
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (affectedRows != 0 && generatedKeys.next()) {
					return fetchProductById(generatedKeys.getLong(1))
						.orElseThrow(IllegalStateException::new);
				} else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static Product update(long id, Product product) {
		try (PreparedStatement statement = Database.getConnection().prepareStatement(
			"UPDATE product SET name = ?, description = ?, price = ?, available_quantity = ? WHERE id = ?",
			new String[]{"id"}
		)) {
			statement.setString(1, product.name());
			statement.setString(2, product.description());
			statement.setDouble(3, product.price());
			statement.setDouble(4, product.availableQuantity());
			statement.setDouble(5, id);

			int affectedRows = statement.executeUpdate();
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (affectedRows != 0 && generatedKeys.next()) {
					return fetchProductById(generatedKeys.getLong(1))
						.orElseThrow(IllegalStateException::new);
				} else {
					throw new SQLException("Updating user failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void delete(long id) {
		try (PreparedStatement statement = Database.getConnection().prepareStatement(
			"DELETE FROM product WHERE id=?")) {
			statement.setLong(1, id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

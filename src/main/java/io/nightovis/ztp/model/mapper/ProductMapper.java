package io.nightovis.ztp.model.mapper;

import io.nightovis.ztp.model.domain.Product;
import io.nightovis.ztp.model.dto.ProductDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ProductMapper {

	public static ProductDto toDto(Product product) {
		return new ProductDto(
			product.id(),
			product.name(),
			product.description(),
			product.price(),
			product.availableQuantity()
		);
	}

	public static Set<Product> toDomainSet(ResultSet resultSet) throws SQLException {
		Set<Product> products = new HashSet<>();
		if (resultSet == null) {
			return products;
		}

		while (resultSet.next()) {
			products.add(toDomain(resultSet));
		}

		return products;
	}

	public static Product toDomain(ResultSet resultSet) throws SQLException {
		return new Product(
			resultSet.getLong("id"),
			resultSet.getString("name"),
			resultSet.getString("description"),
			resultSet.getDouble("price"),
			resultSet.getLong("available_quantity")
		);
	}

	public static Product toDomain(ProductDto dto) {
		return new Product(
			dto.id(),
			dto.name(),
			dto.description(),
			dto.price(),
			dto.availableQuantity()
		);
	}
}

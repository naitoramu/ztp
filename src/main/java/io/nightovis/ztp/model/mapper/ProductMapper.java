package io.nightovis.ztp.model.mapper;

import io.nightovis.ztp.model.domain.Product;
import io.nightovis.ztp.model.dto.ProductDto;
import io.nightovis.ztp.service.OrderService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

	public static Map<Long, OrderService.ProductInfo> toIdQuantityMap(ResultSet resultSet) throws SQLException {
		Map<Long, OrderService.ProductInfo> idToQuantity = new HashMap<>();
		if (resultSet == null) {
			return idToQuantity;
		}

		while (resultSet.next()) {
			idToQuantity.put(
				resultSet.getLong("id"),
				new OrderService.ProductInfo(
					resultSet.getDouble("price"),
					resultSet.getLong("available_quantity")
				)
			);
		}

		return idToQuantity;
	}
}

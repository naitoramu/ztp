package io.nightovis.ztp.model.mapper;

import io.nightovis.ztp.model.domain.OrderProduct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class OrderProductMapper {
	public static Set<OrderProduct> toDomainSet(ResultSet resultSet) throws SQLException {
		Set<OrderProduct> OrderProducts = new HashSet<>();
		if (resultSet == null) {
			return OrderProducts;
		}

		while (resultSet.next()) {
			OrderProducts.add(toDomain(resultSet));
		}

		return OrderProducts;
	}

	public static OrderProduct toDomain(ResultSet resultSet) throws SQLException {
		return new OrderProduct(
			resultSet.getLong("id"),
			resultSet.getLong("quantity"),
			resultSet.getString("name"),
			resultSet.getString("description"),
			resultSet.getLong("quantity") * resultSet.getDouble("price")
		);
	}
}

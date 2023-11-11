package io.nightovis.ztp.model.mapper;

import io.nightovis.ztp.model.domain.AddressDetails;
import io.nightovis.ztp.service.delivery.DeliveryStrategy;
import io.nightovis.ztp.model.domain.Order;
import io.nightovis.ztp.model.domain.OrderProduct;
import io.nightovis.ztp.model.dto.AddressDetailsDto;
import io.nightovis.ztp.model.dto.OrderDto;
import io.nightovis.ztp.model.dto.OrderProductDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderMapper {

	public static Set<Order> toDomainSet(ResultSet resultSet) throws SQLException {
		Set<Order> Orders = new HashSet<>();
		if (resultSet == null) {
			return Orders;
		}

		while (resultSet.next()) {
			Orders.add(toDomain(resultSet));
		}

		return Orders;
	}

	public static Order toDomain(ResultSet resultSet) throws SQLException {
		return new Order(
			resultSet.getLong("id"),
			new AddressDetails(
				resultSet.getString("first_name"),
				resultSet.getString("last_name"),
				resultSet.getString("address"),
				resultSet.getString("postal_code"),
				resultSet.getString("city")
			),
			new HashSet<>(),
			resultSet.getDouble("total_cost"),
			resultSet.getDouble("shipping_cost"),
			DeliveryStrategy.getByString(resultSet.getString("shipping_method"))
		);
	}

	public static OrderDto toDto(Order order) {
		return new OrderDto(
			order.id(),
			new AddressDetailsDto(
				order.addressDetails().firstName(),
				order.addressDetails().lastName(),
				order.addressDetails().address(),
				order.addressDetails().postalCode(),
				order.addressDetails().city()
			),
			order.orderProducts().stream().map(OrderMapper::toDto).collect(Collectors.toSet()),
			order.totalCost(),
			order.shippingCost(),
			order.deliverer().method()
		);
	}

	private static OrderProductDto toDto(OrderProduct orderProduct) {
		return new OrderProductDto(
			orderProduct.id(),
			orderProduct.quantity(),
			orderProduct.name(),
			orderProduct.description(),
			orderProduct.cost()
		);
	}

	public static Order toDomain(OrderDto dto) {
		DeliveryStrategy deliveryStrategy = DeliveryStrategy.fromMethod(dto.shippingMethod());
		AddressDetails addressDetails = new AddressDetails(
			dto.addressDetails().firstName(),
			dto.addressDetails().lastName(),
			dto.addressDetails().address(),
			dto.addressDetails().postalCode(),
			dto.addressDetails().city()
		);

		return new Order(
			dto.id(),
			addressDetails,
			dto.orderProducts().stream().map(OrderMapper::toDomain).collect(Collectors.toSet()),
			dto.totalCost(),
			deliveryStrategy.calculateShippingCost(addressDetails),
			deliveryStrategy
		);
	}

	public static OrderProduct toDomain(OrderProductDto product) {
		return new OrderProduct(
			product.id(),
			product.quantity(),
			product.name(),
			product.description(),
			product.cost()
		);
	}
}

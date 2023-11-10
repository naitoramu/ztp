package io.nightovis.ztp.api.controller;

import io.nightovis.ztp.api.servlet.ResponseEntity;
import io.nightovis.ztp.model.dto.OrderDto;
import io.nightovis.ztp.model.mapper.OrderMapper;
import io.nightovis.ztp.service.OrderService;

import java.util.Set;
import java.util.stream.Collectors;

public class OrderController {
	public static ResponseEntity<Set<OrderDto>> fetchAll() {
		Set<OrderDto> products = OrderService.fetchAllOrders().stream()
			.map(OrderMapper::toDto)
			.collect(Collectors.toSet());

		return ResponseEntity.ok(products);
	}

	public static ResponseEntity<OrderDto> create(OrderDto orderDto) {
		return null;
	}
}

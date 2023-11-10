package io.nightovis.ztp.api.controller;

import io.nightovis.ztp.api.servlet.ResponseEntity;
import io.nightovis.ztp.model.dto.OrderDto;
import io.nightovis.ztp.model.mapper.OrderMapper;
import io.nightovis.ztp.problem.ProblemOccurredException;
import io.nightovis.ztp.problem.Problems;
import io.nightovis.ztp.service.OrderService;
import io.nightovis.ztp.util.Validation;

import java.util.Set;
import java.util.stream.Collectors;

public class OrderController {

	public static ResponseEntity<Set<OrderDto>> fetchAll() {
		Set<OrderDto> orders = OrderService.fetchAllOrders().stream()
			.map(OrderMapper::toDto)
			.collect(Collectors.toSet());

		return ResponseEntity.ok(orders);
	}

	public static ResponseEntity<OrderDto> fetchById(long orderId) {
		OrderDto order = OrderService.fetchById(orderId)
			.map(OrderMapper::toDto)
			.orElseThrow(() -> new ProblemOccurredException(Problems.resourceNotFound("order", orderId)));

		return ResponseEntity.ok(order);
	}

	public static ResponseEntity<OrderDto> create(OrderDto order) {

		Validation.validate(order);
		OrderDto createdOrder = OrderMapper.toDto(OrderService.create(OrderMapper.toDomain(order)));
		String location = "/orders/" + createdOrder.id();
		return ResponseEntity.created(createdOrder, location);
	}
}

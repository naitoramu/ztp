package io.nightovis.ztp.service;

import io.nightovis.ztp.model.domain.Order;
import io.nightovis.ztp.repository.OrderRepository;

import java.util.Set;

public class OrderService {

	public static Set<Order> fetchAllOrders() {
		return OrderRepository.fetchAll();
	}
}

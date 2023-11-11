package io.nightovis.ztp.service;

import io.nightovis.ztp.model.domain.Order;
import io.nightovis.ztp.model.domain.OrderProduct;
import io.nightovis.ztp.problem.ProblemOccurredException;
import io.nightovis.ztp.problem.Problems;
import io.nightovis.ztp.repository.OrderRepository;
import io.nightovis.ztp.repository.ProductRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class OrderService {

	public static Set<Order> fetchAllOrders() {
		return OrderRepository.fetchAll();
	}

	public static Optional<Order> fetchById(long orderId) {
		return OrderRepository.fetchOrderById(orderId);
	}

	public static Order create(Order order) {
		Map<Long, Long> productIdToQuantity = order.orderProducts().stream().collect(
			Collectors.toMap(OrderProduct::id, OrderProduct::quantity)
		);
		Map<Long, ProductInfo> productIdToAvailableQuantity = ProductRepository.fetchProductsQuantity(
			productIdToQuantity.keySet()
		);
		Map<Long, Long> updatedProductQuantities = new HashMap<>();
		double totalCost = 0;

		for (Map.Entry<Long, Long> entry : productIdToQuantity.entrySet()) {
			Long id = entry.getKey();
			Long quantity = entry.getValue();
			Long availableQuantity = getAvailableQuantity(productIdToAvailableQuantity, id);

			checkProductAvailability(quantity, availableQuantity, id);

			updatedProductQuantities.put(id, availableQuantity - quantity);
			totalCost += productIdToAvailableQuantity.get(id).price() * quantity;
		}
		totalCost += order.shippingCost();

		Order createdOrder = OrderRepository.create(order.totalCost(totalCost));
		ProductRepository.updateProductsQuantity(updatedProductQuantities);

		return createdOrder;
	}

	private static void checkProductAvailability(Long quantity, Long availableQuantity, Long id) {
		if (quantity > availableQuantity) {
			throw new ProblemOccurredException(
				Problems.exceedingAvailableQuantity(id, quantity, availableQuantity)
			);
		}
	}

	private static Long getAvailableQuantity(Map<Long, ProductInfo> productIdToAvailableQuantity, Long id) {
		return Optional.ofNullable(productIdToAvailableQuantity.get(id))
			.map(ProductInfo::quantity)
			.orElseThrow(() -> new ProblemOccurredException(Problems.resourceNotFound("product", id)));
	}

	public record ProductInfo(
		double price,
		Long quantity
	) {}
}

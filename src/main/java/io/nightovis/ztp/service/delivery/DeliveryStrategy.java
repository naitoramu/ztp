package io.nightovis.ztp.service.delivery;

import io.nightovis.ztp.model.dto.ShippingMethod;
import io.nightovis.ztp.model.domain.AddressDetails;

public interface DeliveryStrategy {

	static DeliveryStrategy getByString(String deliverer) {
		return switch (deliverer) {
			case "POSTAL" -> new Post();
			case "COURIER" -> new Courier();
			default -> throw new IllegalStateException("Unexpected value: " + deliverer);
		};
	}

	static DeliveryStrategy fromMethod(ShippingMethod method) {
		return switch (method) {
			case POSTAL -> new Post();
			case COURIER -> new Courier();
		};
	}

	double calculateShippingCost(AddressDetails addressDetails);

	ShippingMethod method();
}

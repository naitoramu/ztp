package io.nightovis.ztp.model.domain;

import io.nightovis.ztp.model.ShippingMethod;

public interface Deliverer {

	static Deliverer getByString(String deliverer) {
		return switch (deliverer) {
			case "POSTAL" -> new Post();
			case "COURIER" -> new Courier();
			default -> throw new IllegalStateException("Unexpected value: " + deliverer);
		};
	}

	static Deliverer fromMethod(ShippingMethod method) {
		return switch (method) {
			case POSTAL -> new Post();
			case COURIER -> new Courier();
		};
	}

	double calculateShippingCost(AddressDetails addressDetails);

	ShippingMethod method();
}

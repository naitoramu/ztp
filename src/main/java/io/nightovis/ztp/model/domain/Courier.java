package io.nightovis.ztp.model.domain;

import io.nightovis.ztp.model.ShippingMethod;

public class Courier implements Deliverer {
	@Override
	public double calculateShippingCost(AddressDetails addressDetails) {
		return 2137;
	}

	@Override
	public ShippingMethod method() {
		return ShippingMethod.COURIER;
	}
}

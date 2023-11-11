package io.nightovis.ztp.service.delivery;

import io.nightovis.ztp.model.dto.ShippingMethod;
import io.nightovis.ztp.model.domain.AddressDetails;

public class Courier implements DeliveryStrategy {
	@Override
	public double calculateShippingCost(AddressDetails addressDetails) {
		return 2137;
	}

	@Override
	public ShippingMethod method() {
		return ShippingMethod.COURIER;
	}
}

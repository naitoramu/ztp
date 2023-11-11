package io.nightovis.ztp.service.delivery;

import io.nightovis.ztp.model.dto.ShippingMethod;
import io.nightovis.ztp.model.domain.AddressDetails;

public class Post  implements DeliveryStrategy {
	@Override
	public double calculateShippingCost(AddressDetails addressDetails) {
		return 420;
	}

	@Override
	public ShippingMethod method() {
		return ShippingMethod.POSTAL;
	}
}

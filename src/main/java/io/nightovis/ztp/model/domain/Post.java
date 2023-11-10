package io.nightovis.ztp.model.domain;

import io.nightovis.ztp.model.ShippingMethod;

public class Post  implements Deliverer {
	@Override
	public double calculateShippingCost(AddressDetails addressDetails) {
		return 420;
	}

	@Override
	public ShippingMethod method() {
		return ShippingMethod.POSTAL;
	}
}

package io.nightovis.ztp.model.domain;

import java.util.Set;

public record Order(
	long id,
	AddressDetails addressDetails,
	Set<OrderProduct> orderProducts,
	double totalCost,
	double shippingCost,
	Deliverer deliverer
) {
}

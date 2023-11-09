package io.nightovis.ztp.model.domain;

public record Product(
	long id,
	String name,
	String description,
	double price,
	long availableQuantity

) {
}

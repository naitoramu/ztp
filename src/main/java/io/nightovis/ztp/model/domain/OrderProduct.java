package io.nightovis.ztp.model.domain;

public record OrderProduct(
	long id,
	long quantity,
	String name,
	String description,
	double cost

) {
}

package io.nightovis.ztp.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("products")
public record ProductMongo(
	@Id
	String id,
	String name,
	String description,
	double price,
	long availableQuantity
) {
}

package io.nightovis.ztp.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("products")
public record ProductMongo(
	@Id
	String id,
	String name,
	String description,
	double price,
	long availableQuantity,
	@DBRef
	List<AuditLogMongo<ProductMongo>> auditLogs
) {
	public ProductMongo(String id, String name, String description, double price, long availableQuantity) {
		this(id, name, description, price, availableQuantity, null);
	}
}
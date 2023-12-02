package io.nightovis.ztp.model.mapper;

import io.nightovis.ztp.model.domain.AuditLog;
import io.nightovis.ztp.model.domain.Product;
import io.nightovis.ztp.model.dto.ProductDto;
import io.nightovis.ztp.model.mongo.ProductMongo;

public class ProductMapper {

	public static ProductDto toDto(Product product) {
		return new ProductDto(
			product.id(),
			product.name(),
			product.description(),
			product.price(),
			product.availableQuantity()
		);
	}

	public static Product toDomain(ProductDto dto) {
		return new Product(
			null,
			dto.name(),
			dto.description(),
			dto.price(),
			dto.availableQuantity()
		);
	}

	public static Product toDomain(ProductMongo product) {
		return new Product(
			product.id(),
			product.name(),
			product.description(),
			product.price(),
			product.availableQuantity(),
			product.auditLogs().stream().map(AuditLog::fromMongo).toList()
		);
	}

	public static ProductMongo toMongo(Product product) {
		return new ProductMongo(
			product.id(),
			product.name(),
			product.description(),
			product.price(),
			product.availableQuantity(),
			product.auditLogs().stream().map(AuditLog::toMongo).toList()
		);
	}
}
package io.nightovis.ztp.model.mapper;

import io.nightovis.ztp.model.domain.AuditLog;
import io.nightovis.ztp.model.domain.Product;
import io.nightovis.ztp.model.dto.AuditLogDto;
import io.nightovis.ztp.model.dto.ProductDto;
import io.nightovis.ztp.model.mongo.AuditLogMongo;
import io.nightovis.ztp.model.mongo.ProductMongo;

import java.util.Optional;

public class ProductMapper {

	public static ProductDto toDto(Product product) {
		return new ProductDto(
			product.id(),
			product.name(),
			product.description(),
			product.imgUrl(),
			product.price(),
			product.availableQuantity()
		);
	}

	public static AuditLogDto<ProductDto> toDto(AuditLog<Product> log) {
		return new AuditLogDto<>(
			log.resourceId(),
			log.resourceType(),
			log.operation(),
			log.resource().map(ProductMapper::toDto),
			log.timestamp()
		);
	}

	public static Product toDomain(ProductDto dto) {
		return new Product(
			null,
			dto.name(),
			dto.description(),
			dto.imgUrl(),
			dto.price(),
			dto.availableQuantity()
		);
	}

	public static Product toDomain(ProductMongo product) {
		return new Product(
			product.id(),
			product.name(),
			product.description(),
			product.imgUrl(),
			product.price(),
			product.availableQuantity()
		);
	}

	public static AuditLog<Product> toDomain(AuditLogMongo<ProductMongo> log) {
		return new AuditLog<>(
			log.id(),
			log.resourceId(),
			log.resourceType(),
			log.operation(),
			Optional.ofNullable(log.resource()).map(ProductMapper::toDomain),
			log.timestamp()
		);
	}

	public static ProductMongo toMongo(Product product) {
		return new ProductMongo(
			product.id(),
			product.name(),
			product.description(),
			product.imgUrl(),
			product.price(),
			product.availableQuantity()
		);
	}

	public static AuditLogMongo<ProductMongo> toMongo(AuditLog<Product> log) {
		return new AuditLogMongo<>(
			null,
			log.resourceId(),
			log.resourceType(),
			log.operation(),
			log.resource().map(ProductMapper::toMongo).orElse(null),
			log.timestamp()
		);
	}
}
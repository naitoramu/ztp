package io.nightovis.ztp.api.controller;

import io.nightovis.ztp.api.servlet.ResponseEntity;
import io.nightovis.ztp.problem.ProblemOccurredException;
import io.nightovis.ztp.problem.Problems;
import io.nightovis.ztp.model.dto.ProductDto;
import io.nightovis.ztp.model.mapper.ProductMapper;
import io.nightovis.ztp.service.ProductService;
import io.nightovis.ztp.util.Validation;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductController {

	public static ResponseEntity<Set<ProductDto>> fetchAllProducts() {
		Set<ProductDto> products = ProductService.fetchAllProducts().stream()
			.map(ProductMapper::toDto)
			.collect(Collectors.toSet());

		return ResponseEntity.ok(products);
	}

	public static ResponseEntity<ProductDto> fetchById(long id) throws ProblemOccurredException {
		ProductDto product = ProductService.fetchProductById(id)
			.map(ProductMapper::toDto)
			.orElseThrow(() -> new ProblemOccurredException(Problems.resourceNotFound("product", id)));

		return ResponseEntity.ok(product);
	}

	public static ResponseEntity<ProductDto> create(ProductDto product) throws ProblemOccurredException {
		Validation.validate(product);
		ProductDto createdProduct = ProductMapper.toDto(ProductService.create(ProductMapper.toDomain(product)));
		String location = "/products/" + createdProduct.id();
		return ResponseEntity.created(createdProduct, location);
	}

	public static ResponseEntity<ProductDto> update(long id, ProductDto product) throws ProblemOccurredException {
		Validation.validate(product);
		ProductDto updatedProduct = ProductMapper.toDto(ProductService.update(id, ProductMapper.toDomain(product)));

		return ResponseEntity.ok(updatedProduct);
	}

	public static ResponseEntity<ProductDto> delete(long id) throws ProblemOccurredException {
		ProductService.delete(id);

		return ResponseEntity.noContent();
	}
}

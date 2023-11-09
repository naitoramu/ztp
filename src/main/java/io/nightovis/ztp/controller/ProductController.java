package io.nightovis.ztp.controller;

import io.nightovis.ztp.ProblemOccurredException;
import io.nightovis.ztp.Problems;
import io.nightovis.ztp.dto.ProductDto;
import io.nightovis.ztp.mapper.ProductMapper;
import io.nightovis.ztp.service.ProductService;
import io.nightovis.ztp.servlet.ResponseEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductController {

	private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

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
		validateProduct(product);
		ProductDto createdProduct = ProductMapper.toDto(ProductService.create(ProductMapper.toDomain(product)));
		String location = "/products/" + createdProduct.id();
		return ResponseEntity.created(createdProduct, location);
	}

	public static ResponseEntity<ProductDto> update(long id, ProductDto product) throws ProblemOccurredException {
		validateProduct(product);
		ProductDto updatedProduct = ProductMapper.toDto(ProductService.update(id, ProductMapper.toDomain(product)));

		return ResponseEntity.ok(updatedProduct);
	}

	public static ResponseEntity<ProductDto> delete(long id) throws ProblemOccurredException {
		ProductService.delete(id);

		return ResponseEntity.noContent();
	}

	private static void validateProduct(ProductDto product) {
		Set<ConstraintViolation<ProductDto>> violations = validator.validate(product);
		if (!violations.isEmpty()) {
			throw new ProblemOccurredException(Problems.validationError(violations));
		}
	}
}

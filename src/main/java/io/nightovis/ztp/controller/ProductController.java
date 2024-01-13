package io.nightovis.ztp.controller;

import io.nightovis.ztp.model.domain.AuditLog;
import io.nightovis.ztp.model.dto.ProductDto;
import io.nightovis.ztp.model.mapper.ProductMapper;
import io.nightovis.ztp.problem.ProblemOccurredException;
import io.nightovis.ztp.problem.Problems;
import io.nightovis.ztp.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<Set<ProductDto>> fetchAllProducts() {
		Set<ProductDto> products = productService.fetchAllProducts().stream()
			.map(ProductMapper::toDto)
			.collect(Collectors.toSet());

		return ResponseEntity.ok(products);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductDto> fetchById(@PathVariable String productId) throws ProblemOccurredException {
		ProductDto product = productService.fetchProductById(productId)
			.map(ProductMapper::toDto)
			.orElseThrow(() -> new ProblemOccurredException(Problems.resourceNotFound("product", productId)));

		return ResponseEntity.ok(product);
	}

	@PostMapping
	public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductDto product) throws ProblemOccurredException {
		ProductDto createdProduct = ProductMapper.toDto(productService.create(ProductMapper.toDomain(product)));
		String location = "/products/" + createdProduct.id();
		return ResponseEntity.created(URI.create(location)).body(createdProduct);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> update(@PathVariable String productId, @RequestBody @Valid ProductDto product) throws ProblemOccurredException {
		ProductDto updatedProduct = ProductMapper.toDto(productService.update(productId, ProductMapper.toDomain(product)));

		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<?> delete(@PathVariable String productId) throws ProblemOccurredException {
		productService.delete(productId);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{productId}/audit")
	public ResponseEntity<List<AuditLog>> fetchAuditLogsByProductId(@PathVariable String productId) throws ProblemOccurredException {
		List<AuditLog> logs = productService.fetchAuditLogsByProductId(productId);

		return ResponseEntity.ok(logs);
	}
}

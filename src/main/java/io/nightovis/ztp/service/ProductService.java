package io.nightovis.ztp.service;

import io.nightovis.ztp.model.mapper.ProductMapper;
import io.nightovis.ztp.problem.ProblemOccurredException;
import io.nightovis.ztp.problem.Problems;
import io.nightovis.ztp.model.domain.Product;
import io.nightovis.ztp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> fetchAllProducts() {
		return productRepository.findAll().stream().map(ProductMapper::toDomain).toList();
	}

	public Optional<Product> fetchProductById(String id) {
		return productRepository.findById(id).map(ProductMapper::toDomain);
	}

	public Product create(Product product) {
		return ProductMapper.toDomain(productRepository.insert(ProductMapper.toMongo(product)));
	}

	public Product update(String id, Product product) {
		Product existingProduct = productRepository.findById(id).map(ProductMapper::toDomain)
			.orElseThrow(() -> productNotFoundException(id));

		return ProductMapper.toDomain(productRepository.save(ProductMapper.toMongo(product.id(existingProduct.id()))));
	}

	public void delete(String id) {
		productRepository.findById(id)
			.orElseThrow(() -> productNotFoundException(id));

		productRepository.deleteById(id);
	}

	private static ProblemOccurredException productNotFoundException(String id) {
		return new ProblemOccurredException(Problems.resourceNotFound("product", id));
	}
}
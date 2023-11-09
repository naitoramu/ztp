package io.nightovis.ztp.service;

import io.nightovis.ztp.problem.ProblemOccurredException;
import io.nightovis.ztp.problem.Problems;
import io.nightovis.ztp.model.domain.Product;
import io.nightovis.ztp.repository.ProductRepository;

import java.util.Optional;
import java.util.Set;

public class ProductService {

	public static Set<Product> fetchAllProducts() {
		return ProductRepository.fetchAllProducts();
	}

	public static Optional<Product> fetchProductById(long id) {
		return ProductRepository.fetchProductById(id);
	}

	public static Product create(Product product) {
		return ProductRepository.create(product);
	}

	public static Product update(long id, Product product) {
		checkIfProductWithGivenIdExists(id);
		return ProductRepository.update(id, product);
	}

	public static void delete(long id) {
		checkIfProductWithGivenIdExists(id);
		ProductRepository.delete(id);
	}

	private static void checkIfProductWithGivenIdExists(long id) {
		ProductRepository.fetchProductById(id)
			.orElseThrow(() -> new ProblemOccurredException(Problems.resourceNotFound("product", id)));
	}
}

package io.nightovis.ztp.service;

import io.nightovis.ztp.model.ResourceType;
import io.nightovis.ztp.model.domain.AuditLog;
import io.nightovis.ztp.model.domain.Product;
import io.nightovis.ztp.model.mapper.ProductMapper;
import io.nightovis.ztp.model.mongo.ProductMongo;
import io.nightovis.ztp.problem.ProblemOccurredException;
import io.nightovis.ztp.problem.Problems;
import io.nightovis.ztp.repository.AuditLogRepository;
import io.nightovis.ztp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final AuditLogRepository auditLogRepository;

	public ProductService(ProductRepository productRepository, AuditLogRepository auditLogRepository) {
		this.productRepository = productRepository;
		this.auditLogRepository = auditLogRepository;
	}

	public List<Product> fetchAllProducts() {
		return productRepository.findAll().stream().map(ProductMapper::toDomain).toList();
	}

	public Optional<Product> fetchProductById(String id) {
		return productRepository.findById(id).map(ProductMapper::toDomain);
	}

	public List<AuditLog<Product>> fetchAuditLogsByProductId(String productId) {
		return auditLogRepository.findByResourceIdAndResourceType(productId, ResourceType.PRODUCT).stream()
			.map(ProductMapper::toDomain)
			.toList();
	}

	public Product create(Product product) {
		ProductMongo createdProduct = productRepository.insert(ProductMapper.toMongo(product));

		AuditLog<Product> auditLog = AuditLog.resourceCreated(createdProduct.id(), ResourceType.PRODUCT, product);
		auditLogRepository.save(ProductMapper.toMongo(auditLog));

		return ProductMapper.toDomain(createdProduct);
	}

	public Product update(String id, Product product) {
		Product existingProduct = productRepository.findById(id).map(ProductMapper::toDomain)
			.orElseThrow(() -> productNotFoundException(id));

		ProductMongo updatedProduct = productRepository.save(ProductMapper.toMongo(product.id(existingProduct.id())));

		AuditLog<Product> auditLog = AuditLog.resourceUpdated(updatedProduct.id(), ResourceType.PRODUCT, product);
		auditLogRepository.save(ProductMapper.toMongo(auditLog));

		return ProductMapper.toDomain(updatedProduct);
	}

	public void delete(String id) {
		productRepository.findById(id)
			.orElseThrow(() -> productNotFoundException(id));

		productRepository.deleteById(id);

		AuditLog<Product> auditLog = AuditLog.resourceDeleted(id, ResourceType.PRODUCT);
		auditLogRepository.save(ProductMapper.toMongo(auditLog));
	}

	private static ProblemOccurredException productNotFoundException(String id) {
		return new ProblemOccurredException(Problems.resourceNotFound("product", id));
	}
}
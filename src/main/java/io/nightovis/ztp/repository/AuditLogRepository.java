package io.nightovis.ztp.repository;

import io.nightovis.ztp.model.ResourceType;
import io.nightovis.ztp.model.mongo.AuditLogMongo;
import io.nightovis.ztp.model.mongo.ProductMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditLogRepository extends MongoRepository<AuditLogMongo<ProductMongo>, String> {
	List<AuditLogMongo<ProductMongo>> findByResourceIdAndResourceType(String productId, ResourceType resourceType);
}

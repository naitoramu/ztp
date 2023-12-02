package io.nightovis.ztp.repository;

import io.nightovis.ztp.model.ResourceType;
import io.nightovis.ztp.model.mongo.AuditLogMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuditLogRepository extends MongoRepository<AuditLogMongo, String> {
	List<AuditLogMongo> findByResourceIdAndResourceType(String productId, ResourceType resourceType);
}

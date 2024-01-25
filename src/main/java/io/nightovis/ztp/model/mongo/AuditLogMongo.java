package io.nightovis.ztp.model.mongo;

import io.nightovis.ztp.model.AuditOperation;
import io.nightovis.ztp.model.ResourceType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("audit_logs")
public record AuditLogMongo<T>(
	@Id
	String id,
	String resourceId,
	ResourceType resourceType,
	AuditOperation operation,
	T resource,
	Instant timestamp
) {
}
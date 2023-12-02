package io.nightovis.ztp.model.domain;

import io.nightovis.ztp.model.AuditOperation;
import io.nightovis.ztp.model.ResourceType;
import io.nightovis.ztp.model.mongo.AuditLogMongo;

import java.time.Instant;

public record AuditLog(
	String id,
	String resourceId,
	ResourceType resourceType,
	AuditOperation operation,
	Instant timestamp
) {
	public AuditLogMongo toMongo() {
		return new AuditLogMongo(null, resourceId, resourceType, operation, timestamp);
	}

	public static AuditLog fromMongo(AuditLogMongo log) {
		return new AuditLog(log.id(), log.resourceId(), log.resourceType(), log.operation(), log.timestamp());
	}
}
package io.nightovis.ztp.model.domain;

import io.nightovis.ztp.model.AuditOperation;
import io.nightovis.ztp.model.ResourceType;

import java.time.Instant;
import java.util.Optional;

public record AuditLog<T>(
	String id,
	String resourceId,
	ResourceType resourceType,
	AuditOperation operation,
	Optional<T> resource,
	Instant timestamp
) {

	public static <T> AuditLog<T> resourceCreated(String resourceId, ResourceType resourceType, T resource) {
		return new AuditLog<>(
			null,
			resourceId,
			resourceType,
			AuditOperation.CREATE,
			Optional.of(resource),
			Instant.now()
		);
	}

	public static <T> AuditLog<T> resourceUpdated(String resourceId, ResourceType resourceType, T resource) {
		return new AuditLog<>(
			null,
			resourceId,
			resourceType,
			AuditOperation.UPDATE,
			Optional.of(resource),
			Instant.now()
		);
	}

	public static <T> AuditLog<T> resourceDeleted(String resourceId, ResourceType resourceType) {
		return new AuditLog<>(
			null,
			resourceId,
			resourceType,
			AuditOperation.DELETE,
			Optional.empty(),
			Instant.now()
		);
	}
}
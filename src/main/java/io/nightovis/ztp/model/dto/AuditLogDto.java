package io.nightovis.ztp.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.nightovis.ztp.model.AuditOperation;
import io.nightovis.ztp.model.ResourceType;

import java.time.Instant;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public record AuditLogDto<T>(
	String resourceId,
	ResourceType resourceType,
	AuditOperation operation,
	Optional<T> resource,
	Instant timestamp
) {}

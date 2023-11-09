package io.nightovis.ztp;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record Problem(
	int statusCode,
	String error,
	String details,
	Set<Violation> violations
) {

	public String serialize() {
		return JsonMapper.serialize(this);
	}

	public record Violation(
		String field,
		String message,
		Object rejectedValue
	) {

		static <T> Violation fromConstraintViolation(ConstraintViolation<T> violation) {
			return new Violation(
				violation.getPropertyPath().toString(), violation.getMessage(), violation.getInvalidValue());
		}
	}
}

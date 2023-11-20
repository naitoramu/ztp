package io.nightovis.ztp.problem;

import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.stream.Collectors;

public class Problems {

	public static <T> Problem validationError(Set<ConstraintViolation<T>> violations) {
		return new Problem(
			HttpStatus.BAD_REQUEST,
			"Validation error",
			"Submitted request is invalid. See violations below for more information",
			violations.stream().map(Problem.Violation::fromConstraintViolation).collect(Collectors.toSet())
		);
	}

	public static Problem invalidArgumentError(Set<Problem.Violation> violations) {
		return new Problem(
			HttpStatus.BAD_REQUEST,
			"Validation error",
			"Submitted request is invalid. See violations below for more information",
			violations
		);
	}

	public static Problem resourceNotFound(String resourceName, String resourceId) {
		return new Problem(
			HttpStatus.NOT_FOUND,
			"Resource not found",
			"The system could not find " + resourceName + " with ID = " + resourceId,
			null
		);
	}

	public static Problem badRequest(String details) {
		return new Problem(
			HttpStatus.BAD_REQUEST,
			"Bad request",
			details,
			null
		);
	}

	public static Problem pathNotFound(String path) {
		return new Problem(
			HttpStatus.NOT_FOUND,
			"Not found",
			"The system could not find path '" + path + "'",
			null
		);
	}

	public static Problem exceedingAvailableQuantity(long id, long quantity, long availableQuantity) {
		return new Problem(
			HttpStatus.NOT_ACCEPTABLE,
			"Excessive quantity",
			"Quantity = " + quantity + " is to much for product with ID = " + id + ". Available quantity: " + availableQuantity,
			null
		);
	}

	public static Problem internalServerError() {
		return new Problem(
			HttpStatus.INTERNAL_SERVER_ERROR,
			"Internal Server Error",
			"Unexpected error occurred. Please try again in a while.",
			null
		);
	}
}
package io.nightovis.ztp;

import jakarta.validation.ConstraintViolation;

import java.net.HttpURLConnection;
import java.util.Set;
import java.util.stream.Collectors;

public class Problems {

	public static <T> Problem validationError(Set<ConstraintViolation<T>> violations) {
		return new Problem(
			HttpURLConnection.HTTP_BAD_REQUEST,
			"Validation error",
			"Submitted request is invalid. See violations below for more information",
			violations.stream().map(Problem.Violation::fromConstraintViolation).collect(Collectors.toSet())
		);
	}

	public static Problem resourceNotFound(String resourceName, long resourceId) {
		return new Problem(
			HttpURLConnection.HTTP_NOT_FOUND,
			"Resource not found",
			"The system could not find " + resourceName + " with ID = " + resourceId,
			null
		);
	}

	public static Problem badRequest(String details) {
		return new Problem(HttpURLConnection.HTTP_BAD_REQUEST, "Bad request", details, null);
	}

	public static Problem pathNotFound(String path) {
		return new Problem(
			HttpURLConnection.HTTP_NOT_FOUND,
			"Not found",
			"The system could not find path '" + path + "'",
			null
		);
	}
}

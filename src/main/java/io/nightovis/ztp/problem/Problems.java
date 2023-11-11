package io.nightovis.ztp.problem;

import io.nightovis.ztp.api.servlet.util.HttpMethod;
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

	public static Problem badMethod(HttpMethod method) {
		return new Problem(
			HttpURLConnection.HTTP_BAD_METHOD,
			"Bad method",
			"Http method '" + method.toString() + "' not allowed.",
			null
		);
	}

	public static Problem exceedingAvailableQuantity(long id, long quantity, long availableQuantity) {
		return new Problem(
			422,
			"Excessive quantity",
			"Quantity = " + quantity + " is to much for product with ID = " + id + ". Available quantity: " + availableQuantity,
			null
		);
	}
}

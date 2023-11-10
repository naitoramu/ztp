package io.nightovis.ztp.util;

import io.nightovis.ztp.problem.ProblemOccurredException;
import io.nightovis.ztp.problem.Problems;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class Validation {
	private static final Validator validator = getValidator();

	private static Validator getValidator() {
			try(ValidatorFactory validatorFactory = jakarta.validation.Validation.buildDefaultValidatorFactory()) {
				return validatorFactory.getValidator();
			}
	}

	public static <T> void validate(T dto) {
		Set<ConstraintViolation<T>> violations = validator.validate(dto);
		if (!violations.isEmpty()) {
			throw new ProblemOccurredException(Problems.validationError(violations));
		}
	}
}

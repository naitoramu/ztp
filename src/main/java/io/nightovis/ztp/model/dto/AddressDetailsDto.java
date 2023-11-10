package io.nightovis.ztp.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressDetailsDto(
	@NotNull
	@NotBlank
	@Size(min = 1, max = 64)
	String firstName,

	@NotNull
	@NotBlank
	@Size(min = 1, max = 64)
	String lastName,

	@NotNull
	@NotBlank
	@Size(min = 1, max = 256)
	String address,

	@NotNull
	@NotBlank
	@Size(min = 6, max = 6)
	String postalCode,

	@NotNull
	@NotBlank
	@Size(min = 1, max = 64)
	String city
) {
}

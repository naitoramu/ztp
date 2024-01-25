package io.nightovis.ztp.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductDto(

	String id,

	@NotNull
	@NotBlank
	@Size(min = 1, max = 256)
	String name,

	@NotBlank
	@Size(min = 1, max = 1024)
	String description,

	@NotNull
	Double price,

	@NotNull
	Long availableQuantity

) {}
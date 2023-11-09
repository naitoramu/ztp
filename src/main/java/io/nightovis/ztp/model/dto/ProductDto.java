package io.nightovis.ztp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductDto(

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	long id,

	@NotNull
	@NotBlank
	@Size(min = 1, max = 256)
	String name,

	@NotBlank
	@Size(min = 1, max = 1024)
	String description,

	double price,

	long availableQuantity

) {}

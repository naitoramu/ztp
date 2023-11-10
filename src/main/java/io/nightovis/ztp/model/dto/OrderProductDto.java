package io.nightovis.ztp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record OrderProductDto(

	@NotNull
	long id,

	@NotNull
	long quantity,

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	String name,

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	String description,

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	double cost

) {
}

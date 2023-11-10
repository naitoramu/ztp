package io.nightovis.ztp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.nightovis.ztp.model.ShippingMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record OrderDto(

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	long id,

	@Valid
	AddressDetailsDto addressDetails,

	@NotNull
	@Valid
	@Size(min = 1)
	Set<OrderProductDto> orderProducts,

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	double totalCost,

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	double shippingCost,

	@NotNull
	ShippingMethod shippingMethod

) {
}

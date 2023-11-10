package io.nightovis.ztp.model.domain;

public record AddressDetails(
	String firstName,
	String lastName,
	String address,
	String postalCode,
	String city
) {
}

package io.nightovis.ztp.model.domain;

import java.util.Objects;
import java.util.Set;

public final class Order {
	private final long id;
	private final AddressDetails addressDetails;
	private final Set<OrderProduct> orderProducts;
	private final double shippingCost;
	private final Deliverer deliverer;

	private double totalCost;

	public Order(long id, AddressDetails addressDetails, Set<OrderProduct> orderProducts,
	             double totalCost, double shippingCost, Deliverer deliverer) {

		this.id = id;
		this.addressDetails = addressDetails;
		this.orderProducts = orderProducts;
		this.totalCost = totalCost;
		this.shippingCost = shippingCost;
		this.deliverer = deliverer;
	}

	public long id() {
		return id;
	}

	public AddressDetails addressDetails() {
		return addressDetails;
	}

	public Set<OrderProduct> orderProducts() {
		return orderProducts;
	}

	public double shippingCost() {
		return shippingCost;
	}

	public Deliverer deliverer() {
		return deliverer;
	}

	public double totalCost() {
		return totalCost;
	}

	public Order totalCost(double totalCost) {
		this.totalCost = totalCost;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Order) obj;
		return this.id == that.id &&
			Objects.equals(this.addressDetails, that.addressDetails) &&
			Objects.equals(this.orderProducts, that.orderProducts) &&
			Double.doubleToLongBits(this.totalCost) == Double.doubleToLongBits(that.totalCost) &&
			Double.doubleToLongBits(this.shippingCost) == Double.doubleToLongBits(that.shippingCost) &&
			Objects.equals(this.deliverer, that.deliverer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, addressDetails, orderProducts, totalCost, shippingCost, deliverer);
	}

	@Override
	public String toString() {
		return "Order[" +
			"id=" + id + ", " +
			"addressDetails=" + addressDetails + ", " +
			"orderProducts=" + orderProducts + ", " +
			"totalCost=" + totalCost + ", " +
			"shippingCost=" + shippingCost + ", " +
			"deliverer=" + deliverer + ']';
	}

}

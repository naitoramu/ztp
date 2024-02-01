package io.nightovis.ztp.model.domain;

import java.net.URL;
import java.util.Objects;

public final class Product {
	private String id;
	private final String name;
	private final String description;
	private final URL imgUrl;
	private final double price;
	private final long availableQuantity;

	public Product(String id, String name, String description, URL imgUrl, double price, long availableQuantity) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.imgUrl = imgUrl;
		this.price = price;
		this.availableQuantity = availableQuantity;
	}

	public String id() {
		return id;
	}

	public Product id(String id) {
		this.id = id;
		return this;
	}

	public String name() {
		return name;
	}

	public String description() {
		return description;
	}

	public URL imgUrl() {
		return imgUrl;
	}

	public double price() {
		return price;
	}

	public long availableQuantity() {
		return availableQuantity;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Product) obj;
		return Objects.equals(this.id, that.id) &&
			Objects.equals(this.name, that.name) &&
			Objects.equals(this.description, that.description) &&
			Objects.equals(this.imgUrl, that.imgUrl) &&
			Double.doubleToLongBits(this.price) == Double.doubleToLongBits(that.price) &&
			this.availableQuantity == that.availableQuantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, imgUrl, price, availableQuantity);
	}

	@Override
	public String toString() {
		return "Product[" +
			"id=" + id + ", " +
			"name=" + name + ", " +
			"description=" + description + ", " +
			"imgUrl=" + imgUrl + ", " +
			"price=" + price + ", " +
			"availableQuantity=" + availableQuantity + ']';
	}
}

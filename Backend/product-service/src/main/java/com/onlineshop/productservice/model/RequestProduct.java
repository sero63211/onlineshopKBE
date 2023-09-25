package com.onlineshop.productservice.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestProduct {

	@NotBlank(message = "Please Enter the Product name")
	@JsonProperty("productName")
	private String productName;

	@NotBlank(message = "Please Enter the description")
	@JsonProperty("description")
	private String description;

	@NotNull(message = "stock information can't empty")
	@JsonProperty("stock")
	private long stock;

	@NotBlank(message = "unit information can't empty")
	@JsonProperty("unit")
	private String unit;

	@NotNull(message = "categoryId can't empty")
	@JsonProperty("categoryId")
	private long categoryId;

	@NotNull(message = "userId can't empty")
	@JsonProperty("userId")
	private long userId;

	@NotNull(message = "price can't empty")
	@JsonProperty("price")
	private double price;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

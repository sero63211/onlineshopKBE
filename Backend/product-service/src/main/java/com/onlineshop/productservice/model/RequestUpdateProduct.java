package com.onlineshop.productservice.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestUpdateProduct {

	@NotBlank
	@JsonProperty("productName")
	private String productName;

	@NotBlank(message = "Please Enter the description")
	@JsonProperty("description")
	private String description;

	@NotNull
	@JsonProperty("stock")
	private Long stock;

	@NotBlank
	@JsonProperty("unit")
	private String unit;
	@NotNull
	@JsonProperty("price")
	private double price;

	@NotNull
	@JsonProperty("categoryId")
	private Long categoryId;

	@NotNull
	@JsonProperty("userId")
	private Long userId;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}

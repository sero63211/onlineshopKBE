package com.onlineshop.orderservice.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RequestCart {

	@NotNull
	private Long productId;
	
	
	@NotNull
	private int quantity;
	

	 @NotBlank(message = "Order status can't empty")
	 private String orderStatus;
	 
	 @NotNull
		private Long userId;
	 
	 

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	

}

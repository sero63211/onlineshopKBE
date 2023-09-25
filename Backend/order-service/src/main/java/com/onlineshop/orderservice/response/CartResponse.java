package com.onlineshop.orderservice.response;

public class CartResponse {

	//private double totalPrice;

	private int quantity;

	private String orderStatus;
	
	private long cartId;
	
	private long productId;
	

//	public double getTotalPrice() {
//		return totalPrice;
//	}
//
//	public void setTotalPrice(double totalPrice) {
//		this.totalPrice = totalPrice;
//	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	 
}

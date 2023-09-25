package com.onlineshop.orderservice.model;
  
 
import javax.validation.constraints.NotNull;
 
public class RequestItems {
	
	
	@NotNull(message = "Please give quantity Value")
	private int quantity;
	
	@NotNull(message = "Product id is missing")
	private Long productId;
	
	@NotNull(message = "User id is missing")
	private Long userId;
	
	 
	private Long cartId;
	
	
	
	
	
	 
	 

	public Long getCartId() {
		return cartId;
	}


	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


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


	 
	
	


}

package com.onlineshop.orderservice.exception;

import lombok.Data;

@Data
public class ProductNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	
	private String message;
	
	
	public String getMessage() {
		return message;
	}
	
	
	
	

	public ProductNotFoundException(String message) {
		super(message);
		this.message = message;
	}





	public void setMessage(String message) {
		this.message = message;
	}

	private static final long serialVersionUID = 1L;
	
	public ProductNotFoundException () {
		
	}
	 
	 
	
	public ProductNotFoundException (String message, Throwable cause) {
		 super(message, cause);
	}
	
	public ProductNotFoundException (Throwable cause) {
		 super(cause);
	}
	
	

}

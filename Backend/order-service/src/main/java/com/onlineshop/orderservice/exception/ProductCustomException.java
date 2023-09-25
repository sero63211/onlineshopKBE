package com.onlineshop.orderservice.exception;

public class ProductCustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;


	public ProductCustomException(String errorcode) {
		super(errorcode);
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
}

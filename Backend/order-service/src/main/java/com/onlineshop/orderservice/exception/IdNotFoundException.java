package com.onlineshop.orderservice.exception;

public class IdNotFoundException extends  RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	 private String errorCode;


	public IdNotFoundException(String errorCode) {
		super(errorCode);
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}


	 
}

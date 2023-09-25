package com.onlineshop.orderservice.exception;

public class OrderCustomException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String errorCode;
	private int status;

	    public OrderCustomException(String message, String errorCode, int status) {
	        super(errorCode);
	       
	        
	    }

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

}

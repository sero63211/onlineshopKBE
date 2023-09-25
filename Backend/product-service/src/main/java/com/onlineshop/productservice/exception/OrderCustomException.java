package com.onlineshop.productservice.exception;

public class OrderCustomException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String errorCode;
	private int status;

	    public OrderCustomException(String message, String errorCode, int status) {
	        super(errorCode);
	       
	        this.setErrorCode(errorCode);
	        this.setStatus(status);
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

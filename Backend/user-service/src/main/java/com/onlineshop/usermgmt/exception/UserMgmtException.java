package com.onlineshop.usermgmt.exception;

public class UserMgmtException  extends RuntimeException	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String message;


	public UserMgmtException(String message) {
		super(message);
		this.message = message;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
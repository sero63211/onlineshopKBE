package com.onlineshop.orderservice.exception;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
public class ErrorResponse {
	
	private String statuserrorCode;
	private String message;
	
	 private  long timeStamp;

	  private List<String> errors;

	    public ErrorResponse(String message, List<String> errors , long timeStamp) {
	        super();
	        this.message = message;
	        this.errors = errors;
	        this.timeStamp=timeStamp;
	    }

	    public ErrorResponse(String message, String error, long timestamp) {
	        super();
	        
	        this.message = message;
	        errors = Arrays.asList(error);
	        this.timeStamp=timeStamp;
	    }
}

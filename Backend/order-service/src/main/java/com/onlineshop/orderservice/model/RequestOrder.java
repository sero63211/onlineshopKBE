package com.onlineshop.orderservice.model;

 

 
 
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
 
@NoArgsConstructor
@Data
public class RequestOrder {
	
	@NotNull(message = "cartId size is missing")
	private Long cartId;
	  
	@NotNull(message = "userId size is missing")
	private Long userId;
	 
	private Long productId;
	
	@NotNull(message = "quantity size is missing")
	private int quantity;
	
	@NotNull(message = "orderPayId size is missing")
	private Long orderPayId;
	
	private LocalDateTime dateTime=LocalDateTime.now();
	 
	 
	
	
	 
	
	
	
	
	
}

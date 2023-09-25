package com.onlineshop.orderservice.dto;

import lombok.Data;

@Data
public class OrderPayment {
	
	private Long orderPayId;

	private String paymentOption;

	private String payerId;

	private String paymentStatus;
	
	private String transactionId;
	 
}

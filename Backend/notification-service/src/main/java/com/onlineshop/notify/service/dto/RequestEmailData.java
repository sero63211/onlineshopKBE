package com.onlineshop.notify.service.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RequestEmailData {
	private String username;
	
	private CartDto cart;

	private double totalAmount;
	
	private String paymentOption;
	
	private String email;

	private String payerId;

	private String paymentStatus;
	
	private String transactionId;
}

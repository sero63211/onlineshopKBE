package com.onlineshop.orderservice.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderDto {
	private Long orderId;
	
	private LocalDateTime dateTime;

	private double totalAmount;

	private CartDto cart;

	private long user;
	
	private long orderPayment;
}

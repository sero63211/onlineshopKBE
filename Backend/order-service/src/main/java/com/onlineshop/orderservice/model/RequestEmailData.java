package com.onlineshop.orderservice.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.onlineshop.orderservice.entity.Cart;

import lombok.Data;

@Data
public class RequestEmailData {
	private String username;
	
	private Cart cart;

	private double totalAmount;
	
	private String paymentOption;
	
	private String email;

	private String payerId;

	private String paymentStatus;
	
	private String transactionId;
}

package com.onlineshop.orderservice.model;

import lombok.Data;

@Data
public class RequestPaymentModel {
	private long userId;
	private double amount;
}

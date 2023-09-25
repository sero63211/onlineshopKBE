package com.onlineshop.orderservice.response;

import lombok.Data;

@Data
public class SellerPaymentResponse {
	private double overAll;
	private double pending;
	private double recieved;
	
}

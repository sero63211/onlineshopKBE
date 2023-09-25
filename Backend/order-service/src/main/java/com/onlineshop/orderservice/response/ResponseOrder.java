package com.onlineshop.orderservice.response;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseOrder {
	
	
	private LocalDateTime dateTime;
	
	private double totalAmount;
	
	private String productName;
	
	private int quantity;
	
	private double totalPrice;
	
	 
	private double price;
	
	//	private List<byte[]> imageData;

	
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	 

	 

	 
	
	
	public ResponseOrder()
	{
		
	}
	

}

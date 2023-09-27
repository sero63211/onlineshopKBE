package com.onlineshop.notify.service.dto;

import java.time.LocalDateTime;
import java.util.Locale.Category;

import lombok.Data;

@Data
public class Product {

	private Long productId;

	private String productName;
	
	private String description;

	private Long stocks;

	private String unit;

	private double price;

	private LocalDateTime addedAt;
	
	private long seller;

	private Category category;

	private byte[] imageData;
	
	private String status;
	 
}

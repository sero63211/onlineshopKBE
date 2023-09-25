package com.onlineshop.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties
public class Category {
	private Long categoryId;
	
	private String categoryName;
	
	private String status;
	
	
}

package com.onlineshop.productservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductResponse {
	
	private long productId;
	
	private String ProductName;
	
	
	private long stock;
	 
	private String unit;
	
	private long categoryId;
	
	private String categoryname;
	
	private double price;
	
	private byte[] data;
	 
	private String status;



}
	  
	
	
	
	 
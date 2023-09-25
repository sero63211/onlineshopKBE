package com.onlineshop.productservice.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestCategory {
	
	@NotNull
	private Long userId;
	 
	@NotBlank(message = "category name can't empty")
	private String categoryName;
	
	 

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	 

	
 

	
	
	
	
	
	
	
	
	
	
	
}

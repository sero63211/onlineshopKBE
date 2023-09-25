package com.onlineshop.productservice.service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.model.RequestCategory;

@Service
public interface CategoryService {
	
	public Category saveCategory(RequestCategory requestCategory);
	 
	public Category getCategory(Long id);
	
	public Category updatecategory(RequestCategory Category, Long id);

	public void deleteById(Long id, Long userId);
 
	public List<Category> findbyOrder(Long userId);
	
	public Category findByCategory(Long id);
		
}

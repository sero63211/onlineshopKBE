package com.onlineshop.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.entity.Product;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
	
	
	 
	public List<Category> findAllByOrderByCategoryNameAsc();
	
	 
	public List<Category> findByCategoryNameContaining(String name);

}

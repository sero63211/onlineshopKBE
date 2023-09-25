package com.onlineshop.productservice.repository;

import java.util.List;
import java.util.OptionalDouble;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineshop.productservice.entity.Product;

public interface IProductMappingRespository extends JpaRepository<Product, Long> {

 
	 
	public List<Product> findAllBySellerOrderByAddedAtDesc(long sellerId);
 
	public Product findByProductName(String productName);
	
	  
	public List<Product> findBySellerAndProductNameContainingOrCategoryCategoryNameContaining(long sellerId, String name, String category);
	
	
	public List<Product> findAllByStatus(String status);

	public List<Product> findAllByStatusOrderByAddedAtDesc(String string);

	public List<Product> findAllByStatusAndProductNameContainingOrStatusAndCategoryCategoryNameContaining(String string,
			String pName, String string2, String pName2);

	public Product findByProductId(Long productId);
	
	

}

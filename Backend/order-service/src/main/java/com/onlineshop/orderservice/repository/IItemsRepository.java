package com.onlineshop.orderservice.repository;

 
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineshop.orderservice.entity.Items;
 

public interface IItemsRepository extends JpaRepository<Items, Long> {
	
	  
	
	 public Set<Items> findByCartCartId(Long cartId);
	 
	 
	 public Items findByProductAndCartCartId(long productId, Long cartId);
	 
	 

}

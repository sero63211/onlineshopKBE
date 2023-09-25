package com.onlineshop.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlineshop.orderservice.entity.Cart;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
	
	 
	
	
 		@Query(value = "select * from cart where user=:userId and order_status=:orderStatus", nativeQuery = true)
	public Cart findByOrderStatusAndUser(@Param("userId") Long userId, @Param("orderStatus") String orderStatus);
	
	
   @Query(value = "select * from cart where user=:userid and order_status=:status", nativeQuery = true)
	public List<Cart> findAllByOrderStatusAndUser(@Param("userid") Long userid, @Param("status") String status);
	
  
 
	
	
}

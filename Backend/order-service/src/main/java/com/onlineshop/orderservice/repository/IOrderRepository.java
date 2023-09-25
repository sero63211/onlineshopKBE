package com.onlineshop.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlineshop.orderservice.entity.Orders;

public interface IOrderRepository extends JpaRepository<Orders, Long> {
	
	public List<Orders> findAllByUserOrderByDateTimeDesc(Long userId);

	public List<Orders> findAllByOrderByDateTimeDesc();

}

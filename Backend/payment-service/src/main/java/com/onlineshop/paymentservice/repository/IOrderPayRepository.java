package com.onlineshop.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineshop.paymentservice.entity.OrderPayment;

@Repository
public interface IOrderPayRepository extends JpaRepository<OrderPayment, Long> {

	 
}

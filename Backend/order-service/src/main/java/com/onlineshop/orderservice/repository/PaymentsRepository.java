package com.onlineshop.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineshop.orderservice.entity.Payments;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
	//Payments findBySellerId(long userId);

	Payments findBySeller(long seller);
}

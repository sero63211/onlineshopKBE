package com.onlineshop.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineshop.orderservice.entity.SellerPayment;

@Repository
public interface SellerPaymentRepository extends JpaRepository<SellerPayment, Long>{

	List<SellerPayment> findAllBySellerOrderByOrdersDateTimeDesc(long userId);

}

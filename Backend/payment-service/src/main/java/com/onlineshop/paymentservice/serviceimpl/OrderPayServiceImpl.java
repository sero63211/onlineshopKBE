package com.onlineshop.paymentservice.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineshop.paymentservice.entity.OrderPayment;
import com.onlineshop.paymentservice.model.RequestOrderPay;
import com.onlineshop.paymentservice.repository.IOrderPayRepository;
import com.onlineshop.paymentservice.service.OrderPayService;

@Service
public class OrderPayServiceImpl implements OrderPayService {
	
	@Autowired 
	IOrderPayRepository orderPayRepo;
	
	
	 
	public OrderPayment saveOrderPay(RequestOrderPay requestOrderPay) {
		 
		OrderPayment orderPayment = new OrderPayment();
		
		orderPayment.setPaymentOption(requestOrderPay.getPaymentOption());
		orderPayment.setTransactionId(requestOrderPay.getTransactionId());
		orderPayment.setPayerId(requestOrderPay.getPayerId());
		orderPayment.setPaymentStatus(requestOrderPay.getPaymentStatus());
		
		return orderPayRepo.save(orderPayment);
	}

 

}

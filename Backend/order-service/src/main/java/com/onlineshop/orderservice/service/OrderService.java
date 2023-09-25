package com.onlineshop.orderservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.onlineshop.orderservice.dto.OrderDto;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.entity.Orders;
import com.onlineshop.orderservice.entity.Payments;
import com.onlineshop.orderservice.entity.SellerPayment;
import com.onlineshop.orderservice.model.RequestOrderPay;
import com.onlineshop.orderservice.model.RequestPaymentModel;

@Service
public interface OrderService {
	
	public Orders placeOrder(RequestOrderPay requestOrder);
	
	public void cancelOrder(long id) throws Exception;
	
 
	
	public List<OrderDto> getAllOrders(long userId);
	
	 
	
	
	public List<Cart> getAllorderInActive(long userId);

	public Map<String, List<SellerPayment>> getAllordersBySeller(long userId);

	public Payments getAllPaymentsBySeller(long userId);

	public Payments sendPaymentsToSeller(RequestPaymentModel data);

	public List<Orders> getAllordersForAdmin();
	
	
}

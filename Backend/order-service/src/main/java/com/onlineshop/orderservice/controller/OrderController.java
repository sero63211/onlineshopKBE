package com.onlineshop.orderservice.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.orderservice.dto.OrderDto;
import com.onlineshop.orderservice.entity.Orders;
import com.onlineshop.orderservice.entity.Payments;
import com.onlineshop.orderservice.entity.SellerPayment;
import com.onlineshop.orderservice.model.RequestOrderPay;
import com.onlineshop.orderservice.model.RequestPaymentModel;
import com.onlineshop.orderservice.service.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {

	@Autowired
	OrderService orderService;

	private static final Logger logger = LogManager.getLogger(OrderController.class);

	@PostMapping 
	public ResponseEntity<Orders> saveOrder(@Valid @RequestBody RequestOrderPay resquestOrder) {
	 	logger.info("Place the order given item Quantity={}, cartId={}", resquestOrder.getDateTime(),
		 	resquestOrder.getCartId());
	 	Orders placeOrder = orderService.placeOrder(resquestOrder);
		return new ResponseEntity<Orders>(placeOrder, HttpStatus.CREATED); 
	}
 

	@GetMapping("/{userId}")
	public ResponseEntity<List<OrderDto>> getAllorders(@PathVariable("userId") long userId) {
		List<OrderDto> orders = orderService.getAllOrders(userId);
		logger.info("To get all orders", orders.size());
		return new ResponseEntity<List<OrderDto>>(orders, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Orders>> getAllordersForAdmin() {
		List<Orders> orders = orderService.getAllordersForAdmin();
		logger.info("To get all orders", orders.size());
		return new ResponseEntity<List<Orders>>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/seller/{userId}")
	public ResponseEntity<Map<String, List<SellerPayment>>> getAllordersBySeller(@PathVariable("userId") long userId) {
		Map<String, List<SellerPayment>> orders = orderService.getAllordersBySeller(userId);
		logger.info("To get all orders", orders.size());
		return new ResponseEntity<Map<String, List<SellerPayment>>>(orders, HttpStatus.OK);
	}

 
	@GetMapping("/seller/payment/{userId}")
	public ResponseEntity<Payments> getAllPaymentsBySeller(@PathVariable("userId") long userId) {
		Payments orders = orderService.getAllPaymentsBySeller(userId);
		return new ResponseEntity<Payments>(orders, HttpStatus.OK);
	}
	
	
	@PostMapping("/admin/payment")
	public ResponseEntity<Payments> sendPaymentToSeller(@RequestBody RequestPaymentModel data) {
		Payments orders = orderService.sendPaymentsToSeller(data);
		return new ResponseEntity<Payments>(orders, HttpStatus.OK);
	}
	

}

package com.onlineshop.orderservice.service;

import org.springframework.stereotype.Service;

import com.onlineshop.orderservice.dto.CartDto;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.response.ResponseCart;

@Service
public interface CartService {

	
	public Cart saveCart(RequestItems requestitem);
	
	
	
	public CartDto getCartByUser(ResponseCart responseCart);

	 
	
	
}

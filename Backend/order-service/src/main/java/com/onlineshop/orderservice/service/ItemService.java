package com.onlineshop.orderservice.service;

 

import org.springframework.stereotype.Service;

import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.model.RequestItems;

@Service
public interface ItemService {
	
	 
	
	public void deleteByid(long itemId, long cartId, long userId);
	
//	public Items getItemsByid(long id) throws Exception;
	
  
	public Items updatecategory(RequestItems items, long id);
	
}

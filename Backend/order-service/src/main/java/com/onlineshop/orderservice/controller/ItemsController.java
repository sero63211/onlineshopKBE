package com.onlineshop.orderservice.controller;
 

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.service.ItemService;

@RestController
@CrossOrigin("*")
@RequestMapping("/order/items")
public class ItemsController {

	@Autowired
	ItemService itemservice;

	private static final Logger logger = LogManager.getLogger(ItemsController.class);

	 
	
	@DeleteMapping("/{itemId}/{cartid}/{userId}")
	public ResponseEntity<?> deleteItembyid(@PathVariable("itemId") long itemId, @PathVariable("cartid")
	long cartid, @PathVariable("userId") long userId) {
		logger.info("To get item data={}", itemId);
			itemservice.deleteByid(itemId, cartid, userId);
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateitems(@PathVariable("id") long id, @RequestBody RequestItems item) {
		Items items = itemservice.updatecategory(item, id);
		logger.info("update the item" + id, items.getQuantity());
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

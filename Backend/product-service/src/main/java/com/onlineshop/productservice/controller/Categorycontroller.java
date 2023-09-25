package com.onlineshop.productservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.model.RequestCategory;
import com.onlineshop.productservice.service.CategoryService;

@RestController
@RequestMapping("/product/category")
@CrossOrigin("*")
public class Categorycontroller {

	@Autowired
	CategoryService categoryservice;

	private static final Logger logger = LogManager.getLogger(Categorycontroller.class);

	@PostMapping
	public ResponseEntity<Category> saveCategory(@Valid @RequestBody RequestCategory resquestCategory) {

		logger.info("add to category category={}", resquestCategory.getCategoryName());

		return new ResponseEntity<>(categoryservice.saveCategory(resquestCategory), HttpStatus.CREATED);
	}

	@GetMapping("/ordercategory/{userId}")
	public ResponseEntity<List<Category>> getbyfiltercategory(@PathVariable("userId") long userId) {
		
		List<Category> categoryList = categoryservice.findbyOrder(userId);
		
		logger.info("add to category category={}", categoryList.size());
		
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getByid(@PathVariable("id") Long id) {

	 

		return new ResponseEntity<Object>(categoryservice.getCategory(id), HttpStatus.OK);

	}

 

	@DeleteMapping("/{id}/{userId}")
	public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id, @PathVariable("userId") long userId) {
			
		categoryservice.deleteById(id, userId);
		 
		logger.info("delete the category and user id" + id, userId);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@Valid @RequestBody RequestCategory category,
			@PathVariable("id") long id) {
		logger.info("before update the category" + id, category.getCategoryName());
		
  
		return new ResponseEntity<>(categoryservice.updatecategory(category, id), HttpStatus.OK);
	}

 

}

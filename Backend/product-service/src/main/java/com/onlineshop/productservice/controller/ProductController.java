package com.onlineshop.productservice.controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.onlineshop.productservice.entity.Product;
import com.onlineshop.productservice.model.RequestApproveData;
import com.onlineshop.productservice.response.ProductResponse;
import com.onlineshop.productservice.service.ProductService;

@RestController

@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {

	@Autowired
	ProductService productService;

	private static final Logger logger = LogManager.getLogger(ProductController.class);

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Product> save(@Valid @RequestPart("data") String resquestProduct,
			@RequestPart(value = "imagefile") MultipartFile file)
			throws IOException, SQLIntegrityConstraintViolationException {
		logger.info("save new product - resquestProduct= {}, file={} ", resquestProduct, file.getOriginalFilename());
		Product saveProduct = productService.saveProduct(resquestProduct, file);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveProduct);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getByid(@PathVariable("id") long id) {
		logger.info("getProductById  productId : " + id);
		return new ResponseEntity<Object>(productService.getProductById(id), HttpStatus.OK);

	}

	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Product> updateProduct(@Valid @RequestPart("data") String resquestProduct,
			@RequestPart(value = "imagefile", required = false) MultipartFile file, @PathVariable("id") long id)
			throws Exception {
		logger.info("update product - resquestProduct= {}, file={} ", resquestProduct);

		return new ResponseEntity<Product>(productService.updateProduct(resquestProduct, file, id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}/{userId}")
	public ResponseEntity<Void> deleteproduct(@PathVariable("id") long id, @PathVariable("userId") long userId) {
		logger.info("delete product ={} : " + id);
		productService.deletebyId(id, userId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

	@GetMapping("/search")
	public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam("productName") String productName, @RequestParam("userId") Long userId) {
		logger.info("search by product name ={} : " + productName);
		List<ProductResponse> productResponseList = productService.filterbyId(productName, userId);
		logger.info("get all product ={} : " + productResponseList.size());
		return new ResponseEntity<List<ProductResponse>>(productResponseList, HttpStatus.OK);
	}

	@GetMapping("/pendingProduct")
	public ResponseEntity<List<ProductResponse>> getAllPendingProduct() {
		logger.info("get all pending status product");
		List<ProductResponse> productResponseList = productService.getAllPendingPoduct();
		logger.info("get all product ={} : " + productResponseList.size());
		return new ResponseEntity<List<ProductResponse>>(productResponseList, HttpStatus.OK);

	}
	
//	@PostMapping("/reviews")
//	public ResponseEntity<Product> saveReview(@Valid @RequestBody RequestFeedback feedback) {
//		logger.info("get all pending status product");
//		Product productResponse = productService.saveReview(feedback);
//		logger.info("get all product ={} : " + productResponse);
//		return new ResponseEntity<>(productResponse, HttpStatus.OK);
//
//	}
//	
//	@PostMapping("/ratings")
//	public ResponseEntity<Product> saveReview(@Valid @RequestBody RequestRatings rating) {
//		logger.info("get all pending status product");
//		Product productResponse = productService.saveRating(rating);
//		logger.info("get all product ={} : " + productResponse);
//		return new ResponseEntity<>(productResponse, HttpStatus.OK);
//
//	}

	@PutMapping
	public ResponseEntity<?> setProductApproved(@Valid @RequestBody RequestApproveData requestvalue) {

		return new ResponseEntity<Product>(productService.setStatusApproveProduct(requestvalue.getProductId(),
				requestvalue.getUserId(), requestvalue.getStatus()), HttpStatus.OK);
	}

}

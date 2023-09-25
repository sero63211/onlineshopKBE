package com.onlineshop.productservice.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.onlineshop.productservice.dto.User;
import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.entity.Product;
import com.onlineshop.productservice.exception.ProductCustomException;
import com.onlineshop.productservice.exception.ProductNotFoundException;
import com.onlineshop.productservice.model.RequestCategory;
import com.onlineshop.productservice.repository.ICategoryRepository;
import com.onlineshop.productservice.serviceimpl.CategoryServiceimpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class CategoryServiceimplTest {
	
	@InjectMocks
	private CategoryServiceimpl categoryService;

	@Mock
	private ICategoryRepository categoryRepo;
	
	@Mock
	private ProductService productService;
	
	@Test
	void saveCategory() throws Exception {
		Mockito.when(productService.isSeller(any(Long.class))).thenReturn(true);
		Mockito.when(categoryRepo.save(any(Category.class))).thenReturn(getCategory());
		Category dto = categoryService.saveCategory(getRequestCategory());
		assertThat(dto.getCategoryName()).isEqualTo("food");
	}
	
	@Test
	void saveCategoryWithError() throws Exception {
		Mockito.when(productService.isSeller(any(Long.class))).thenReturn(false);
		Mockito.when(categoryRepo.save(any(Category.class))).thenReturn(getCategory());
		ProductCustomException error = Assertions.assertThrows(ProductCustomException.class, () -> {
			categoryService.saveCategory(getRequestCategory());
		});
		assertThat(error.getMessage()).isEqualTo("You are a not a admin");
	}
	
	@Test
	void deleteCategoryById() throws Exception {
		Mockito.when(productService.isSeller(any(Long.class))).thenReturn(true);
		Mockito.when(categoryRepo.findById(any(Long.class))).thenReturn(Optional.of(getCategory1()));
		Mockito.doNothing().when(categoryRepo).deleteById(any(Long.class));
		
		NullPointerException error = Assertions.assertThrows(NullPointerException.class, () -> {
			categoryService.deleteById(1l, 1l);
		});
	}	
	
	@Test
	void getCategoryById() throws Exception {
		Mockito.when(productService.isSeller(any(Long.class))).thenReturn(false);
		Mockito.when(categoryRepo.findById(any(Long.class))).thenReturn(Optional.of(getCategory()));
		Category category = categoryService.getCategory(1l);
		assertThat(category.getCategoryName()).isEqualTo("food");
	}
	
	@Test
	void  getCategoryByIdWithError() throws Exception {
		Mockito.when(productService.isSeller(any(Long.class))).thenReturn(false);
		Mockito.when(categoryRepo.findById(any(Long.class))).thenReturn(null);
		ProductNotFoundException error = Assertions.assertThrows(ProductNotFoundException.class, () -> {
			categoryService.getCategory(1l);
		});
		//assertThat(error.getMessage()).contains("null");
	}
	
	private Product getProduct() {
		Product product = new Product();
		product.setAddedAt(LocalDateTime.now());
		product.setCategory(getCategory());
		product.setProductName("Dates");
		product.setDescription("Dates is good for health");
		product.setPrice(20);
		product.setProductId(1l);
		product.setStocks(100l);
		product.setUnit("kg");
		product.setSeller(1l);
		product.setStatus("APPROVED");
		return product;
	}
	
	
	private Category getCategory() {
		Category category = new Category();
		category.setCategoryId(1l);
		category.setCategoryName("food");
		category.setStatus("ACTIVE");
		return category;
	}
	
	private Category getCategory1() {
		Category category = new Category();
		category.setCategoryId(1l);
		category.setCategoryName("food");
		category.setStatus("ACTIVE");
		
		List<Product> productList = new ArrayList<>();
		productList.add(getProduct());
		
		return category;
	}

	private RequestCategory getRequestCategory() {
		RequestCategory category = new RequestCategory();
		category.setUserId(1l);
		category.setCategoryName("Dates");
		return category;
	}

	private User getUser() {
		User user = new User();
		user.setCreatedAt(LocalDateTime.now());
		user.setEmail("test@gmail.com");
		user.setId(1l);
		user.setName("test_user");
		user.setPhoneNumber("9999988888");
		user.setRole("seller");
		user.setUsername("test_user");
		user.setSurname("user");
		return user;
	}
	

}

package com.onlineshop.productservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.productservice.client.OnlineShopClient;
import com.onlineshop.productservice.dto.User;
import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.entity.Product;
import com.onlineshop.productservice.model.RequestProduct;
import com.onlineshop.productservice.repository.IProductMappingRespository;
import com.onlineshop.productservice.response.ProductResponse;
import org.springframework.test.context.ActiveProfiles;
import com.onlineshop.productservice.serviceimpl.ProductServiceimpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class ProductServiceimplTest {

	@InjectMocks
	private ProductServiceimpl productService;

	@Mock
	private IProductMappingRespository productRepo;
	
	@Mock
	private CategoryService categoryService;
	
	@Mock
	private OnlineShopClient onlineShopClient;
	
	@Spy
	ObjectMapper mapp = new ObjectMapper();
	
	@Test
	void saveProduct() throws Exception {
//		Mockito.when(productService.isSeller(any(Long.class))).thenReturn(true);
		Mockito.when(categoryService.getCategory(Mockito.anyLong())).thenReturn(getCategory());
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(productRepo.save(any(Product.class))).thenReturn(getProduct());
		MockMultipartFile firstFile = new MockMultipartFile("filename.txt", "filename.txt", "text/plain",
				"some xml".getBytes());
		Product dto = productService.saveProduct(mapp.writeValueAsString(getRequestProduct()), firstFile );
		assertThat(dto.getProductId()).isEqualTo(1l);
	}
	
	@Test
	void editProduct() throws Exception {
//		Mockito.when(productService.isSeller(any(Long.class))).thenReturn(true);
		Mockito.when(categoryService.getCategory(Mockito.anyLong())).thenReturn(getCategory());
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(productRepo.findByProductId(any(Long.class))).thenReturn(getProduct());
		Mockito.when(productRepo.save(any(Product.class))).thenReturn(getProduct());
		MockMultipartFile firstFile = new MockMultipartFile("filename.txt", "filename.txt", "text/plain",
				"some xml".getBytes());
		Product dto = productService.updateProduct(mapp.writeValueAsString(getRequestProduct()), firstFile,1l );
		assertThat(dto.getProductId()).isEqualTo(1l);
	}
	
	@Test
	void deleteProductById() throws Exception {
//		Mockito.when(productService.isSeller(any(Long.class))).thenReturn(true);
		Mockito.when(categoryService.getCategory(Mockito.anyLong())).thenReturn(getCategory());
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(productRepo.findByProductId(any(Long.class))).thenReturn(getProduct());
		Mockito.when(productRepo.save(any(Product.class))).thenReturn(getProduct());
		productService.deletebyId(1l,1l );
	}
	
	
	@Test
	void filterProductTest() throws Exception {
//		Mockito.when(productService.isSeller(any(Long.class))).thenReturn(true);
		Mockito.when(categoryService.getCategory(Mockito.anyLong())).thenReturn(getCategory());
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		List<Product> prodList = new ArrayList<>();
		prodList.add(getProduct());
		Mockito.when(productRepo.findAllBySellerOrderByAddedAtDesc(any(Long.class))).thenReturn(prodList);
		Mockito.when(productRepo.save(any(Product.class))).thenReturn(getProduct());
		List<ProductResponse> filterbyId = productService.filterbyId("",1l );
		assertThat(filterbyId.size()).isEqualTo(1);
	}
	
	
	private Category getCategory() {
		Category category = new Category();
		category.setCategoryId(1l);
		category.setCategoryName("food");
		category.setStatus("ACTIVE");
		return category;
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
	
	private ProductResponse getProductResponse() {
		ProductResponse product = new ProductResponse();
		product.setCategoryId(1l);
		product.setProductName("Dates");
		product.setPrice(20);
		product.setStock(100l);
		product.setUnit("kg");
		return product;
	}

	private RequestProduct getRequestProduct() {
		RequestProduct product = new RequestProduct();
		product.setCategoryId(1l);
		product.setProductName("Dates");
		product.setDescription("Dates is good for health");
		product.setPrice(20);
		product.setStock(100l);
		product.setUnit("kg");
		product.setUserId(1l);
		return product;
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






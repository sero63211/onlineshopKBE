package com.onlineshop.productservice.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.productservice.client.OnlineShopClient;
import com.onlineshop.productservice.dto.User;
import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.entity.Product;
import com.onlineshop.productservice.model.RequestCategory;
import com.onlineshop.productservice.model.RequestProduct;
import com.onlineshop.productservice.response.ProductResponse;

@SpringBootTest//(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(value=OrderAnnotation.class)
public class ProductServiceIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private OnlineShopClient onlineShopClient;
	
	
	@Test
	@Order(1)
	public void testCreateCategory() throws Exception {
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Category category = getCategory();
		mockMvc.perform(post("/product/category")
				.content(objectMapper.writeValueAsString(getRequestCategory()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	@Order(2)
	public void testCreateProduct() throws Exception {
		Product product = getProduct();
		RequestProduct request = getRequestProduct();
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		String writeValueAsBytes = objectMapper.writeValueAsString(request);
		MockMultipartFile firstFile = new MockMultipartFile("filename.txt", "filename.txt", "text/plain",
				"some xml".getBytes());
		mockMvc.perform(
				MockMvcRequestBuilders.multipart(HttpMethod.POST, "/product").file("imagefile", firstFile.getBytes())
						.file("data", writeValueAsBytes.getBytes()).contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	@Order(3)
	public void testSearchProduct() throws Exception {
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		mockMvc.perform(get("/product/search?productName=Dates&userId=1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	
	private RequestCategory getRequestCategory() {
		RequestCategory category = new RequestCategory();
		category.setUserId(1l);
		category.setCategoryName("Dates");
		return category;
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

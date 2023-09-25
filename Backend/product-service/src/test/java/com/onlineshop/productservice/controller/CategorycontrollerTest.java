package com.onlineshop.productservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.productservice.client.OnlineShopClient;
import com.onlineshop.productservice.dto.User;
import com.onlineshop.productservice.entity.Category;
import com.onlineshop.productservice.entity.Product;
import com.onlineshop.productservice.model.RequestCategory;
import com.onlineshop.productservice.response.ProductResponse;
import com.onlineshop.productservice.service.CategoryService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Categorycontroller.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategorycontrollerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	@MockBean
	private OnlineShopClient onlineShopClient;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testCreateCategory() throws Exception {
		Category category = getCategory();
		RequestCategory request = getRequestCategory();
		String writeValueAsBytes = objectMapper.writeValueAsString(request);
		System.out.println("writeValueAsBytes=" + writeValueAsBytes);
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(categoryService.saveCategory(any(RequestCategory.class))).thenReturn(getCategory());
		mockMvc.perform(post("/product/category")
				.content(objectMapper.writeValueAsString(getRequestCategory()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(category)));
	}

	@Test
	public void testUpdateProduct() throws Exception {
		Category category = getCategory();
		RequestCategory request = getRequestCategory();
		String writeValueAsBytes = objectMapper.writeValueAsString(request);
		System.out.println("writeValueAsBytes=" + writeValueAsBytes);
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(categoryService.updatecategory(any(RequestCategory.class), any(Long.class))).thenReturn(getCategory());
		mockMvc.perform(put("/product/category/1")
				.content(objectMapper.writeValueAsString(getRequestCategory()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(category)));
	}

	@Test
	public void testGetProduct() throws Exception {
		Category category = getCategory();
		Mockito.when(categoryService.getCategory(any(Long.class))).thenReturn(category);
		mockMvc.perform(get("/product/category/" + 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(category)));
	}

	@Test
	public void testDeleteProduct() throws Exception {
		Mockito.doNothing().when(categoryService).deleteById(any(Long.class), any(Long.class));
		mockMvc.perform(delete("/product/category/" + 1 + "/" + 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
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

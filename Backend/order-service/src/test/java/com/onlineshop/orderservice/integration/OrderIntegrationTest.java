package com.onlineshop.orderservice.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.orderservice.client.OnlineshopClient;
import com.onlineshop.orderservice.dto.CartDto;
import com.onlineshop.orderservice.dto.Category;
import com.onlineshop.orderservice.dto.ItemsDto;
import com.onlineshop.orderservice.dto.OrderPayment;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.dto.User;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.model.RequestOrderPay;
import com.onlineshop.orderservice.response.ResponseCart;

@SpringBootTest//(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(value=OrderAnnotation.class)
public class OrderIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private OnlineshopClient onlineShopClient;
	
	@Test
	@Order(1)
	public void testGetOrder() throws Exception {
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(onlineShopClient.getProductById(Mockito.anyLong())).thenReturn(getProduct());
		mockMvc.perform( get("/order/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	@Order(2)
	public void testSaveCart() throws Exception {
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(onlineShopClient.getProductById(Mockito.anyLong())).thenReturn(getProduct());
		mockMvc.perform( post("/order/cart")
				.content(objectMapper.writeValueAsString(getRequestItems()))
				.contentType(MediaType.APPLICATION_JSON));
	}
	
	
	@Test
	@Order(3)
	public void testSaveOrder() throws Exception {
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(onlineShopClient.getProductById(Mockito.anyLong())).thenReturn(getProduct());
		Mockito.when(onlineShopClient.createOrderPayment(any(RequestOrderPay.class))).thenReturn(getOrderPayment());
		mockMvc.perform( post("/order")
				.content(objectMapper.writeValueAsString(getRequestOrderPay()))
				.contentType(MediaType.APPLICATION_JSON));
	}
	
	private RequestOrderPay getRequestOrderPay() {
		RequestOrderPay pay = new RequestOrderPay();
		pay.setCartId(1l);
		pay.setUserId(1l);
		pay.setPayerId("21312312");
		pay.setPaymentOption("paypal");
		pay.setPaymentStatus("COMPLETED");
		pay.setTransactionId("324324324");
		return pay;
	}
	private OrderPayment getOrderPayment() {
		OrderPayment pay = new OrderPayment();
		pay.setOrderPayId(1l);
		pay.setPayerId("343123");
		pay.setPaymentOption("paypal");
		pay.setPaymentStatus("SUCCESS");
		pay.setTransactionId("78756745");
		return pay;
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
	
	private Cart getCart() {
		Cart cart = new Cart();
		cart.setCartId(1l);
		cart.setOrderStatus("ACTIVE");
		cart.setTotalAmount(100l);
		cart.setUser(1l);
		
		Set<Items> items = new HashSet<>();
		Items item = new Items();
		item.setItemId(1l);
		item.setProduct(1);
		item.setQuantity(2);
		item.setTotalPrice(100);
		items.add(item);
		
		cart.setItem(items);
		return cart;
	}

	private RequestItems getRequestItems() {
		RequestItems itm = new RequestItems();
		itm.setCartId(1l);
		itm.setProductId(1l);
		itm.setUserId(1l);
		itm.setQuantity(100);
		return itm;
	}
	
	private CartDto getCartDto() {
		CartDto dto = new CartDto();
		dto.setCartId(1l);
		dto.setOrderStatus("ACTIVE");
		dto.setTotalAmount(100);
		
		Set<ItemsDto> items = new HashSet<>();
		ItemsDto item = new ItemsDto();
		item.setItemId(1l);
		item.setProduct(getProduct());
		item.setQuantity(2);
		item.setTotalPrice(100);
		items.add(item);
		dto.setItem(items);
		dto.setUser(1l);
		
		
		
		return dto;
	}
	
	private ResponseCart getResponseCart() {
		ResponseCart cart = new ResponseCart();
		cart.setOrderStatus("ACTIVE");
		cart.setUserId(1l);
		return cart;
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
}

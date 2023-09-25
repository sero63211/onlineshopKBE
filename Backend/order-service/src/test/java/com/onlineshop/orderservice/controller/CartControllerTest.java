package com.onlineshop.orderservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
import com.onlineshop.orderservice.dto.CartDto;
import com.onlineshop.orderservice.dto.Category;
import com.onlineshop.orderservice.dto.ItemsDto;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.response.ResponseCart;
import com.onlineshop.orderservice.service.CartService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false)
class CartControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CartService cartservice;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testSaveCart() throws Exception {
		Mockito.when(cartservice.saveCart(any(RequestItems.class))).thenReturn(getCart());
		mockMvc.perform( post("/order/cart")
				.content(objectMapper.writeValueAsString(getRequestItems()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	@Test
	public void testGetCart() throws Exception {
		Mockito.when(cartservice.getCartByUser(any(ResponseCart.class))).thenReturn(getCartDto());
		mockMvc.perform( post("/order/cart/data")
				.content(objectMapper.writeValueAsString(getResponseCart()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
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

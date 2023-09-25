package com.onlineshop.orderservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.onlineshop.orderservice.dto.OrderDto;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.entity.Orders;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.model.RequestOrderPay;
import com.onlineshop.orderservice.response.ResponseCart;
import com.onlineshop.orderservice.service.OrderService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	OrderService orderService;
	
	@Autowired
	private ObjectMapper objectMapper;

	
	@Test
	public void testSaveOrder() throws Exception {
		Mockito.when(orderService.placeOrder(any(RequestOrderPay.class))).thenReturn(getOrders());
		mockMvc.perform( post("/order")
				.content(objectMapper.writeValueAsString(getRequestOrderPay()))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	

	@Test
	public void testGetAllorders() throws Exception {
		List<OrderDto> cartList = new ArrayList<>();
		cartList.add(getOrderDto());
		Mockito.when(orderService.getAllOrders(any(Long.class))).thenReturn(cartList);
		mockMvc.perform( get("/order/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
	}
	
	private Orders getOrders() {
		Orders o = new Orders();
		o.setCart(getCart());
		o.setDateTime(LocalDateTime.now());
		o.setOrderId(1l);
		o.setOrderPayment(1l);
		o.setTotalAmount(100);
		o.setUser(1l);
		return o;
	}
	
	
	
	private OrderDto getOrderDto() {
		OrderDto o = new OrderDto();
		o.setCart(getCartDto());
		o.setDateTime(LocalDateTime.now());
		o.setOrderId(1l);
		o.setOrderPayment(1l);
		o.setTotalAmount(100);
		o.setUser(1l);
		return o;
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
	
	private Items getItems() {
		Items item = new Items();
		item.setItemId(1l);
		item.setProduct(1l);
		item.setQuantity(2);
		item.setTotalPrice(100);
		return item;
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

package com.onlineshop.orderservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineshop.orderservice.client.OnlineshopClient;
import com.onlineshop.orderservice.dto.CartDto;
import com.onlineshop.orderservice.dto.Category;
import com.onlineshop.orderservice.dto.ItemsDto;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.dto.User;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.repository.ICartRepository;
import com.onlineshop.orderservice.repository.IItemsRepository;
import com.onlineshop.orderservice.response.ResponseCart;
import com.onlineshop.orderservice.serviceimpl.CartServiceimpl;
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class CartServiceimplTest {

	@InjectMocks
	private CartServiceimpl cartService;

	@Mock
	ICartRepository cartRepo;
	
	@Mock
	IItemsRepository itemRepo;
	
	@Mock
	private OnlineshopClient onlineShopClient;
	
	@Spy
	ObjectMapper mapp = new ObjectMapper();
	
	@Test
	void saveCartTest() throws Exception {
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(onlineShopClient.getProductById(Mockito.anyLong())).thenReturn(getProduct());
		Mockito.when(cartRepo.findByOrderStatusAndUser(any(Long.class), any(String.class))).thenReturn(getCart());
		//Mockito.when(itemRepo.findByProductAndCartCartId(any(Long.class), any(Long.class))).thenReturn(getItems());
		Mockito.when(itemRepo.findByProductAndCartCartId(any(Long.class), any(Long.class))).thenReturn(null);
		Set<Items> itmList = new HashSet<>();
		itmList.add(getItems());
		
		Mockito.when(itemRepo.findByCartCartId(any(Long.class))).thenReturn(itmList);
		Mockito.when(cartRepo.save(any(Cart.class))).thenReturn(getCart());
		Cart dto = cartService.saveCart(getRequestItems());
		assertThat(dto.getCartId()).isEqualTo(1l);
	}
	
	@Test
	void getCartByUserTest() throws Exception {
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(onlineShopClient.getProductById(Mockito.anyLong())).thenReturn(getProduct());
		Mockito.when(cartRepo.findByOrderStatusAndUser(any(Long.class), any(String.class))).thenReturn(getCart());
		//Mockito.when(itemRepo.findByProductAndCartCartId(any(Long.class), any(Long.class))).thenReturn(getItems());
		Mockito.when(itemRepo.findByProductAndCartCartId(any(Long.class), any(Long.class))).thenReturn(null);
		Set<Items> itmList = new HashSet<>();
		itmList.add(getItems());
		
		Mockito.when(itemRepo.findByCartCartId(any(Long.class))).thenReturn(itmList);
		Mockito.when(cartRepo.save(any(Cart.class))).thenReturn(getCart());
		CartDto dto = cartService.getCartByUser(getResponseCart());
		assertThat(dto.getCartId()).isEqualTo(1l);
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
	
	private Items getItems() {
		Items item = new Items();
		item.setItemId(1l);
		item.setProduct(1l);
		item.setQuantity(2);
		item.setTotalPrice(100);
		return item;
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

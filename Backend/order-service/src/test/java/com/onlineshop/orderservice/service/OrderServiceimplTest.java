package com.onlineshop.orderservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.onlineshop.orderservice.client.OnlineshopClient;
import com.onlineshop.orderservice.dto.CartDto;
import com.onlineshop.orderservice.dto.Category;
import com.onlineshop.orderservice.dto.ItemsDto;
import com.onlineshop.orderservice.dto.OrderDto;
import com.onlineshop.orderservice.dto.OrderPayment;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.dto.User;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.entity.Orders;
import com.onlineshop.orderservice.entity.Payments;
import com.onlineshop.orderservice.entity.SellerPayment;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.model.RequestOrderPay;
import com.onlineshop.orderservice.repository.ICartRepository;
import com.onlineshop.orderservice.repository.IItemsRepository;
import com.onlineshop.orderservice.repository.IOrderRepository;
import com.onlineshop.orderservice.repository.PaymentsRepository;
import com.onlineshop.orderservice.repository.SellerPaymentRepository;
import com.onlineshop.orderservice.response.ResponseCart;
import com.onlineshop.orderservice.serviceimpl.OrderServiceimpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class OrderServiceimplTest {
	
	@InjectMocks
	private OrderServiceimpl orderService;

	@Mock
	IOrderRepository OrderRepo;

	@Mock
	OnlineshopClient onlineShopClient;

	@Mock
	ICartRepository cartRepo;

	@Mock
	IItemsRepository itemRepo;

	@Mock
	SellerPaymentRepository sellerPaymentRepo;
	
	@Mock
	PaymentsRepository paymentsRepo;
	
	@Mock
	RabbitTemplate rabbitTemplate;

	@Test
	void testPlaceOrder() throws Exception {
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(onlineShopClient.getProductById(Mockito.anyLong())).thenReturn(getProduct());
		Mockito.when(onlineShopClient.createOrderPayment(any(RequestOrderPay.class))).thenReturn(getOrderPayment());
		Mockito.when(cartRepo.findByOrderStatusAndUser(any(Long.class), any(String.class))).thenReturn(getCart());
		Mockito.when(paymentsRepo.findBySeller(any(Long.class))).thenReturn(getPayments());
		Mockito.when(paymentsRepo.save(any(Payments.class))).thenReturn(getPayments());
		Mockito.when(OrderRepo.save(any(Orders.class))).thenReturn(getOrders());
		List<SellerPayment> list = new ArrayList<>();
		list.add(getSellerPayment());
		Mockito.when(sellerPaymentRepo.saveAll(any(List.class))).thenReturn(list);
		Mockito.doNothing().when(rabbitTemplate).convertAndSend(any(String.class), any(String.class),any(String.class));
		//Mockito.when(itemRepo.findByProductAndCartCartId(any(Long.class), any(Long.class))).thenReturn(getItems());
		Mockito.when(itemRepo.findByProductAndCartCartId(any(Long.class), any(Long.class))).thenReturn(null);
		Set<Items> itmList = new HashSet<>();
		itmList.add(getItems());
		Mockito.when(itemRepo.findByCartCartId(any(Long.class))).thenReturn(itmList);
		Mockito.when(cartRepo.save(any(Cart.class))).thenReturn(getCart());
		Mockito.when(cartRepo.findById(any(Long.class))).thenReturn(Optional.of(getCart()));
		
		Orders items = orderService.placeOrder(getRequestOrderPay());
		assertThat(items.getOrderId()).isEqualTo(1);
	}
	
	@Test
	void testGetAllOrders() throws Exception {
		Mockito.when(onlineShopClient.getUserById(Mockito.anyLong())).thenReturn(getUser());
		Mockito.when(onlineShopClient.getProductById(Mockito.anyLong())).thenReturn(getProduct());
		Mockito.when(onlineShopClient.createOrderPayment(any(RequestOrderPay.class))).thenReturn(getOrderPayment());
		Mockito.when(cartRepo.findByOrderStatusAndUser(any(Long.class), any(String.class))).thenReturn(getCart());
		Mockito.when(paymentsRepo.findBySeller(any(Long.class))).thenReturn(getPayments());
		Mockito.when(paymentsRepo.save(any(Payments.class))).thenReturn(getPayments());
		List<Orders> listOrders = new ArrayList<>();
		listOrders.add(getOrders());
		Mockito.when(OrderRepo.findAllByUserOrderByDateTimeDesc(any(Long.class))).thenReturn(listOrders);
		List<SellerPayment> list = new ArrayList<>();
		list.add(getSellerPayment());
		Mockito.when(sellerPaymentRepo.saveAll(any(List.class))).thenReturn(list);
		Mockito.doNothing().when(rabbitTemplate).convertAndSend(any(String.class), any(String.class),any(String.class));
		//Mockito.when(itemRepo.findByProductAndCartCartId(any(Long.class), any(Long.class))).thenReturn(getItems());
		Mockito.when(itemRepo.findByProductAndCartCartId(any(Long.class), any(Long.class))).thenReturn(null);
		Set<Items> itmList = new HashSet<>();
		itmList.add(getItems());
		Mockito.when(itemRepo.findByCartCartId(any(Long.class))).thenReturn(itmList);
		Mockito.when(cartRepo.save(any(Cart.class))).thenReturn(getCart());
		Mockito.when(cartRepo.findById(any(Long.class))).thenReturn(Optional.of(getCart()));
		
		List<OrderDto> items = orderService.getAllOrders(1);
		assertThat(items.size()).isEqualTo(1);
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

	private SellerPayment getSellerPayment() {
		SellerPayment pay = new SellerPayment();
		pay.setBuyer(1l);
		pay.setId(1l);
		pay.setIsPaymentDone("success");
		pay.setItem(getItems());
		pay.setOrderId("564634");
		pay.setOrders(getOrders());
		pay.setSeller(1l);
		pay.setTotalAmount(100);
		return pay;
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
	
	private Payments getPayments() {
		Payments pay = new Payments();
		pay.setId(1l);
		pay.setOverAllAmount(100);
		pay.setPendingAmount(60);
		pay.setSeller(1l);
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
	private Items getItems() {
		Items item = new Items();
		item.setCart(getCart());
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

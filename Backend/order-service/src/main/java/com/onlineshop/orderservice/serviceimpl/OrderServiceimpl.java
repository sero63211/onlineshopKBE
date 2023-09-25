package com.onlineshop.orderservice.serviceimpl;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.onlineshop.orderservice.client.OnlineshopClient;
import com.onlineshop.orderservice.dto.CartDto;
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
import com.onlineshop.orderservice.exception.IdNotFoundException;
import com.onlineshop.orderservice.exception.ProductCustomException;
import com.onlineshop.orderservice.model.RequestEmailData;
import com.onlineshop.orderservice.model.RequestOrderPay;
import com.onlineshop.orderservice.model.RequestPaymentModel;
import com.onlineshop.orderservice.repository.ICartRepository;
import com.onlineshop.orderservice.repository.IItemsRepository;
import com.onlineshop.orderservice.repository.IOrderRepository;
import com.onlineshop.orderservice.repository.PaymentsRepository;
import com.onlineshop.orderservice.repository.SellerPaymentRepository;
import com.onlineshop.orderservice.service.OrderService;

@Service
public class OrderServiceimpl implements OrderService {

	@Autowired
	IOrderRepository OrderRepo;

	@Autowired
	OnlineshopClient onlineshopClient;

	@Autowired
	ICartRepository cartRepo;

	@Autowired
	IItemsRepository itemRepo;

	@Autowired
	SellerPaymentRepository sellerPaymentRepo;
	
	@Autowired
	PaymentsRepository paymentsRepo;
	
	@Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;
	 
	public Orders placeOrder(RequestOrderPay requestOrder) {
 
		try {
			UUID oid= UUID.randomUUID();
			Orders orders = new Orders();
			Cart findBycart = cartRepo.findById(requestOrder.getCartId())
					.orElseThrow(() -> new IdNotFoundException(requestOrder.getCartId() + " Not Found "));
			findBycart.setOrderStatus("INACTIVE");
			orders.setCart(findBycart);
			User user = onlineshopClient.getUserById(requestOrder.getUserId());
				 if(user==null) {
					 throw new IdNotFoundException("user id not found");
				 }
				 
				 
			orders.setUser(requestOrder.getUserId());
			orders.setTotalAmount(findBycart.getTotalAmount());

			RequestOrderPay requestOrderPay = new RequestOrderPay();
			requestOrderPay.setCartId(requestOrder.getCartId());
			requestOrderPay.setUserId(requestOrder.getUserId());
			//orderPayment.setCardNumber(EncryptionUtils.encrypt(requestOrder.getCardNumber()));
			requestOrderPay.setPaymentOption(requestOrder.getPaymentOption());
			requestOrderPay.setTransactionId(requestOrder.getTransactionId());
			requestOrderPay.setPayerId(requestOrder.getPayerId());
			requestOrderPay.setPaymentStatus(requestOrder.getPaymentStatus());
			requestOrderPay.setDateTime(LocalDateTime.now());
			
			OrderPayment orderPayment = onlineshopClient.createOrderPayment(requestOrderPay);
			
			orders.setOrderPayment(orderPayment.getOrderPayId());
			
			
			
			List<SellerPayment> sellerPaymentList = new ArrayList<>();
			Set<Items> list = itemRepo.findByCartCartId(requestOrder.getCartId());
			for (Items item : list) {
				Product product = onlineshopClient.getProductById(item.getProduct());
				if(product==null) {
					throw new IdNotFoundException("product id not found");
				}
				if (product.getStocks() >= item.getQuantity()) {
					product.setStocks(product.getStocks() - item.getQuantity());
				} else {
					throw new ProductCustomException(
							"INSUFFICIENT QUANTITY" + " Remaining stocks " + product.getStocks());
				}
				

				SellerPayment sellerPayments = new SellerPayment();
				sellerPayments.setOrderId(oid.toString());
				sellerPayments.setOrders(orders);
				sellerPayments.setBuyer(orders.getUser());
				sellerPayments.setIsPaymentDone("PENDING");
				sellerPayments.setItem(item);
				sellerPayments.setSeller(product.getSeller());
				sellerPayments.setTotalAmount(item.getTotalPrice());
				
				Payments payments = paymentsRepo.findBySeller(product.getSeller());
				
				if(payments==null) {
					payments = new Payments();
					payments.setSeller(product.getSeller());
				}
				double finalPendingPrice = payments.getPendingAmount() + item.getTotalPrice();
				double finalOverAllPrice = payments.getOverAllAmount() + item.getTotalPrice();
				payments.setPendingAmount(finalPendingPrice);
				payments.setOverAllAmount(finalOverAllPrice);
				paymentsRepo.save(payments);
				sellerPaymentList.add(sellerPayments);
			}
			sellerPaymentRepo.saveAll(sellerPaymentList);
			
			RequestEmailData email = new RequestEmailData();
			email.setUsername(user.getName()+" "+user.getSurname());
			email.setCart(findBycart);
			email.setEmail(user.getEmail());
			email.setPayerId(requestOrder.getPayerId());
			email.setPaymentOption(requestOrder.getPaymentOption());
			email.setPaymentStatus(requestOrder.getPaymentStatus());
			email.setTotalAmount(findBycart.getTotalAmount());
			email.setTransactionId(requestOrder.getTransactionId());
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(email);
			System.out.println("==================RABBITMQ send=====================>");
			rabbitTemplate.convertAndSend(exchange,routingKey, json);
			System.out.println("=======================================>");
			
			return OrderRepo.save(orders);

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
		}
	}

	public void cancelOrder(long id) throws Exception {
		 
	}
	
	public Map<String, List<SellerPayment>> getAllordersBySeller(long userId){
		List<SellerPayment> respo = sellerPaymentRepo.findAllBySellerOrderByOrdersDateTimeDesc(userId);
	
		Map<String, List<SellerPayment>> collect = respo.stream().collect(Collectors.groupingBy(SellerPayment::getOrderId));
	
		return collect;
	}
	
	public Payments getAllPaymentsBySeller(long userId){
		Payments payments = paymentsRepo.findBySeller(userId);
		return payments;
	}

	public Payments sendPaymentsToSeller(RequestPaymentModel data){
		Payments payments = paymentsRepo.findBySeller(data.getUserId());
		double amountPending = payments.getPendingAmount() - data.getAmount();
		payments.setPendingAmount(amountPending);
		return  paymentsRepo.save(payments);
	}


	public List<OrderDto> getAllOrders(long userId) {
		List<OrderDto> orderList = new ArrayList<>();
		try {
			List<Orders> orders = OrderRepo.findAllByUserOrderByDateTimeDesc(userId);

			if (orders == null) {

				throw new IdNotFoundException("products and make a purchase");
			}
			
			for (Orders each : orders) {
				OrderDto dto = new OrderDto();
				dto.setDateTime(each.getDateTime());
				dto.setOrderId(each.getOrderId());
				dto.setOrderPayment(each.getOrderPayment());
				dto.setTotalAmount(each.getTotalAmount());
				dto.setUser(each.getUser());
				Cart crt = each.getCart();
				CartDto cart = new CartDto();
				cart.setCartId(crt.getCartId());
				cart.setOrderStatus(crt.getOrderStatus());
				cart.setTotalAmount(crt.getTotalAmount());
				cart.setUser(crt.getUser());
				Set<ItemsDto> itemSet = new HashSet<>();
				for (Items items : crt.getItem()) {
					ItemsDto itm = new ItemsDto();
					Product product = onlineshopClient.getProductById(items.getProduct());
					itm.setItemId(items.getItemId());
					itm.setQuantity(items.getQuantity());
					itm.setTotalPrice(items.getTotalPrice());
					itm.setProduct(product);
					itemSet.add(itm);
				}
				cart.setItem(itemSet);
				dto.setCart(cart);
				orderList.add(dto);
			}
			
			return orderList;
		}catch (Exception e) {
			e.printStackTrace();
			return orderList;
		}

		
	}
	
	public List<Orders> getAllordersForAdmin() {

		List<Orders> orders = OrderRepo.findAllByOrderByDateTimeDesc();

		return orders;
	}

	

	public List<Cart> getAllorderInActive(long userId) {

		return cartRepo.findAllByOrderStatusAndUser(userId, "INACTIVE");
	}



}

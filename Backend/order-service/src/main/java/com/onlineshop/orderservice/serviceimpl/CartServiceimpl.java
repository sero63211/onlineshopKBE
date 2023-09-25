package com.onlineshop.orderservice.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineshop.orderservice.client.OnlineshopClient;
import com.onlineshop.orderservice.dto.CartDto;
import com.onlineshop.orderservice.dto.ItemsDto;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.dto.User;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.exception.IdNotFoundException;
import com.onlineshop.orderservice.exception.ProductCustomException;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.repository.ICartRepository;
import com.onlineshop.orderservice.repository.IItemsRepository;
import com.onlineshop.orderservice.response.ResponseCart;
import com.onlineshop.orderservice.service.CartService;

@Service
public class CartServiceimpl implements CartService {

	@Autowired

	ICartRepository cartRepo;
	 
	@Autowired
	IItemsRepository itemRepo;
	
	@Autowired
	OnlineshopClient onlineshopClient;
 

	private static final Logger logger = LoggerFactory.getLogger(CartServiceimpl.class);

	public Cart saveCart(RequestItems requestitem) {

		logger.info("CartServiceimpl | cart is called");
		try {
			User user = onlineshopClient.getUserById(requestitem.getUserId());
			 
			if(user==null) {
				throw new IdNotFoundException("userId not found");
			}
			
			Cart cart = cartRepo.findByOrderStatusAndUser(requestitem.getUserId(), "ACTIVE");
			if(cart == null) {
				cart = new Cart();
				cart.setOrderStatus("ACTIVE");
			} 
			Product product = onlineshopClient.getProductById(requestitem.getProductId());
			if(product==null) {
				throw new IdNotFoundException("product id not found");
			}
			
			Items productId = itemRepo.findByProductAndCartCartId(requestitem.getProductId(), cart.getCartId());
			
			if(productId != null) {
				throw new IdNotFoundException("This product already added in your cart");
			}
			 
			 Items item = new Items();
				item.setQuantity(requestitem.getQuantity());
				
			
			 item.setProduct(requestitem.getProductId());
				
			 double totalPriceValue =  product.getPrice() * requestitem.getQuantity();
			 
				
			 item.setTotalPrice(totalPriceValue);
			  
			 Set<Items> itemList = cart.getItem();
			 item.setCart(cart);
			 itemList.add(item);
			   
			 cart.setItem(itemList);
			  
			 Set<Items> itemlist = itemRepo.findByCartCartId(cart.getCartId());
				
			   
				double totalAmount= 0;
					for(Items itemcart : itemlist) {
					 
					totalAmount += itemcart.getTotalPrice();
					
				}
				
				cart.setTotalAmount(totalAmount + totalPriceValue);
				
				 
			 cart.setUser(requestitem.getUserId());
				
			 return cartRepo.save(cart);
			 
	}
		catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
	}
		
	}

	
	
	
	
	public CartDto getCartByUser(ResponseCart responseCart) {
		Cart cart= new Cart();
		try {
		 	
		cart = cartRepo.findByOrderStatusAndUser(responseCart.getUserId(), "ACTIVE");
		 
		if(cart==null) {
			//throw new IdNotFoundException("cart is empty");
		} else {
			CartDto dto = new CartDto();
			dto.setCartId(cart.getCartId());
			dto.setOrderStatus(cart.getOrderStatus());
			dto.setTotalAmount(cart.getTotalAmount());
			dto.setUser(cart.getUser());
			Set<ItemsDto> itemSet = new HashSet<>();
			for (Items items : cart.getItem()) {
				ItemsDto itm = new ItemsDto();
				Product product = onlineshopClient.getProductById(items.getProduct());
				itm.setItemId(items.getItemId());
				itm.setQuantity(items.getQuantity());
				itm.setTotalPrice(items.getTotalPrice());
				itm.setProduct(product);
				itemSet.add(itm);
			}
			dto.setItem(itemSet);
			return dto;
		}
		
		
		 
		return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ProductCustomException(e.getMessage());
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	 
}

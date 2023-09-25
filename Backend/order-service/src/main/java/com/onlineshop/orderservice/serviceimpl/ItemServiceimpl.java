package com.onlineshop.orderservice.serviceimpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineshop.orderservice.client.OnlineshopClient;
import com.onlineshop.orderservice.entity.Cart;
import com.onlineshop.orderservice.entity.Items;
import com.onlineshop.orderservice.dto.Product;
import com.onlineshop.orderservice.exception.IdNotFoundException;
import com.onlineshop.orderservice.exception.ProductCustomException;
import com.onlineshop.orderservice.model.RequestItems;
import com.onlineshop.orderservice.repository.ICartRepository;
import com.onlineshop.orderservice.repository.IItemsRepository;
import com.onlineshop.orderservice.service.ItemService;

@Service
public class ItemServiceimpl implements ItemService {

	@Autowired
	IItemsRepository itemRepo;

	@Autowired
	ICartRepository cartRepo;

	@Autowired
	OnlineshopClient onlineshopClient;

	private static final Logger logger = LogManager.getLogger(ItemServiceimpl.class);

	

	public void deleteByid(long itemId, long cartId, long userId) {

		Optional<Cart> cart = cartRepo.findById(cartId);

		if (cart.isPresent()) {
			Set<Items> itemList = cart.get().getItem();
			Optional<Items> itemToRemove = itemList.stream().filter(e -> e.getItemId() == itemId).findFirst();

			logger.info("To get item data={}", itemId);
			
			if (itemToRemove.isPresent()) {

				itemList.remove(itemToRemove.get());

				cartRepo.save(cart.get());
			} else {
				throw new EntityNotFoundException("Item with id " + itemId + " not found in cart with id " + cartId);
			}
		} else {
			throw new EntityNotFoundException("Cart with id " + cartId + " not found");
		}

		Cart cart2 = cartRepo.findByOrderStatusAndUser(userId, "ACTIVE");

		Set<Items> item = itemRepo.findByCartCartId(cart2.getCartId());

			updateTotalPrice(item, cart2);
		 
	}

 	 

//	public Items getItemsByid(long id) throws Exception {
//		try {
//			return itemRepo.findById(id).get();
//
//		} catch (Exception idNotFoundException) {
//			throw new IdNotFoundException(idNotFoundException.getMessage());
//		}
//
//	}

	public Items updatecategory(RequestItems items, long id) {
			try {
				Items items1 = itemRepo.findById(id).orElseThrow(() -> new IdNotFoundException("Not Found" + id));
				items1.setQuantity(items.getQuantity());
				Product product = onlineshopClient.getProductById(items.getProductId());
				if(product==null) {
					throw new IdNotFoundException("product id not found");
				}
				double total = product.getPrice() * items.getQuantity();
				items1.setTotalPrice(total);
				items1.setProduct(items.getProductId());
				Cart cart = cartRepo.findById(items1.getCart().getCartId())
						.orElseThrow(() -> new IdNotFoundException(" cart Not found "));
				Set<Items> itemlist = itemRepo.findByCartCartId(cart.getCartId());
					updateTotalPrice(itemlist, cart);
				return itemRepo.save(items1);	
			}
			catch (Exception e) {
				throw new ProductCustomException(e.getMessage());
			}
		

	}
	
	public void updateTotalPrice(Set<Items> items, Cart cart) {
		double totalAmount = 0;
		for (Items itemcart : items) {
			totalAmount += itemcart.getTotalPrice();
		}
		cart.setTotalAmount(totalAmount);
		cartRepo.save(cart);
	}
	

 

}

package com.onlineshop.notify.service.dto;

import lombok.Data;

@Data
public class ItemsDto {
	private Long itemId;

	private int quantity;

	private double totalPrice;

	private Product product;
}

package com.onlineshop.orderservice.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class CartDto {
	private Long cartId;

	private Set<ItemsDto> item = new HashSet<>();

	private long user;

	private String orderStatus;

	private double totalAmount;
}

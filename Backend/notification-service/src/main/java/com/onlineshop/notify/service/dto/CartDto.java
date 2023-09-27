package com.onlineshop.notify.service.dto;

import java.util.HashSet;
import java.util.Set;

public class CartDto {
	private Long cartId;

	private Set<ItemsDto> item = new HashSet<>();

	private long user;

	private String orderStatus;

	private double totalAmount;
}

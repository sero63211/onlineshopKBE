package com.onlineshop.paymentservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_payment")
@Getter
@Setter
public class OrderPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderPayId;

	private String paymentOption;

	private String payerId;

	private String paymentStatus;
	
	private String transactionId;
}

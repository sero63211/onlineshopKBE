package com.onlineshop.orderservice.entity;

 
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
 

 

@Entity
@Table(name = "Orders")
@Getter
@Setter
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "orderid")
	private Long orderId;

	
	@Column(name = "ordertime")
	private LocalDateTime dateTime = LocalDateTime.now();

	@Column(name = "totalAmount")
	private double totalAmount;

	@OneToOne
	@JoinColumn(name = "cartId", referencedColumnName = "cartId")
	private Cart cart;

	//@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE})
	//@JoinColumn(name = "userId")
	private long user;
	
	
//	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE})
//	@JoinColumn(name = "orderPayid", referencedColumnName = "orderPayid")
	private long orderPayment;
	
	
 

}

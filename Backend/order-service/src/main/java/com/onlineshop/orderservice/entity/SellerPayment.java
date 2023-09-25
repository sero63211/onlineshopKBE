package com.onlineshop.orderservice.entity;

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
@Table(name = "seller_payment")
@Getter
@Setter
public class SellerPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "order_track_id")
	private String orderId;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "orderId")
	private Orders orders;
	
	//@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.REMOVE})
	//@JoinColumn(name = "sellerId")
	private long seller;
	
	//@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE})
	//@JoinColumn(name = "buyerId")
	private long buyer;
	
	@OneToOne 
	@JoinColumn(name = "itemId", referencedColumnName = "itemId")
	private Items item;
	
	private double totalAmount;
	
	private String isPaymentDone;

}

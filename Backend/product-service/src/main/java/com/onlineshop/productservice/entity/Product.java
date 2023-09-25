package com.onlineshop.productservice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product", uniqueConstraints = { @UniqueConstraint(columnNames = "productName") })
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "productId")
	private Long productId;

	@Column(name = "productname", nullable = false, unique = true)
	private String productName;
	
	@Column(name = "description", nullable = false, unique = true, length = 2000)
	private String description;

	@Column(name = "stocks")
	private Long stocks;

	@Column(name = "unit")
	private String unit;

	@Column(name = "price")
	private double price;

	@Column(name = "addedAt")
	private LocalDateTime addedAt;
	
	private long seller;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@Column(length=10000000)
	private byte[] imageData;
	
	private String status;
	 
}

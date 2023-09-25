//package com.onlineshop.productservice.entity;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "ratings")
//@Getter
//@Setter
//public class Ratings {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long ratingsId;
//	
//	@Column(name = "rating",precision=10, scale=2)
//	private Double rating;
//	
//	@ManyToOne
//	@JoinColumn(name = "productId")
//	@JsonIgnore
//	private Product product;
//	
//	//@ManyToOne
//	//@JoinColumn(name = "buyer_id")
//	private long buyerId;
//
//}

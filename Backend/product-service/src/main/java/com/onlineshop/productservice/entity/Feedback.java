//package com.onlineshop.productservice.entity;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "feedback")
//@Getter
//@Setter
//public class Feedback {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long feedbackId;
//
//	@Column(length = 3000)
//	private String feedbackContent;
//	
//	@ManyToOne
//	@JoinColumn(name = "productId")
//	@JsonIgnore
//	private Product product;
//	
////	@ManyToOne
////	@JoinColumn(name = "buyer_id")
//	private long buyerId;
//	
//}

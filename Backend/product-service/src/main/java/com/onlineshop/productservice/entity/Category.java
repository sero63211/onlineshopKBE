package com.onlineshop.productservice.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
 

@Entity
@Table(name = "Catagory", uniqueConstraints = {@UniqueConstraint(columnNames ="categoryName" )})
@Getter
@Setter
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long categoryId;
	
	@Column(name = "categoryName", nullable = false, unique = true)
	private String categoryName;
	
	
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	@JsonIgnore
	private List<Product> product;
 	
	private String status;
	
	 
	

}

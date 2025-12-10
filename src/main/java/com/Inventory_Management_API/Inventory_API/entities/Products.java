package com.Inventory_Management_API.Inventory_API.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Entity
@Data
public class Products {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message="name of product is Required Field")
	@Column(nullable=false,unique =true)
	private String name;
	
	@NotNull(message="price is required")
	@Positive(message="price must be > 0")
	private double price;
	
	@Min(value=0,message="quantity must be >=0")
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name="category_ID",nullable=false)
	private Category category;
	@CreationTimestamp
	@Column(updatable=false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
}

package com.Inventory_Management_API.Inventory_API.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductResponse {
	private long id;
	private String name;
	private double price;
	private int quantity;
	private long category_id;
	private String category_name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

package com.Inventory_Management_API.Inventory_API.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
	@NotBlank(message = "Name of Category is required")
	private String name;
	private String description;
}

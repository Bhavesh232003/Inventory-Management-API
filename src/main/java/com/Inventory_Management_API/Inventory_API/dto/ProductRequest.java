package com.Inventory_Management_API.Inventory_API.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequest {
	@NotBlank(message ="Product name is required")
    private String name;

    @NotNull(message ="Price is required")
    @Positive(message ="Price must be > 0")
    private Double price;

    @Min(value = 0, message ="Quantity must be >=0")
    private Integer quantity;

    @NotNull(message ="Category ID is required")
    private Long categoryId;

}

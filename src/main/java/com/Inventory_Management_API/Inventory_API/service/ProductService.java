package com.Inventory_Management_API.Inventory_API.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Inventory_Management_API.Inventory_API.Exception.CategoryNotFoundException;
import com.Inventory_Management_API.Inventory_API.Exception.DuplicateNameException;
import com.Inventory_Management_API.Inventory_API.Exception.ProductNotFoundException;
import com.Inventory_Management_API.Inventory_API.dto.ProductRequest;
import com.Inventory_Management_API.Inventory_API.dto.ProductResponse;
import com.Inventory_Management_API.Inventory_API.entities.Category;
import com.Inventory_Management_API.Inventory_API.entities.Products;
import com.Inventory_Management_API.Inventory_API.repository.CategoryRepository;
import com.Inventory_Management_API.Inventory_API.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new DuplicateNameException("Duplicate product: " + request.getName() + " already exists");
        }
        Category category = categoryRepository.findById(request.getCategoryId())
               .orElseThrow(() -> new CategoryNotFoundException("Category with ID "+request.getCategoryId()+" not found"));
        Products product = new Products();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setCategory(category);
        Products savedProduct = productRepository.save(product);
       
        return mapResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts(Long categoryId, Double minPrice, Double maxPrice) {
    	Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID "+categoryId+" not found"));
        List<Products> products = productRepository.searchProducts(categoryId, minPrice, maxPrice);
        return products.stream()
                .map(e->this.mapResponse(e))
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID/ "+ id+ " not found"));
        return mapResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Products product = productRepository.findById(id)
               .orElseThrow(() -> new ProductNotFoundException("Product with ID " +id+" not found"));
        if (!product.getName().equals(request.getName()) && productRepository.existsByName(request.getName())) {
             throw new DuplicateNameException("Duplicate product "+ request.getName()+" already exists");
        }

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        if (request.getCategoryId() != null && product.getCategory().getId()!=(request.getCategoryId())) {
            Category category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new CategoryNotFoundException("Category with ID "+request.getCategoryId()+" not found"));
            product.setCategory(category);
        }
        Products updatedProduct = productRepository.save(product);
        return mapResponse(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID "+id+" not found");
        }
        productRepository.deleteById(id);
    }

    private ProductResponse mapResponse(Products product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());
        if (product.getCategory() != null) {
           response.setCategory_id(product.getCategory().getId());
           response.setCategory_name(product.getCategory().getName());
        }
        return response;
    }
}
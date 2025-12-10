package com.Inventory_Management_API.Inventory_API.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Inventory_Management_API.Inventory_API.Exception.CategoryNotFoundException;
import com.Inventory_Management_API.Inventory_API.Exception.DuplicateNameException;
import com.Inventory_Management_API.Inventory_API.dto.CategoryRequest;
import com.Inventory_Management_API.Inventory_API.dto.CategoryResponse;
import com.Inventory_Management_API.Inventory_API.entities.Category;
import com.Inventory_Management_API.Inventory_API.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
	this.categoryRepository=categoryRepository;
	}

	public CategoryResponse CreateCategory(CategoryRequest request) {
		
		if(categoryRepository.existsByName(request.getName())) {
			throw new DuplicateNameException("This Category "+request.getName()+" already exists.");
		}
		Category category=new Category();
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		
		Category savedCategory= categoryRepository.save(category);
		
		return MapResponse(savedCategory);
	}
	
	public CategoryResponse MapResponse(Category category) {
		CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
	}
	// I have used Stream basic to perform this thing
	public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(e->this.MapResponse(e))
                .collect(Collectors.toList());
    }
	
	 public CategoryResponse getCategoryById(long id) {
	        Category category = categoryRepository.findById(id)
	            .orElseThrow(() -> new CategoryNotFoundException("Category with Id "+id+" not found"));
	        
	        return MapResponse(category);
	        }
	 
	 public void deleteCategory(Long id) {
	        if (!categoryRepository.existsById(id)) {
	            throw new CategoryNotFoundException("Category with Id "+id+" not found");
	        }
	        categoryRepository.deleteById(id);
	    }
}

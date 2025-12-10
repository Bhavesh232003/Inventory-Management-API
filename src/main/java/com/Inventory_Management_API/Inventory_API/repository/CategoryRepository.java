package com.Inventory_Management_API.Inventory_API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Inventory_Management_API.Inventory_API.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	boolean existsByName(String name);
	Optional<Category> findById(long id);
}

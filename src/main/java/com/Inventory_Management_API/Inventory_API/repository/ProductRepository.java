package com.Inventory_Management_API.Inventory_API.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Inventory_Management_API.Inventory_API.entities.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
	boolean existsByName(String name);
	
	List<Products> findByCategoryId(long categoryId);
	
	@Query("SELECT p FROM Products p WHERE (:categoryId IS NULL OR p.category.id = :categoryId) AND " +
	           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
	           "(:maxPrice IS NULL OR p.price <= :maxPrice)")
	List<Products> searchProducts(@Param("categoryId") Long categoryId,
			@Param("minPrice") Double minPrice,
			@Param("maxPrice") Double maxPrice);
}

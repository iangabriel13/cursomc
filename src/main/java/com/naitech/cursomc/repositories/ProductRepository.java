package com.naitech.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.naitech.cursomc.domain.Category;
import com.naitech.cursomc.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT product FROM Product product INNER JOIN product.categories categories WHERE product.name LIKE %:name% AND categories in :categories")
	Page<Product> findDistinctByNameContainingAndCategoriesIn(@Param("name") String name, @Param("categories") List<Category> categories, Pageable pageable);
}

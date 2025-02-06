package com.naitech.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naitech.cursomc.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Page<Category> findAll(Pageable pageable);
}

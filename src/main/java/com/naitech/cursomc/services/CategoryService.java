package com.naitech.cursomc.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.naitech.cursomc.domain.Category;
import com.naitech.cursomc.repositories.CategoryRepository;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Category find(Integer id) {
		Optional<Category> category = categoryRepository.findById(id);

		return category.get();
	}

}

package com.naitech.cursomc.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.naitech.cursomc.domain.Category;
import com.naitech.cursomc.repositories.CategoryRepository;
import com.naitech.cursomc.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Category find(Integer id) {
		Optional<Category> category = categoryRepository.findById(id);

		return category.orElseThrow(() -> new ObjectNotFoundException(
				"Category not found! Id: " + id + ", Type: " + Category.class.getName()));
	}

	public Category insert(Category category) {
		category.setId(null);
		return categoryRepository.save(category);
	}

	public Category update(Category category) {
		find(category.getId());
		return categoryRepository.save(category);
	}

}

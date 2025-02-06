package com.naitech.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.naitech.cursomc.domain.Category;
import com.naitech.cursomc.dto.CategoryDTO;
import com.naitech.cursomc.repositories.CategoryRepository;
import com.naitech.cursomc.services.exceptions.DataIntegrityException;
import com.naitech.cursomc.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> findAll() {
		return categoryRepository.findAll();
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

	public void delete(Integer id) {
		Category category = find(id);

		if (category.getProducts() != null && category.getProducts().size() > 0) {
			throw new DataIntegrityException("Category has products! Id: " + id);
		}

		categoryRepository.deleteById(id);
	}

	public Page<Category> findPage(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	public Category fromDTO(CategoryDTO categoryDTO) {
		return new Category(categoryDTO.getId(), categoryDTO.getName());
	}

}

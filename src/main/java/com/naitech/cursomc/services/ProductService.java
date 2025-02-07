package com.naitech.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.naitech.cursomc.domain.Category;
import com.naitech.cursomc.domain.Product;
import com.naitech.cursomc.repositories.CategoryRepository;
import com.naitech.cursomc.repositories.ProductRepository;
import com.naitech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {

	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;

	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	public Product find(Integer id) {
		Optional<Product> product = productRepository.findById(id);

		return product.orElseThrow(() -> new ObjectNotFoundException( 
				"Product not found! Id: " + id + ", Type: " + Product.class.getName())); 
	}

	public Page<Product> findPage(String name, List<Integer> categoryIds, Pageable pageable) {
		List<Category> categories = categoryRepository.findAllById(categoryIds);
		return productRepository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageable);
	}
}

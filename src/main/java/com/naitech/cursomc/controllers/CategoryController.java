package com.naitech.cursomc.controllers;



import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naitech.cursomc.domain.Category;

@RestController
@RequestMapping(value="/categories")
public class CategoryController {

	@GetMapping
	public List<Category> list() {
		Category category1 = new Category(1, "Informatica");
		Category category2 = new Category(2, "Escritorio");
		
		List<Category> list = new ArrayList<>();
		list.add(category1);
		list.add(category2);
		
		return list;
	}
}

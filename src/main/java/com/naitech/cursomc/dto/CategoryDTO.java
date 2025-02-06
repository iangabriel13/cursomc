package com.naitech.cursomc.dto;

import java.io.Serializable;

import com.naitech.cursomc.domain.Category;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Cannot be empty")
	@Size(min = 5, max = 80, message = "The length should be between 5 and 80 characters")
	private String name;

	public CategoryDTO() {
	}

	public CategoryDTO(Category category) {
		id = category.getId();
		name = category.getName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

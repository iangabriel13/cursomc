package com.naitech.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.naitech.cursomc.domain.Category;
import com.naitech.cursomc.repositories.CategoryRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category category1 = new Category(null, "Informatica");
		Category category2 = new Category(null, "Escritorio");
		
		categoryRepository.saveAll(Arrays.asList(category1, category2));
	}

}

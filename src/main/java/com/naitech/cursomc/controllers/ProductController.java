package com.naitech.cursomc.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.naitech.cursomc.controllers.utils.URL;
import com.naitech.cursomc.domain.Product;
import com.naitech.cursomc.dto.ProductDTO;
import com.naitech.cursomc.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		Product product = productService.find(id);

		return ResponseEntity.ok().body(product);
	}

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAllPagination(@RequestParam() String name, @RequestParam() String categories, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "24") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
		Page<Product> products = productService.findPage(name, URL.decodeIntList(categories), pageable);
		Page<ProductDTO> productsDTO = products.map(p -> new ProductDTO(p));
		return ResponseEntity.ok(productsDTO);
	}

	private Sort.Order parseSort(String[] sort) {
		return new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]);
	}
}

package com.naitech.cursomc.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.naitech.cursomc.domain.ClientOrder;
import com.naitech.cursomc.services.ClientOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/orders")
public class ClientOrderController {

	private ClientOrderService clientOrderService;

	public ClientOrderController(ClientOrderService clientOrderService) {
		this.clientOrderService = clientOrderService;
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		ClientOrder clientOrder = clientOrderService.find(id);

		return ResponseEntity.ok().body(clientOrder);
	}

	@PostMapping
	public ResponseEntity<?> insert(@Valid @RequestBody ClientOrder clientOrder) {
		clientOrder = clientOrderService.insert(clientOrder);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clientOrder.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	public ResponseEntity<Page<ClientOrder>> findAllPagination(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "24") int size, @RequestParam(defaultValue = "id,desc") String[] sort) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
		Page<ClientOrder> clientOrders = clientOrderService.findPage(pageable);
		return ResponseEntity.ok(clientOrders);
	}

	private Sort.Order parseSort(String[] sort) {
		return new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]);
	}
}

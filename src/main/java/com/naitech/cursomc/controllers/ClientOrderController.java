package com.naitech.cursomc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naitech.cursomc.domain.ClientOrder;
import com.naitech.cursomc.services.ClientOrderService;

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
}

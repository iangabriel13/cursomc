package com.naitech.cursomc.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.dto.ClientDTO;
import com.naitech.cursomc.dto.ClientNewDTO;
import com.naitech.cursomc.services.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

	private ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping
	public ResponseEntity<List<ClientDTO>> findAll() {
		List<Client> clients = clientService.findAll();

		List<ClientDTO> clientsDTO = clients.stream().map(c -> new ClientDTO(c)).collect(Collectors.toList());

		return ResponseEntity.ok().body(clientsDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id) {
		Client client = clientService.find(id);

		return ResponseEntity.ok().body(client);
	}

	@PostMapping
	public ResponseEntity<?> insert(@Valid @RequestBody ClientNewDTO clientNewDTO) {
		Client client = clientService.fromDTO(clientNewDTO);
		client = clientService.insert(client);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody ClientDTO clientDTO) {
		Client client = clientService.fromDTO(clientDTO);
		client.setId(id);
		client = clientService.update(client);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		clientService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClientDTO>> findAllPagination(@RequestParam(defaultValue = "0") int page, // Default to
																											// page 0
			@RequestParam(defaultValue = "24") int size, // Default size 10
			@RequestParam(defaultValue = "id,asc") String[] sort // Default sorting
	) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
		Page<Client> clients = clientService.findPage(pageable);
		Page<ClientDTO> clientsDTO = clients.map(c -> new ClientDTO(c));
		return ResponseEntity.ok(clientsDTO);
	}

	private Sort.Order parseSort(String[] sort) {
		return new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]);
	}
}

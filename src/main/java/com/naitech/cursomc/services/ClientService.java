package com.naitech.cursomc.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.repositories.ClientRepository;
import com.naitech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	private ClientRepository clientRepository;

	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public Client find(Integer id) {
		Optional<Client> client = clientRepository.findById(id);

		return client.orElseThrow(() -> new ObjectNotFoundException( 
				"Client not found! Id: " + id + ", Type: " + Client.class.getName())); 
	}

}

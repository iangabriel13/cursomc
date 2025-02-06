package com.naitech.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.dto.ClientDTO;
import com.naitech.cursomc.repositories.ClientRepository;
import com.naitech.cursomc.services.exceptions.DataIntegrityException;
import com.naitech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	private ClientRepository clientRepository;

	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public List<Client> findAll() {
		return clientRepository.findAll();
	}
	
	public Client find(Integer id) {
		Optional<Client> client = clientRepository.findById(id);

		return client.orElseThrow(() -> new ObjectNotFoundException( 
				"Client not found! Id: " + id + ", Type: " + Client.class.getName())); 
	}

	public Client update(Client client) {
		Client clientDatabase = find(client.getId());
		updateClient(clientDatabase, client);
		return clientRepository.save(clientDatabase);
	}

	private void updateClient(Client clientDatabase, Client client) {
		clientDatabase.setName(client.getName());
		clientDatabase.setEmail(client.getEmail());
	}

	public void delete(Integer id) {
		Client client = find(id);
		
		if (client.getOrders() != null && client.getOrders().size() > 0) {
			throw new DataIntegrityException("Client has orders! Id: " + id);
		}
		
		if (client.getAddresses() != null && client.getAddresses().size() > 0) {
			throw new DataIntegrityException("Client has addresses! Id: " + id);
		}
		
		clientRepository.deleteById(id);
	}


	public Page<Client> findPage(Pageable pageable) {
		return clientRepository.findAll(pageable);
	}
	
	public Client fromDTO(ClientDTO clientDTO) {
		return new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getEmail(), null, null);
	}
	
}

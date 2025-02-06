package com.naitech.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naitech.cursomc.domain.Address;
import com.naitech.cursomc.domain.City;
import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.domain.enums.ClientType;
import com.naitech.cursomc.dto.ClientDTO;
import com.naitech.cursomc.dto.ClientNewDTO;
import com.naitech.cursomc.repositories.AddressRepository;
import com.naitech.cursomc.repositories.ClientRepository;
import com.naitech.cursomc.services.exceptions.DataIntegrityException;
import com.naitech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	private ClientRepository clientRepository;
	private AddressRepository addressRepository;

	public ClientService(ClientRepository clientRepository, AddressRepository addressRepository) {
		this.clientRepository = clientRepository;
		this.addressRepository = addressRepository;
	}

	public List<Client> findAll() {
		return clientRepository.findAll();
	}

	public Client find(Integer id) {
		Optional<Client> client = clientRepository.findById(id);

		return client.orElseThrow(
				() -> new ObjectNotFoundException("Client not found! Id: " + id + ", Type: " + Client.class.getName()));
	}

	@Transactional
	public Client insert(Client client) {
		client.setId(null);
		client = clientRepository.save(client);
		addressRepository.saveAll(client.getAddresses());
		return client;
	}

	@Transactional
	public Client update(Client client) {
		Client clientDatabase = find(client.getId());
		updateClient(clientDatabase, client);
		return clientRepository.save(clientDatabase);
	}

	private void updateClient(Client clientDatabase, Client client) {
		clientDatabase.setName(client.getName());
		clientDatabase.setEmail(client.getEmail());
	}

	@Transactional
	public void delete(Integer id) {
		Client client = find(id);

		if (client.getOrders() != null && client.getOrders().size() > 0) {
			throw new DataIntegrityException("Client has orders! Id: " + id);
		}

		clientRepository.deleteById(id);
	}

	public Page<Client> findPage(Pageable pageable) {
		return clientRepository.findAll(pageable);
	}

	public Client fromDTO(ClientDTO clientDTO) {
		return new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getEmail(), null, null);
	}

	public Client fromDTO(ClientNewDTO clientNewDTO) {
		Client client = new Client(null, clientNewDTO.getName(), clientNewDTO.getEmail(), clientNewDTO.getCpfOuCnpj(),
				ClientType.toEnum(clientNewDTO.getClientType()));
		City city = new City(clientNewDTO.getCityId(), null, null);
		Address address = new Address(null, clientNewDTO.getAddress(), clientNewDTO.getNumber(),
				clientNewDTO.getComplement(), clientNewDTO.getNeighbourhood(), clientNewDTO.getPostcode(), client,
				city);
		client.getAddresses().add(address);
		client.getPhones().add(clientNewDTO.getPhone1());
		if (clientNewDTO.getPhone2() != null) {
			client.getPhones().add(clientNewDTO.getPhone2());
		}
		if (clientNewDTO.getPhone3() != null) {
			client.getPhones().add(clientNewDTO.getPhone3());
		}

		return client;
	}

}

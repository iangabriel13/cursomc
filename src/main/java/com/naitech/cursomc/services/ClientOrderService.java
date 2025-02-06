package com.naitech.cursomc.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.naitech.cursomc.domain.ClientOrder;
import com.naitech.cursomc.repositories.ClientOrderRepository;
import com.naitech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientOrderService {

	private ClientOrderRepository clientOrderRepository;

	public ClientOrderService(ClientOrderRepository clientOrderRepository) {
		this.clientOrderRepository = clientOrderRepository;
	}

	public ClientOrder find(Integer id) {
		Optional<ClientOrder> clientOrder = clientOrderRepository.findById(id);

		return clientOrder.orElseThrow(() -> new ObjectNotFoundException( 
				"Order not found! Id: " + id + ", Type: " + ClientOrder.class.getName())); 
	}

}

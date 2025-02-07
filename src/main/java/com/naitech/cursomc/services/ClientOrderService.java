package com.naitech.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naitech.cursomc.domain.BankSlipPayment;
import com.naitech.cursomc.domain.ClientOrder;
import com.naitech.cursomc.domain.ItemOrder;
import com.naitech.cursomc.domain.enums.PaymentStatus;
import com.naitech.cursomc.repositories.ClientOrderRepository;
import com.naitech.cursomc.repositories.ItemOrderRepository;
import com.naitech.cursomc.repositories.PaymentRepository;
import com.naitech.cursomc.repositories.ProductRepository;
import com.naitech.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClientOrderService {

	private ClientOrderRepository clientOrderRepository;
	private BankSlipService bankSlipService;
	private PaymentRepository paymentRepository;
	private ProductRepository productRepository;
	private ItemOrderRepository itemOrderRepository;

	public ClientOrderService(ClientOrderRepository clientOrderRepository, BankSlipService bankSlipService,
			PaymentRepository paymentRepository, ProductRepository productRepository,
			ItemOrderRepository itemOrderRepository) {
		this.clientOrderRepository = clientOrderRepository;
		this.bankSlipService = bankSlipService;
		this.paymentRepository = paymentRepository;
		this.productRepository = productRepository;
		this.itemOrderRepository = itemOrderRepository;
	}

	public ClientOrder find(Integer id) {
		Optional<ClientOrder> clientOrder = clientOrderRepository.findById(id);

		return clientOrder.orElseThrow(() -> new ObjectNotFoundException(
				"Order not found! Id: " + id + ", Type: " + ClientOrder.class.getName()));
	}

	@Transactional
	public ClientOrder insert(ClientOrder clientOrder) {
		clientOrder.setId(null);
		clientOrder.setDateOrder(new Date());
		clientOrder.getPayment().setPaymentStatus(PaymentStatus.PENDENTE);
		clientOrder.getPayment().setOrder(clientOrder);
		if (clientOrder.getPayment() instanceof BankSlipPayment) {
			bankSlipService.fillBankSlipPayment((BankSlipPayment) clientOrder.getPayment(), clientOrder.getDateOrder());
		}
		clientOrder = clientOrderRepository.save(clientOrder);
		paymentRepository.save(clientOrder.getPayment());
		for (ItemOrder itemOrder : clientOrder.getItems()) {
			itemOrder.setDiscount(0.0);
			itemOrder.setPrice(productRepository.findById(itemOrder.getProduct().getId()).get().getPrice());
			itemOrder.setClientOrder(clientOrder);
		}
		itemOrderRepository.saveAll(clientOrder.getItems());

		return clientOrder;
	}
}

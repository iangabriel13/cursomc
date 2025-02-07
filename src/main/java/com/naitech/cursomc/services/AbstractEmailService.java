package com.naitech.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.naitech.cursomc.domain.ClientOrder;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(ClientOrder clientOrder) {
		SimpleMailMessage simpleMailMessage = prepareSimpleMailMessageFromOrder(clientOrder);
		sendEmail(simpleMailMessage);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromOrder(ClientOrder clientOrder) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(clientOrder.getClient().getEmail());
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setSubject("Order confirmed! Code: " + clientOrder.getId());
		simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		simpleMailMessage.setText(clientOrder.toString());
		
		return simpleMailMessage;
	}
}

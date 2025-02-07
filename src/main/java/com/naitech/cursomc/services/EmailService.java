package com.naitech.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.naitech.cursomc.domain.ClientOrder;

public interface EmailService {

	void sendOrderConfirmationEmail(ClientOrder clientOrder);
	
	void sendEmail(SimpleMailMessage simpleMailMessage);
}

package com.naitech.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.domain.ClientOrder;

import jakarta.mail.internet.MimeMessage;

public interface EmailService {

	void sendOrderConfirmationEmail(ClientOrder clientOrder);
	
	void sendEmail(SimpleMailMessage simpleMailMessage);
	
	void sendOrderConfirmationHtmlEmail(ClientOrder clientOrder);
	
	void sendHtmlEmail(MimeMessage mimeMessage);

	void sendNewPasswordEmail(Client client, String newPassword); 
}

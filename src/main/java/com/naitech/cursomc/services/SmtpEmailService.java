package com.naitech.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SmtpEmailService extends AbstractEmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Autowired
	private MailSender mailSender; 
	
	@Override
	public void sendEmail(SimpleMailMessage simpleMailMessage) {
		LOG.info("Simulating email sending");
		mailSender.send(simpleMailMessage);
		LOG.info("Emnail sent");		
	}

}

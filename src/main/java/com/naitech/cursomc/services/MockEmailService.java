package com.naitech.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import jakarta.mail.internet.MimeMessage;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage simpleMailMessage) {
		LOG.info("Simulating email sending");
		LOG.info(simpleMailMessage.toString());
		LOG.info("Emnail sent");
	}

	@Override
	public void sendHtmlEmail(MimeMessage mimeMessage) {
		LOG.info("Simulating html email sending");
		LOG.info(mimeMessage.toString());
		LOG.info("Emnail sent");
	}

}

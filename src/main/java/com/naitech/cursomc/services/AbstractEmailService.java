package com.naitech.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.naitech.cursomc.domain.ClientOrder;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

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

	protected String htmlFromTemplatePedido(ClientOrder clientOrder) {
		Context context = new Context();
		context.setVariable("clientOrder", clientOrder);

		return templateEngine.process("email/confirmationOrder", context);
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(ClientOrder clientOrder) {
		MimeMessage mimeMessage;
		try {
			mimeMessage = prepareMimeMessageFromOrder(clientOrder);
			sendHtmlEmail(mimeMessage);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(clientOrder);
		}
	}

	protected MimeMessage prepareMimeMessageFromOrder(ClientOrder clientOrder) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setTo(clientOrder.getClient().getEmail());
		mimeMessageHelper.setFrom(sender);
		mimeMessageHelper.setSubject("Order confirmed! Code: " + clientOrder.getId());
		mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeMessageHelper.setText(htmlFromTemplatePedido(clientOrder), true);

		return mimeMessage;
	}
}

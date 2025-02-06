package com.naitech.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.naitech.cursomc.controllers.exception.FieldMessage;
import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.dto.ClientDTO;
import com.naitech.cursomc.repositories.ClientRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Override
	public void initialize(ClientUpdate ann) {
	}

	@Override
	public boolean isValid(ClientDTO clientDTO, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) httpServletRequest
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		List<FieldMessage> fieldMessages = new ArrayList<>();

		Client client = clientRepository.findByEmail(clientDTO.getEmail());
		if (client != null && !client.getId().equals(uriId)) {
			fieldMessages.add(new FieldMessage("email", "Email already exists"));
		}

		for (FieldMessage fieldMessage : fieldMessages) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
					.addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		}
		return fieldMessages.isEmpty();
	}
}

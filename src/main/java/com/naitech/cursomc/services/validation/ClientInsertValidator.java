package com.naitech.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import com.naitech.cursomc.controllers.exception.FieldMessage;
import com.naitech.cursomc.domain.enums.ClientType;
import com.naitech.cursomc.dto.ClientNewDTO;
import com.naitech.cursomc.services.validation.utils.BR;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {

	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClientNewDTO clientNewDTO, ConstraintValidatorContext context) {

		List<FieldMessage> fieldMessages = new ArrayList<>();
		
		if(clientNewDTO.getClientType().equals(ClientType.PESSOAFISICA.getId()) && !BR.isValidCPF(clientNewDTO.getCpfOuCnpj())) {
			fieldMessages.add(new FieldMessage("cpfOuCnpj", "Invalid CPF"));
		}
		
		if(clientNewDTO.getClientType().equals(ClientType.PESSOAJURIDICA.getId()) && !BR.isValidCNPJ(clientNewDTO.getCpfOuCnpj())) {
			fieldMessages.add(new FieldMessage("cpfOuCnpj", "Invalid CNPJ"));
		}

		for (FieldMessage fieldMessage : fieldMessages) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
					.addPropertyNode(fieldMessage.getFieldName()).addConstraintViolation();
		}
		return fieldMessages.isEmpty();
	}
}

package com.naitech.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.naitech.cursomc.domain.enums.PaymentStatus;

import jakarta.persistence.Entity;

@Entity
@JsonTypeName("cardPayment")
public class CardPayment extends Payment {
	private static final long serialVersionUID = 1L;

	private Integer instalmentsNumber;

	public CardPayment() {
		super();
	}

	public CardPayment(Integer id, PaymentStatus paymentStatus, ClientOrder order, Integer instalmentsNumber) {
		super(id, paymentStatus, order);
		this.instalmentsNumber = instalmentsNumber;
	}

	public Integer getInstalmentsNumber() {
		return instalmentsNumber;
	}

	public void setInstalmentsNumber(Integer instalmentsNumber) {
		this.instalmentsNumber = instalmentsNumber;
	}

}

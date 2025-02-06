package com.naitech.cursomc.domain;

import java.util.Date;

import com.naitech.cursomc.domain.enums.PaymentStatus;

import jakarta.persistence.Entity;

@Entity
public class BankSlipPayment extends Payment {
	private static final long serialVersionUID = 1L;

	private Date dueDate;
	private Date paymentDate;

	public BankSlipPayment() {
	}

	public BankSlipPayment(Integer id, PaymentStatus paymentStatus, ClientOrder order, Date dueDate, Date paymentDate) {
		super(id, paymentStatus, order);
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}

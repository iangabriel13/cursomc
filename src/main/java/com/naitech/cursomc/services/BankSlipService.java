package com.naitech.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.naitech.cursomc.domain.BankSlipPayment;

@Service
public class BankSlipService {

	public void fillBankSlipPayment(BankSlipPayment bankSlipPayment, Date dateOrder) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOrder);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		bankSlipPayment.setDueDate(calendar.getTime());
	}
}

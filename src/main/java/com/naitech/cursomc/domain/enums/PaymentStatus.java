package com.naitech.cursomc.domain.enums;

public enum PaymentStatus {
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int id;
	private String description;
	
	private PaymentStatus(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static PaymentStatus toEnum(Integer id) {
		if(id == null) {
			return null;
		}
		
		for(PaymentStatus paymentStatus : PaymentStatus.values()) {
			if(id.equals(paymentStatus.getId())) {
				return paymentStatus;
			}
		}
		
		throw new IllegalArgumentException("Invalid id: " + id);
	}
}

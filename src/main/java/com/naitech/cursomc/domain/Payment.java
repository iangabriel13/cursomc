package com.naitech.cursomc.domain;

import java.io.Serializable;
import java.util.Objects;

import com.naitech.cursomc.domain.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Payment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer paymentStatus;
	
	@OneToOne
	@JoinColumn(name = "order_id")
	@MapsId
	private ClientOrder order;

	public Payment() {
	}

	public Payment(Integer id, PaymentStatus paymentStatus, ClientOrder order) {
		this.id = id;
		this.paymentStatus = paymentStatus.getId();
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PaymentStatus getPaymentStatus() {
		return PaymentStatus.toEnum(paymentStatus);
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus.getId();
	}

	public ClientOrder getOrder() {
		return order;
	}

	public void setOrder(ClientOrder order) {
		this.order = order;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, order, paymentStatus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(id, other.id) && Objects.equals(order, other.order)
				&& paymentStatus == other.paymentStatus;
	}
	
}

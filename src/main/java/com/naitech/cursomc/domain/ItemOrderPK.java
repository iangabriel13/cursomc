package com.naitech.cursomc.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ItemOrderPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "client_order_id")
	private ClientOrder clientOrder;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	public ClientOrder getClientOrder() {
		return clientOrder;
	}

	public void setClientOrder(ClientOrder clientOrder) {
		this.clientOrder = clientOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clientOrder, product);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemOrderPK other = (ItemOrderPK) obj;
		return Objects.equals(clientOrder, other.clientOrder) && Objects.equals(product, other.product);
	}

}

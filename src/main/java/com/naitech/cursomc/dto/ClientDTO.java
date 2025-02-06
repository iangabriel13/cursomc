package com.naitech.cursomc.dto;

import java.io.Serializable;

import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.services.validation.ClientUpdate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@ClientUpdate
public class ClientDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Cannot be empty")
	@Size(min = 5, max = 80, message = "The length should be between 5 and 80 characters")
	private String name;

	@NotEmpty(message = "Cannot be empty")
	@Email(message = "Invalid email")
	private String email;

	public ClientDTO() {
	}

	public ClientDTO(Client client) {
		id = client.getId();
		name = client.getName();
		email = client.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

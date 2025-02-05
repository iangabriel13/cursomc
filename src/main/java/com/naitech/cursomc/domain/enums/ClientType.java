package com.naitech.cursomc.domain.enums;

public enum ClientType {

	PESSOAFISICA(1, "Pessoa Fisica"),
	PESSOAJURIDICA(2, "Pessoa Juridica");
	
	private int id;
	private String description;
	
	private ClientType(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static ClientType toEnum(Integer id) {
		if(id == null) {
			return null;
		}
		
		for(ClientType clientType : ClientType.values()) {
			if(id.equals(clientType.getId())) {
				return clientType;
			}
		}
		
		throw new IllegalArgumentException("Invalid id: " + id);
	}
	
}

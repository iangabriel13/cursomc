package com.naitech.cursomc.domain.enums;

public enum Role {

	ADMIN(1, "ROLE_ADMIN"), 
	USER(2, "ROLE_USER");

	private int id;
	private String description;

	private Role(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static Role toEnum(Integer id) {
		if (id == null) {
			return null;
		}

		for (Role role : Role.values()) {
			if (id.equals(role.getId())) {
				return role;
			}
		}

		throw new IllegalArgumentException("Invalid id: " + id);
	}
}

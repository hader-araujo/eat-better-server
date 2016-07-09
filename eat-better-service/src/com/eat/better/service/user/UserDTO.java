package com.eat.better.service.user;

import com.eat.better.service.GenenricDTO;

public class UserDTO implements GenenricDTO {

	private static final long serialVersionUID = 3878373869470081599L;

	public static enum FIELD {
		LOGIN, NAME
	};

	private String login;
	private String name;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

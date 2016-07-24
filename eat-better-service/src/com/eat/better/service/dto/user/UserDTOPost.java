package com.eat.better.service.dto.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.eat.better.entity.User;
import com.eat.better.service.dto.GenenricDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDTOPost implements GenenricDTO {

	@JsonIgnore
	private static final long serialVersionUID = 4824005606745947284L;

	@JsonIgnore
	private Long id;

	@NotNull
	@Size(min = 3, max = 20)
	private String login;
	@NotNull
	@Size(min = 5, max = 30)
	private String name;

	public UserDTOPost() {
	}

	public UserDTOPost(User user) {
		this.setId(user.getId());
		this.setLogin(user.getLogin());
		this.setName(user.getName());
	}

	public UserDTOPost(UserDTOPut userPut) {
		this.setId(userPut.getId());
		this.setLogin(userPut.getLogin());
		this.setName(userPut.getName());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTOPost other = (UserDTOPost) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDTOPost [id=" + id + ", login=" + login + ", name=" + name + "]";
	}
}

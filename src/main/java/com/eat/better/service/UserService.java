package com.eat.better.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eat.better.model.entity.UserEntity;
import com.eat.better.model.repository.UserRepository;
import com.eat.better.service.exception.EntityFieldNullPointerException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public UserEntity saveAndFlush(String login, String name) throws EntityFieldNullPointerException {
		
		if (login == null) {
			throw new EntityFieldNullPointerException(UserEntity.class, "Login");
		}

		if (name == null) {
			throw new EntityFieldNullPointerException(UserEntity.class, "Name");
		}

		UserEntity user = new UserEntity();
		user.setLogin(login);
		user.setName(name);
		return repository.saveAndFlush(user);
	}
	
	public UserEntity getOne(Long id) {
		return repository.findOne(id);
	}
	
	public List<UserEntity> findAll() {
		return repository.findAll();
	}
}

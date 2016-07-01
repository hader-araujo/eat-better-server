package com.eat.better.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eat.better.model.entity.SystemVersionEntity;
import com.eat.better.model.repository.SystemVersionJpaRespository;

@Service
public class SystemVersionService {
	@Autowired
	private SystemVersionJpaRespository repository;

	public void saveAndFlush(SystemVersionEntity entity) {
		repository.saveAndFlush(entity);
	}

	public SystemVersionEntity findOne(Long id) {
		return repository.findOne(id);
	}
}

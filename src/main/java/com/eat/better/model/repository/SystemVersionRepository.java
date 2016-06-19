package com.eat.better.model.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.eat.better.model.entity.SystemVersionEntity;

@Repository
public class SystemVersionRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public SystemVersionEntity create(SystemVersionEntity entity) {
		entityManager.persist(entity);
		entityManager.flush();
		return entity;
	}

	public SystemVersionEntity find(Long id) {
		return entityManager.find(SystemVersionEntity.class, id);
	}

}

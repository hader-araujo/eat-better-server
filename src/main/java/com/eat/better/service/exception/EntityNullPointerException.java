package com.eat.better.service.exception;

import java.io.Serializable;

public class EntityNullPointerException extends Exception {

	private static final long serialVersionUID = -8722809437007865134L;
	private Class<? extends Serializable> entity;

	public EntityNullPointerException(Class<? extends Serializable> entity) {
		super(entity.getName());
		this.entity = entity;
	}

	public Class<? extends Serializable> getEntity() {
		return entity;
	}

}

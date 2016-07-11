package com.eat.better.service.exception;

import java.io.Serializable;

public class FieldNullPointerException extends Exception {

	private static final long serialVersionUID = -8722809437007865134L;
	private String fieldName;
	private Class<? extends Serializable> entity;

	public FieldNullPointerException(Class<? extends Serializable> entity, String fieldName) {
		super(String.format("%s-%s", entity.getName(), fieldName));
		this.entity = entity;
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Class<? extends Serializable> getEntity() {
		return entity;
	}

}

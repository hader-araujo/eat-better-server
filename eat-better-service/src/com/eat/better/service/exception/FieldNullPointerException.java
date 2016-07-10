package com.eat.better.service.exception;

import java.io.Serializable;

import com.eat.better.service.exception.crudgeneric.CreateGenericException;

public class FieldNullPointerException extends CreateGenericException {

	private static final long serialVersionUID = -8722809437007865134L;
	private String fieldName;

	public FieldNullPointerException(Class<? extends Serializable> entity, String fieldName) {
		super(String.format("%s-%s", entity.getName(), fieldName));
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

}

package com.eat.better.service.exception;

import java.io.Serializable;

public class FieldNullPointerException extends DTONullPointerException {

	private static final long serialVersionUID = -8722809437007865134L;
	private String fieldName;

	public FieldNullPointerException(Class<? extends Serializable> entity, String fieldName) {
		super(entity);
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

}

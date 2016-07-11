package com.eat.better.service.exception;

import java.io.Serializable;

import com.eat.better.service.exception.crudgeneric.UpdateGenericException;

public class FieldReadOnlyException extends UpdateGenericException {

	private static final long serialVersionUID = -8722809437007865134L;
	private String fieldName;

	public FieldReadOnlyException(Class<? extends Serializable> entity, String fieldName) {
		super(String.format("%s-%s", entity.getName(), fieldName));
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

}

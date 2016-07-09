package com.eat.better.service.exception;

import java.io.Serializable;

import com.eat.better.service.exception.crudgeneric.CreateGenericException;

public class DTONullPointerException extends CreateGenericException {

	private static final long serialVersionUID = -8722809437007865134L;
	private Class<? extends Serializable> dto;

	public DTONullPointerException(Class<? extends Serializable> dto) {
		super(dto.getName());
		this.dto = dto;
	}

	public Class<? extends Serializable> getDto() {
		return dto;
	}


}

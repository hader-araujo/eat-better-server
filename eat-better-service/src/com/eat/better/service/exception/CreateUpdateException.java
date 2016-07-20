package com.eat.better.service.exception;

import com.eat.better.service.exception.enums.CreateUpdateExceptionMessageEnum;

public class CreateUpdateException extends Exception {

	private static final long serialVersionUID = 7495016393091978867L;

	public CreateUpdateException(CreateUpdateExceptionMessageEnum  createUpdateExceptionMessage) {
		super(createUpdateExceptionMessage.name());
	}
	
	public CreateUpdateException(CreateUpdateExceptionMessageEnum  createUpdateExceptionMessage, Exception e) {
		super(createUpdateExceptionMessage.name(), e);
	}
}

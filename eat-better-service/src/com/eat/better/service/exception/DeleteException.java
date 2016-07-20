package com.eat.better.service.exception;

import com.eat.better.service.exception.enums.DeleteExceptionMessageEnum;

public class DeleteException extends Exception {

	private static final long serialVersionUID = 8495641441044847186L;

	public DeleteException(DeleteExceptionMessageEnum deleteExceptionMessage) {
		super(deleteExceptionMessage.toString());
	}
	
	public DeleteException(DeleteExceptionMessageEnum deleteExceptionMessage, Exception e) {
		super(deleteExceptionMessage.toString(), e);
	}
}

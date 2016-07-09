package com.eat.better.service.exception.crudgeneric;

public class DeleteGenericException extends Exception {

	private static final long serialVersionUID = -8722809437007865134L;

	public DeleteGenericException() {
		super();
	}

	public DeleteGenericException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeleteGenericException(String message) {
		super(message);
	}

	public DeleteGenericException(Throwable cause) {
		super(cause);
	}

	public DeleteGenericException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

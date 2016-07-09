package com.eat.better.service.exception.crudgeneric;

public class CreateGenericException extends Exception {

	private static final long serialVersionUID = -8722809437007865134L;

	public CreateGenericException() {
		super();
	}

	public CreateGenericException(String message, Throwable cause) {
		super(message, cause);
	}

	public CreateGenericException(String message) {
		super(message);
	}

	public CreateGenericException(Throwable cause) {
		super(cause);
	}

	public CreateGenericException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

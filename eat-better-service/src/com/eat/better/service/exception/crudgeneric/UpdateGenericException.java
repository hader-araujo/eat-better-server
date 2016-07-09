package com.eat.better.service.exception.crudgeneric;

public class UpdateGenericException extends Exception {

	private static final long serialVersionUID = -8722809437007865134L;

	public UpdateGenericException() {
		super();
	}

	public UpdateGenericException(String message, Throwable cause) {
		super(message, cause);
	}

	public UpdateGenericException(String message) {
		super(message);
	}

	public UpdateGenericException(Throwable cause) {
		super(cause);
	}

	public UpdateGenericException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

package com.eat.better.service.exception.crudgeneric;

public class ReadGenericException extends Exception {

	private static final long serialVersionUID = -8722809437007865134L;

	public ReadGenericException() {
		super();
	}

	public ReadGenericException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReadGenericException(String message) {
		super(message);
	}

	public ReadGenericException(Throwable cause) {
		super(cause);
	}

	public ReadGenericException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

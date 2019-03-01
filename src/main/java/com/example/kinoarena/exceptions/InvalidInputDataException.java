package com.example.kinoarena.exceptions;

public class InvalidInputDataException extends Exception {

	private static final long serialVersionUID = 4058838472671315175L;

	public InvalidInputDataException() {
		super();
	}

	public InvalidInputDataException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidInputDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInputDataException(String message) {
		super(message);
	}

	public InvalidInputDataException(Throwable cause) {
		super(cause);
	}

	
}

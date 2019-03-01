package com.example.kinoarena.exceptions;

public class NotAdminException extends Exception {

	private static final long serialVersionUID = -7444897604162092416L;

	public NotAdminException() {
		super();
	}

	public NotAdminException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotAdminException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAdminException(String message) {
		super(message);
	}

	public NotAdminException(Throwable cause) {
		super(cause);
	}
}

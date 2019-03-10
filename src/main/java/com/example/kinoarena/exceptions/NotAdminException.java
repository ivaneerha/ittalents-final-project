package com.example.kinoarena.exceptions;

public class NotAdminException extends KinoArenaException {

	private static final long serialVersionUID = -7444897604162092416L;

	public NotAdminException() {
		super("You have unauthorized access! Not an admin!");
	}
}

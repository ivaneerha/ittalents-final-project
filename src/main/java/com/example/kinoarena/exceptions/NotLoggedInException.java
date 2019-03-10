package com.example.kinoarena.exceptions;

public class NotLoggedInException extends KinoArenaException {

	private static final long serialVersionUID = 1313214L;

	public NotLoggedInException() {
		super("You are not logged");
	}
}

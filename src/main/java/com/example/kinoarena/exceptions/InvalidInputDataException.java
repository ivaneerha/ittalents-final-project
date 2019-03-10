package com.example.kinoarena.exceptions;

public class InvalidInputDataException extends KinoArenaException {

	private static final long serialVersionUID = 4058838472671315175L;

	public InvalidInputDataException() {
		super("Wrong Input, please try again!");
	}
}

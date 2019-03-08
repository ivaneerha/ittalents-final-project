
package com.example.kinoarena.helper;

import com.example.kinoarena.dto.LoginDto;
import com.example.kinoarena.dto.RegisterDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;

public class UserValidation {
	
	private static final int MIN_LENGTH_OF_PASSWORD = 8;
	private static final int MIN_LENGTH_OF_EMAIL = 8;
	private static final int MIN_NAMES_LENGTH = 2;
	//private final static String EMAIL_PATTERN = "^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{8,30}$";
	private static final String EMAIL_PATTERN = "^[A-Za-z0-9]+@[A-Za-z]+(\\.[A-Za-z]+)+$";
	private final static String PASSWORD_PATTERN = "^[a-zA-Z0-9]{8,30}$";
	private final static String NAME_PATTERN = "^[a-zA-z]{2,30}$";
	private final static String USERNAME_PATTERN = "^[a-zA-z]{5,30}$";
	
	
	public void validateEmail(String email) throws KinoArenaException {
		if (email==null || !email.matches(EMAIL_PATTERN)) {
			throw new InvalidInputDataException();
		}
	}

	public void validatePassword(String password) throws KinoArenaException {
		if (password==null || !password.matches(PASSWORD_PATTERN)) {
			throw new InvalidInputDataException();
		}
	}
	
	public void validateName(String name) throws KinoArenaException {
		if(name==null || !name.matches(NAME_PATTERN)){
			throw new InvalidInputDataException();
		}
	}
	
	public void validateUsername(String username) throws KinoArenaException {
		if(username==null || !username.matches(USERNAME_PATTERN)){
			throw new InvalidInputDataException();
		}
	}
	
	public void validateRegistration(RegisterDto reg) throws KinoArenaException {
		validateName(reg.getFirstName());
		validateName(reg.getLastName());
		validateEmail(reg.getEmail());
		validateUsername(reg.getUsername());
		validatePassword(reg.getPassword());
	}
	
	public void validateLogin(LoginDto log) throws KinoArenaException {
		validateUsername(log.getUsername());
		validatePassword(log.getPassword());
		System.out.println("Ima li wryzka ?");
	}
	
	
	
}
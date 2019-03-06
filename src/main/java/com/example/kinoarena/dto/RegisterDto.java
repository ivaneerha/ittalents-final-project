package com.example.kinoarena.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NonNull;

@Data
public class RegisterDto {

	
	private String firstName;
	private String password;
	private String email;
	private String lastName;
	private String username;	
}

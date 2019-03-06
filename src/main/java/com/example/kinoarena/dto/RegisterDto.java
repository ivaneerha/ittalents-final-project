package com.example.kinoarena.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NonNull;

@Data
public class RegisterDto {

	@NotNull
	private String firstName;
	@NotNull
	private String password;
	@NotNull
	private String email;
	@NotNull
	private String lastName;
	@NotNull
	private String username;	
}

package com.example.kinoarena.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

	@NonNull
	private String firstName;
	@NonNull
	private String password;
	@NonNull
	private String email;
	@NonNull
	private String lastName;
	@NonNull
	private String username;	
}

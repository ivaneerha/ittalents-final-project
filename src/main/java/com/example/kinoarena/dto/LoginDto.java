package com.example.kinoarena.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {
	
	private String username;
	private String password;

}

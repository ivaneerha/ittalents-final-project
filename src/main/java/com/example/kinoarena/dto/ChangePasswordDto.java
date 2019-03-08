package com.example.kinoarena.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ChangePasswordDto {
	
	
	@NonNull
	private String oldPass;
	@NonNull
	private String newPass;
	

}

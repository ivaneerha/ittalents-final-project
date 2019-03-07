package com.example.kinoarena.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ProfileDto {
	
	private Long userId;
	private String username;

	
}

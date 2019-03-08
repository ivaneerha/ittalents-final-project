package com.example.kinoarena.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

@Data
public class ProfileDto {
	
	
	private String favouriteMovie;
	private String favouriteActor;
	private String gsm;
	
}

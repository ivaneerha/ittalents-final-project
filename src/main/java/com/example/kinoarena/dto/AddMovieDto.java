package com.example.kinoarena.dto;

import javax.persistence.Column;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class AddMovieDto {
	
	@NonNull
	private String title;
	private int genreId;
}

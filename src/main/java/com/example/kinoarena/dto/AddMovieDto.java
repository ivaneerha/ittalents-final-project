package com.example.kinoarena.dto;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddMovieDto {
	private String title;
	private int genreId;
}

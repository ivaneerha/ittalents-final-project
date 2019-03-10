package com.example.kinoarena.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ChangeMovieDto {

	@NonNull
	private String title;
	@NonNull
	private Long genreId;
	@NonNull
	private Long movieId;
}

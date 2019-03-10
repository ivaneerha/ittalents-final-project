package com.example.kinoarena.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class AddHallDto {

	@NonNull
	private String type;
	@NonNull
	private Long cinemaId;

}

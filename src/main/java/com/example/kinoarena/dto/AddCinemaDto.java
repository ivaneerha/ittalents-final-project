package com.example.kinoarena.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


@Getter
@Setter
public class AddCinemaDto {
	
	@NonNull
	private String name;
	@NonNull
	private String contact;
	@NonNull
	private Long locatonId;

}

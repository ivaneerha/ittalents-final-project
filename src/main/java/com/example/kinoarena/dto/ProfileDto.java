package com.example.kinoarena.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ProfileDto {

	@NonNull
	private String name;
	@NonNull
	private String lastName;
	private String gsm;
	@NonNull
	private String city;
	@NonNull
	private String address;

}

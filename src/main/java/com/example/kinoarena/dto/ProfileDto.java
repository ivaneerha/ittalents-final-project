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

	private String city;

	private String address;

}

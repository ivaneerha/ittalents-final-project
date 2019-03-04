package com.example.kinoarena.model;

import java.util.ArrayList;
import java.util.List;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.helper.RegexPatterns;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class User {

	private long user_id;
	private String username;
	@JsonIgnore
	private String password;
	private String firstName;
	private String lastName;
	private long location_id;
	private String email;
	private String gsm;
	private boolean isAdmin;
	private String favouriteMovie;
	private String favouriteActor;
	
}

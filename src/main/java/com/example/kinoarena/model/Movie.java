package com.example.kinoarena.model;

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
public class Movie {
	
	private int id;
	//in minutes
	private int length;
	private String title;
	private int genreId;
	

}

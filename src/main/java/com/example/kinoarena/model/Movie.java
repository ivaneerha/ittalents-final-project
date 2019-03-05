package com.example.kinoarena.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
@Entity(name="movies")
public class Movie {
	
	@Id
	private int id;
	//in minutes
	@Column
	private int length;
	@Column
	private String title;
	@Column
	private int genreId;
	

}

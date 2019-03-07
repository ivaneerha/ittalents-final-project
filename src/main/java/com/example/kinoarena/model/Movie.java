package com.example.kinoarena.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
	private Integer movieId;
	//in minutes
	@NonNull @Column
	private Integer length;
	@NonNull @Column
	private String title;
	@NonNull @Column
	private Integer genreId;
	
}

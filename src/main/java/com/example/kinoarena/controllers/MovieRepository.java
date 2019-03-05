package com.example.kinoarena.controllers;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kinoarena.model.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long>{
	
	
	Movie findByTitle(String title);
	
	ArrayList<Movie> findAllByTitle(String title);
		
	List<Movie> findByGenreId(int genreId);

}
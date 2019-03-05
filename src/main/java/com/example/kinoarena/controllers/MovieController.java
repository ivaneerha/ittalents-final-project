package com.example.kinoarena.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.model.Movie;

@RestController
public class MovieController {
	
	@Autowired
	private MovieRepository movieRepository;
	
	/**
	 * Method for searching all movies by name in all cinemas.
	 */
	@GetMapping("movies/name")
	public ArrayList<Movie> listMovies(@RequestParam String name) {
		return movieRepository.findAllByName(name);
	}
	
	/**
	 * Method for searching a movie by name.
	 */
	@GetMapping("movie/name")
	public Movie getMovie(@RequestParam String name) {
		return movieRepository.findByName(name);
	}
	
	
	
}

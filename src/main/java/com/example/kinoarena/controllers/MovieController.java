package com.example.kinoarena.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Movie;

@RestController
public class MovieController extends BaseController{
	
	@Autowired
	private MovieRepository movieRepository;
	
	
	
	/**
	 * Method for searching all movies by title in all cinemas.
	 */
	@GetMapping("/movies/title")
	public ArrayList<Movie> listMovies(@RequestParam String title) throws KinoArenaException{
		return movieRepository.findAllByTitle(title);
	}
	
	/**
	 * Method for searching a movie by name.
	 */
	@GetMapping("/movie/title")
	public Movie getMovie(@RequestParam String title) throws KinoArenaException{
		return movieRepository.findByTitle(title);
	}
	
	//Works
	@GetMapping("/movie/{id}")
	public Movie getMovieById(@PathVariable("id") int id, HttpServletResponse response) throws KinoArenaException{
		Movie movie = movieRepository.findByMovieId(id);
		if(movie != null) {
			return movie;
		} else {
			throw new KinoArenaException("There is no movie with this id!");
		}
	}

	
}

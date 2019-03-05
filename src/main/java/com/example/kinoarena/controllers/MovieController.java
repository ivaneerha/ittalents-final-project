package com.example.kinoarena.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.MovieDao;
import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.dto.AddMovieDto;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Movie;

@RestController
public class MovieController extends BaseController{
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MovieDao movieDao;
	
	/**
	 * Method for searching all movies by name in all cinemas.
	 */
	@GetMapping("movies/title")
	public ArrayList<Movie> listMovies(@RequestParam String title) {
		return movieRepository.findAllByTitle(title);
	}
	
	/**
	 * Method for searching a movie by title.
	 */
	@GetMapping("movie/title")
	public Movie getMovie(@RequestParam String title) {
		return movieRepository.findByTitle(title);
	}
	
	@GetMapping("movie/genre/{id}")
	public List<Movie> findByGenreId(@PathVariable int id) {
		return movieRepository.findByGenreId(id);
	}
	
	@DeleteMapping("movie/delete/{id}")
	public void deleteMovieById(HttpSession session,@PathVariable long id) throws KinoArenaException {
		validateLoginAdmin(session);
		movieRepository.deleteById(id);
	}
	
	@PostMapping("movie/add")
	public void addNewMovie(@RequestBody AddMovieDto movieDto, HttpSession session) throws KinoArenaException, SQLException {
		validateLoginAdmin(session);
		movieDao.addNewMovie(movieDto);
	}
	
}

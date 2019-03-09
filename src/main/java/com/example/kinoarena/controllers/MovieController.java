package com.example.kinoarena.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.MovieDao;
import com.example.kinoarena.dto.MovieDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Movie;

@RestController
public class MovieController extends BaseController {

	@Autowired
	private MovieDao movieDao;

	@Autowired
	private MovieRepository movieRepository;

	// Works
	@GetMapping("/movie/{id}")
	public Movie getMovieById(@PathVariable("id") Long id, HttpServletResponse response) throws KinoArenaException {
		Movie movie = movieRepository.findByMovieId(id);
		if (movie != null) {
			return movie;
		} else {
			throw new KinoArenaException("There is no movie with this id!");
		}
	}

	// Works
	@PostMapping("/movie/add")
	public void addMovie(@RequestBody MovieDto movieDto, HttpServletRequest request, HttpSession session)
			throws SQLException, KinoArenaException {
		validateLoginAdmin(session);
		try {
			if (movieDao.findIfMovieExists(movieDto)) {
				throw new KinoArenaException("The movie already exists!");
			}
			Movie movie = new Movie();
			movie.setTitle(movieDto.getTitle());
			movie.setGenreId(movieDto.getGenreId());
			movieDao.addNewMovie(movieDto);
		} catch (SQLException e) {
			throw new InvalidInputDataException();
		}
	}

	// DOESN'T WORK
	@DeleteMapping("/movie/delete/{id}")
	public void deleteMovie(@PathVariable Long id, HttpServletRequest request, HttpSession session)
			throws KinoArenaException {
		validateLoginAdmin(session);
		Movie movie = movieRepository.findByMovieId(id);
		if (movie != null) {
			movieRepository.deleteById(id);
		} else {
			throw new KinoArenaException("There is no movie with this id!");
		}
	}

	// DOESN'T WORK
	@PostMapping("/movie/find/title")
	public Movie findMovieByTitle(@RequestBody String title) throws KinoArenaException {
		Movie movie = new Movie();
		try {
			movie = movieDao.findMovieByTitle(title);
			if (movie == null) {
				throw new KinoArenaException("There is no movie with this title!");
			} else {
				return movie;
			}
		} catch (SQLException e) {
			throw new InvalidInputDataException();
		}
	}

	// Works
	@GetMapping("/movie/all")
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

}

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.MovieDao;
import com.example.kinoarena.dto.ChangeMovieDto;
import com.example.kinoarena.dto.MovieDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.exceptions.MovieNotFoundException;
import com.example.kinoarena.helper.UserValidation;
import com.example.kinoarena.model.Movie;

@RestController
public class MovieController extends BaseController {

	private static final String GENRE_NOT_FOUND = "There is not such genre!";
	private static final String MOVIE_NOT_FOUND = "There is no movie with this id!";
	private static final String MOVIE_ALREADY_EXISTS = "The movie already exists!";
	
	private UserValidation validation = new UserValidation();

	@Autowired
	private MovieDao movieDao;

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private GenreRepository genreRepository;

	// Works
	@GetMapping("/movie/{id}")
	public Movie getMovieById(@PathVariable("id") Long id, HttpServletResponse response) throws KinoArenaException {
		Movie movie = movieRepository.findByMovieId(id);
		if (movie != null) {
			return movie;
		} else {
			throw new KinoArenaException(MOVIE_NOT_FOUND);
		}
	}
	
	@PutMapping("/movie/change")
	public void changeMovieTitle(@RequestBody ChangeMovieDto dto, HttpSession session) throws KinoArenaException, SQLException {
		validateLoginAdmin(session);
		String title = dto.getTitle();
		Long genreId = dto.getGenreId();
		Long movieId = dto.getMovieId();
		if(movieRepository.findByMovieId(movieId) == null) {
			throw new KinoArenaException(MOVIE_NOT_FOUND);
		}
		validation.validateString(title);
		if(!genreRepository.findById(genreId).isPresent()) {
			throw new KinoArenaException(GENRE_NOT_FOUND);
		}
		movieDao.saveChanges(movieId, title, genreId);
	}

	// Works
	@PostMapping("/movie/add")
	public void addMovie(@RequestBody MovieDto movieDto, HttpServletRequest request, HttpSession session)
			throws SQLException, KinoArenaException {
		validateLoginAdmin(session);
		try {
			if (movieDao.findIfMovieExists(movieDto)) {
				throw new KinoArenaException(MOVIE_ALREADY_EXISTS);
			}
			Movie movie = new Movie();
			movie.setTitle(movieDto.getTitle());
			movie.setGenreId(movieDto.getGenreId());
			movieDao.addNewMovie(movieDto);
		} catch (SQLException e) {
			throw new InvalidInputDataException();
		}
	}

	// Works
	@DeleteMapping("/movie/delete/{id}")
	public void deleteMovie(@PathVariable Long id, HttpServletRequest request, HttpSession session)
			throws KinoArenaException {
		validateLoginAdmin(session);
		if (movieRepository.existsById(id)) {
			movieRepository.deleteById(id);
		} else {
			throw new MovieNotFoundException();
		}
	}

	// Works
	@GetMapping("/movie/all")
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

}

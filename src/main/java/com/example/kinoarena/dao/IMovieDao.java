package com.example.kinoarena.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.kinoarena.dto.MovieDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Movie;

public interface IMovieDao {
	
	public void addNewMovie(MovieDto dto) throws SQLException, InvalidInputDataException, KinoArenaException;
	
	public ArrayList<Movie> getAllMovies() throws SQLException, InvalidInputDataException;

	public Movie getMovieById(int id) throws SQLException, InvalidInputDataException;
	
	public ArrayList<String> getAllMoviesNames() throws SQLException, InvalidInputDataException;
	
	public Movie getMovieByTitle(String title) throws SQLException, InvalidInputDataException;

}

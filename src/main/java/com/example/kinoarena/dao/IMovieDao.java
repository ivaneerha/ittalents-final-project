package com.example.kinoarena.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Movie;

public interface IMovieDao {
	
	public void addNewMovie(Movie movie, int genreType) throws SQLException, InvalidInputDataException;
	
	public void deleteMovie(Movie m) throws Exception;

	public ArrayList<Movie> getAllMovies() throws SQLException, InvalidInputDataException;

	public Movie getMovieById(int id) throws SQLException, InvalidInputDataException;
	
	public ArrayList<String> getAllMoviesNames() throws SQLException, InvalidInputDataException;
	
	public Movie getMovieByName(String name) throws SQLException, InvalidInputDataException;

	public ArrayList<String> getAllGenres() throws SQLException;
	
	public int getGenreIdByName(String genre) throws SQLException;

}

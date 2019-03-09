package com.example.kinoarena.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.example.kinoarena.dto.MovieDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Movie;

public interface IMovieDao {

	public void addNewMovie(MovieDto dto) throws SQLException, InvalidInputDataException, KinoArenaException;

	boolean findIfMovieExists(MovieDto dto) throws SQLException;

}

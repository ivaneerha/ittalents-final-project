package com.example.kinoarena.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.dto.AddMovieDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.helper.RandomNumber;
import com.example.kinoarena.model.Movie;

import lombok.Setter;

@Component
public class MovieDao implements IMovieDao{
	
	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;
	
	private static final String ADD_MOVIE = "INSERT INTO movies (length,title,genre_id) VALUES (?,?,?)";
	private static final String ADD_GENRE = "UPDATE  movies SET genre_id = ? WHERE movie_id = ?";
	//duration of a movie in minutes
	private static final int MIN_DURATION_OF_MOVIE = 90;
	private static final int MAX_DURATION_OF_MOVIE = 180;
	/**
	 * Genre types:
	 * 1  horror
	 * 2  romance
	 * 3  drama
	 * 4  comedy
	 * 5  action
	 * 6  adventure
	 * 7  sci-fy
	 */
	
	
	
	//TODO
	
	public int addNewMovie(AddMovieDto dto) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement ps = null;
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(ADD_MOVIE,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,RandomNumber.randomNumber(MIN_DURATION_OF_MOVIE, MAX_DURATION_OF_MOVIE) );
			ps.setString(2, dto.getTitle());
			ps.setInt(3, dto.getGenreId());
			ps.executeUpdate();
			
			
			ResultSet result = ps.getGeneratedKeys();
			result.next();
			int id = (int)result.getLong(1);
			con.commit();
			return id;
		}
		catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			ps.close();
			con.setAutoCommit(true);
		}
	}
	
	@Override
	public ArrayList<Movie> getAllMovies() throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Movie getMovieById(int id) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<String> getAllMoviesNames() throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void deleteMovieById(long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Movie getMovieByTitle(String title) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}



//TODO update genre for movie
//	private void addGenreToMovie(int id) throws SQLException {
//	Connection con = jdbcTemplate.getDataSource().getConnection();
//		PreparedStatement ps = con.prepareStatement(addGenre);
//		ps.set
//	}
	
	
	
	
	
	
}

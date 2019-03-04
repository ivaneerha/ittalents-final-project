package com.example.kinoarena.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.helper.RandomNumber;
import com.example.kinoarena.model.Movie;

import lombok.Setter;

@Component
public class MovieDao {
	
	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;
	
	private static final String addMovie = "INSERT INTO movies (movie_id,length,title,genre_id) VALUES (?,?,?,?)";
	private static final String addGenre = "UPDATE  movies SET genre_id = ? WHERE movie_id = ?";
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
	
	
	
	
	public void addNewMovie(Movie movie, int genreType) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement ps = null;
		try {
			con.setAutoCommit(false);
			ps = con.prepareStatement(addMovie,Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,movie.getId());
			ps.setInt(2,RandomNumber.randomNumber(MIN_DURATION_OF_MOVIE, MAX_DURATION_OF_MOVIE) );
			ps.setString(3, movie.getTitle());
			ps.setInt(4, genreType);
			ps.executeUpdate();
			
			ResultSet result = ps.getGeneratedKeys();
			result.next();
			movie.setId((int)result.getLong(1));
			con.commit();
		}
		catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			ps.close();
			con.setAutoCommit(true);
		}
	}



//TODO update genre for movie
//	private void addGenreToMovie(int id) throws SQLException {
//	Connection con = jdbcTemplate.getDataSource().getConnection();
//		PreparedStatement ps = con.prepareStatement(addGenre);
//		ps.set
//	}
	
	
	
	
	
	
}

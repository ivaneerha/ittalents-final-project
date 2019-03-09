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

import com.example.kinoarena.dto.MovieDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.helper.RandomNumber;
import com.example.kinoarena.helper.UserValidation;
import com.example.kinoarena.model.Movie;

import lombok.Setter;

@Component
public class MovieDao implements IMovieDao {

	private static final String FIND_MOVIE_BY_TITLE_AND_GENRE_ID = "SELECT * from movies where title = ? and genre_id = ?;";

	private static final String ADD_MOVIE = "insert into movies(title, genre_id) values (?, ?)";

	private static final String GENRE_NOT_FOUND = "There is no genre with id = ";

	private static final String GENRE_SEARCHING = "select * from genres where genre_id = ?";

	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;

	private UserValidation validation;

	private static final String ADD_GENRE = "UPDATE  movies SET genre_id = ? WHERE movie_id = ?";

	private static final String FIND_MOVIE_BY_TITLE = "SELECT * FROM movies WHERE title = ?;";

	@Override
	public void addNewMovie(MovieDto dto) throws SQLException, KinoArenaException {
		String title = dto.getTitle();
		Long genreId = dto.getGenreId();

		Connection con = jdbcTemplate.getDataSource().getConnection();
		try (PreparedStatement ps = con.prepareStatement(ADD_MOVIE)) {
			ps.setString(1, title);
			ps.setLong(2, genreId);
			ps.executeUpdate();
		}
	}

	@Override
	public boolean findIfMovieExists(MovieDto dto) throws SQLException {
		String title = dto.getTitle();
		Long genreId = dto.getGenreId();
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try (PreparedStatement ps = con.prepareStatement(FIND_MOVIE_BY_TITLE_AND_GENRE_ID)) {
			ps.setString(1, title);
			ps.setLong(2, genreId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		}
	}

	public Movie findMovieByTitle(String title) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try (PreparedStatement ps = con.prepareStatement(FIND_MOVIE_BY_TITLE)) {
			ps.setString(1, title);
			ResultSet rs = ps.executeQuery();
			Movie movie = new Movie();
			if (rs.next()) {
				movie.setMovieId(rs.getLong("movie_id"));
				movie.setTitle(rs.getString("title"));
				movie.setGenreId(rs.getLong("genre_id"));
				return movie;
			} else {
				return null;
			}
		}
	}
}

package com.example.kinoarena.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.example.kinoarena.dto.ProjectionWithMoviesDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Projection;
import lombok.Setter;

@Component
public class ProjectionDao implements IProjectionDao {

	private static final String SELECT_ALL_PROJECTION_FOR_A_MOVIE = "select * from projections where movie_id = ?;";
	private static final String SELECT_ALL_PROJECTIONS_FOR_A_CINEMA = "select * from projections where projection_id in (SELECT projection_id from cinemа_projections where cinema_id = ?);";
	private static final String UPDATE_DATETIME = "update projections set start_time = ?, end_time = ? where projection_id = ?";
	private static final String SELECT_PROJECTION_IDS = "SELECT projection_id from cinemа_projections where cinema_id = ?;";
	private static final String SELECT_ALL_PROJECTIONS = "SELECT * from projections where start_time = ? and movie_id = ?;";
	private static final String SELECT_ALL_PROJECTIONS_WITH_TITLES = "SELECT p.projection_id, p.start_time, p.end_time, m.title from projections p join "
			+ "movies m on (p.movie_id = m.movie_id);";
	
	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;


	@Override
	public List<ProjectionWithMoviesDto> getAllProjectionWithMovieTitles() throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		ResultSet result = con.createStatement().executeQuery(SELECT_ALL_PROJECTIONS_WITH_TITLES);
		List<ProjectionWithMoviesDto> projections = new ArrayList<>();
		while (result.next()) {
			ProjectionWithMoviesDto projection = new ProjectionWithMoviesDto();
			projection.setProjectionId(result.getLong("projection_id"));
			projection.setStartTime(result.getString("start_time"));
			projection.setEndTime(result.getString("end_time"));
			projection.setTitle(result.getString("title"));
			projections.add(projection);
		}
		return projections;
	}

	@Override
	public boolean checkIfProjectionExists(String startTime, Long movieId) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try (PreparedStatement ps = con.prepareStatement(SELECT_ALL_PROJECTIONS);) {
			ps.setString(1, startTime);
			ps.setLong(2, movieId);
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				return true;
			}
			return false;
		}
	}

	@Override
	public List<Long> getProjectionIdsByCinemaId(Long id) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		List<Long> ids = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(SELECT_PROJECTION_IDS);) {
			ps.setLong(1, id);
			try (ResultSet result = ps.executeQuery()) {
				while (result.next()) {
					ids.add(result.getLong("projection_id"));
				}
			}
		}
		return ids;
	}

	@Override
	public void changeProjectionDateTime(Long projectionId, String startTime, String endTime) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try (PreparedStatement ps = con.prepareStatement(UPDATE_DATETIME);) {
			ps.setString(1, startTime);
			ps.setString(2, endTime);
			ps.setLong(3, projectionId);
			ps.executeUpdate();
		}
	}

	@Override
	public List<Projection> getProjectionsByCinemaId(Long id) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		List<Projection> projections = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(SELECT_ALL_PROJECTIONS_FOR_A_CINEMA);) {
			ps.setLong(1, id);
			try (ResultSet result = ps.executeQuery()) {
				while (result.next()) {
					String startDateTime = result.getString("start_time");
					String endDateTime = result.getString("end_time");
					Projection projection = new Projection();
					projection.setStartTime(startDateTime.toString());
					projection.setEndTime(endDateTime.toString());
					projection.setMovieId(result.getLong("movie_id"));
					projection.setProjectionId(result.getLong("projection_id"));
					projections.add(projection);
				}
			}
		}
		return projections;
	}

	@Override
	public List<Projection> getAllProjectionsForAMovie(Long movieId) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		List<Projection> projections = new ArrayList<>();
		try (PreparedStatement ps = con.prepareStatement(SELECT_ALL_PROJECTION_FOR_A_MOVIE);) {
			ps.setLong(1, movieId);
			try (ResultSet result = ps.executeQuery()) {
				while (result.next()) {
					String startDateTime = result.getString("start_time");
					String endDateTime = result.getString("end_time");
					Projection projection = new Projection();
					projection.setStartTime(startDateTime.toString());
					projection.setEndTime(endDateTime.toString());
					projection.setMovieId(result.getLong("movie_id"));
					projection.setProjectionId(result.getLong("projection_id"));
					projections.add(projection);
				}
			}
		}
		return projections;
	}

}

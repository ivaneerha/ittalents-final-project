package com.example.kinoarena.dao;

import java.util.List;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.dto.ProjectionWithMoviesDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.NotAdminException;
import com.example.kinoarena.model.Movie;
import com.example.kinoarena.model.Projection;
import com.example.kinoarena.model.User;

import lombok.Setter;

@Component
public class ProjectionDao implements IProjectionDao{
	
	@Autowired
	@Setter
	private JdbcTemplate jdbcTemplate;
	


	@Override
	public void deleteProjection(int broadcastId) throws SQLException, NotAdminException, InvalidInputDataException {
		
		
	}

	public List<ProjectionWithMoviesDto> getAllProjectionWithMovieTitles() throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		String query = "SELECT p.projection_id, p.start_time, p.end_time, m.title from projections p join "
				+ "movies m on (p.movie_id = m.movie_id);";
		ResultSet result = con.createStatement().executeQuery(query);
		List<ProjectionWithMoviesDto> projections = new ArrayList<>();
		while(result.next()) {
			ProjectionWithMoviesDto projection = new ProjectionWithMoviesDto();
			projection.setProjectionId(result.getLong("projection_id"));
			projection.setStartTime(result.getString("start_time"));
			projection.setEndTime(result.getString("end_time"));
			projection.setTitle(result.getString("title"));
			projections.add(projection);
		}
		return projections;
	}
	
	public boolean checkIfProjectionExists(String startTime, Long movieId) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try(PreparedStatement ps = con.prepareStatement("SELECT * from projections where start_time = ? and movie_id = ?;");){
			ps.setString(1, startTime);
			ps.setLong(2, movieId);
			ResultSet result = ps.executeQuery();
			if(result.next()) {
				return true;
			}
			return false;
		}
	}
	

	@Override
	public Collection<Projection> getAllProjectionsForAMovie(Movie m) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public List<Long> getProjectionIdsByCinemaId(Long id) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		List<Long> ids = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement("SELECT projection_id from cinemа_projections where cinema_id = ?;");){
			ps.setLong(1, id);
			try(ResultSet result = ps.executeQuery()){
				while(result.next()) {
					ids.add(result.getLong("projection_id"));
				}
			}
		}
		return ids;
	}
	
	public void changeProjectionDateTime(Long projectionId, String startTime, String endTime) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		try(PreparedStatement ps = con.prepareStatement("update projections set start_time = ?, end_time = ? where projection_id = ?");){
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
		try(PreparedStatement ps = con.prepareStatement("select * from projections where projection_id in (SELECT projection_id from cinemа_projections where cinema_id = ?);");){
			ps.setLong(1, id);
			try(ResultSet result = ps.executeQuery()){
				while(result.next()) {
					String startDateTime = result.getString("start_time");
					String endDateTime = result.getString("start_time");
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

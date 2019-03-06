package com.example.kinoarena.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeProjectionTime(int projectionId, LocalDateTime projectionTime)
			throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProjection(Projection b) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Projection> getAllProjectionsForAMovie(Movie m) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Projection> getAllPRojections() throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Projection getProjectionById(int id) throws SQLException, InvalidInputDataException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Long> getProjectionIdsByCinemaId(int id) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		List<Long> ids = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement("SELECT projection_id from cinemа_projections where cinema_id = ?;");){
			ps.setInt(1, id);
			try(ResultSet result = ps.executeQuery()){
				while(result.next()) {
					ids.add(result.getLong("projection_id"));
				}
			}
		}
		return ids;
	}
	
	@Override
	public List<Projection> getProjectionsByCinemaId(int id) throws SQLException, InvalidInputDataException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		List<Projection> projections = new ArrayList<>();
		try(PreparedStatement ps = con.prepareStatement("select * from projections where projection_id in (SELECT projection_id from cinemа_projections where cinema_id = ?);");){
//		try(PreparedStatement ps = con.prepareStatement("select p.projection_id, p.start_time,p.end_time, m.title from projections p join movies m on (p.movie_id = m.movie_id) where projection_id in (SELECT projection_id from cinemа_projections where cinema_id = ?);");){
			ps.setInt(1, id);
			try(ResultSet result = ps.executeQuery()){
				while(result.next()) {
					LocalDateTime startDateTime = result.getTimestamp("start_time").toLocalDateTime();
//					Date startDateTime = result.getDate("start_time");
//					Date endDateTime = result.getDate("start_time");
					LocalDateTime endDateTime = result.getTimestamp("end_time").toLocalDateTime();
					Projection projection = new Projection();
					projection.setStartTime(startDateTime);
					projection.setEndTime(endDateTime);
					projection.setMovieId(result.getLong("movie_id"));
					projection.setProjectionId(result.getLong("projection_id"));
					projections.add(projection);
				}
			}
		}
		return projections;
	}
	

	
	

}

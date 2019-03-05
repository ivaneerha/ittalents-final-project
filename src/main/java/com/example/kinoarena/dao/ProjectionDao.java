package com.example.kinoarena.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.NotAdminException;
import com.example.kinoarena.model.Movie;
import com.example.kinoarena.model.Projection;

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

}

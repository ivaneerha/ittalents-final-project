package com.example.kinoarena.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Movie;
import com.example.kinoarena.model.Projection;

public interface IProjectionDao {
	
public void deleteProjection(int broadcastId) throws Exception;
	
	public void changeProjectionTime(Long projectionId,LocalDateTime projectionTime) throws SQLException, InvalidInputDataException;

	void addProjection(Projection b) throws SQLException, InvalidInputDataException;

	public Collection<Projection> getAllProjectionsForAMovie(Movie m) throws SQLException, InvalidInputDataException;
	
	public Collection<Projection> getAllPRojections() throws SQLException, InvalidInputDataException;
	
	public Projection getProjectionById(Long id) throws SQLException, InvalidInputDataException;

	List<Long> getProjectionIdsByCinemaId(Long id) throws SQLException, InvalidInputDataException;

	List<Projection> getProjectionsByCinemaId(Long id) throws SQLException, InvalidInputDataException;
	
}

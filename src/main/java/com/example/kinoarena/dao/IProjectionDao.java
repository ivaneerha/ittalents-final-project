package com.example.kinoarena.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Movie;
import com.example.kinoarena.model.Projection;

public interface IProjectionDao {
	
public void deleteProjection(int broadcastId) throws Exception;
	
	public void changeProjectionTime(int projectionId,LocalDateTime projectionTime) throws SQLException, InvalidInputDataException;

	void addProjection(Projection b) throws SQLException, InvalidInputDataException;

	public Collection<Projection> getAllProjectionsForAMovie(Movie m) throws SQLException, InvalidInputDataException;
	
	public Collection<Projection> getAllPRojections() throws SQLException, InvalidInputDataException;
	
	public Projection getProjectionById(int id) throws SQLException, InvalidInputDataException;
	
}
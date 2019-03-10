package com.example.kinoarena.dao;

import java.sql.SQLException;
import java.util.List;
import com.example.kinoarena.dto.ProjectionWithMoviesDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.Projection;

public interface IProjectionDao {

	List<Long> getProjectionIdsByCinemaId(Long id) throws SQLException, InvalidInputDataException;

	List<Projection> getProjectionsByCinemaId(Long id) throws SQLException, InvalidInputDataException;

	void changeProjectionDateTime(Long projectionId, String startTime, String endTime) throws SQLException;

	List<ProjectionWithMoviesDto> getAllProjectionWithMovieTitles() throws SQLException;

	boolean checkIfProjectionExists(String startTime, Long movieId) throws SQLException;

	List<Projection> getAllProjectionsForAMovie(Long movieId) throws SQLException;

}

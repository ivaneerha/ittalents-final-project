package com.example.kinoarena.controllers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.ProjectionDao;
import com.example.kinoarena.dto.ProjectionDateTimeDto;
import com.example.kinoarena.dto.ProjectionDto;
import com.example.kinoarena.dto.ProjectionWithMoviesDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.exceptions.ProjectionNotFoundException;
import com.example.kinoarena.model.Projection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@RestController
public class ProjectionController extends BaseController {

	private static final String NO_PROJECTIONS_FOUND = "No projections found!";

	private static final String PROJECTION_ALREADY_EXISTS = "Projection already exists!";

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

	private static final String PROJECTION_NOT_FOUND = "There is no projection with this id!";

	@Autowired
	private ProjectionRepository projectionRepository;

	@Autowired
	private ProjectionDao projectionDao;

	/**
	 * Get all projections (with the movie ids)
	 */
	@GetMapping("/projections/movie_ids")
	public List<Projection> getAllProjections() {
		return projectionRepository.findAll();
	}

	/**
	 * Get all projections (with the movie titles)
	 */
	@GetMapping("/projections/movie_titles")
	public List<ProjectionWithMoviesDto> getAllProjectionsWithMovieTitles() throws KinoArenaException, SQLException {
		return projectionDao.getAllProjectionWithMovieTitles();
	}

	/**
	 * Get projection by Id
	 */
	@GetMapping("/projection/{id}")
	public Projection getProjectionById(@PathVariable Long id) throws KinoArenaException {
		Projection projection = projectionRepository.findByProjectionId(id);
		if (projection != null) {
			return projection;
		} else {
			throw new ProjectionNotFoundException(PROJECTION_NOT_FOUND);
		}
	}

	@PostMapping("/projection/add")
	public void addProjection(@RequestBody ProjectionDto projectionDto, HttpSession session, HttpServletRequest request)
			throws KinoArenaException {
		validateLoginAdmin(session);
		Projection projection = new Projection();
		try {
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			format.parse(projectionDto.getStartTime());
			format.parse(projectionDto.getEndTime());
			projection.setStartTime(projectionDto.getStartTime());
			projection.setEndTime(projectionDto.getEndTime());
			projection.setMovieId(projectionDto.getMovieId());

			if (projectionDao.checkIfProjectionExists(projectionDto.getStartTime(), projectionDto.getMovieId())) {
				throw new KinoArenaException(PROJECTION_ALREADY_EXISTS);
			}

			projectionRepository.save(projection);
		} catch (KinoArenaException e) {
			throw new KinoArenaException(e.getMessage());
		} catch (Exception e) {
			throw new InvalidInputDataException();
		}
	}


	/**
	 * Get projection ids a cinema
	 */
	@GetMapping("/projections/cinema/{id}")
	public List<Long> getProjectionIdsByCinemaId(@PathVariable Long id)
			throws SQLException, KinoArenaException {
		List<Long> projections = projectionDao.getProjectionIdsByCinemaId(id);
		if (!projections.isEmpty()) {
			return projections;
		} else {
			throw new KinoArenaException(NO_PROJECTIONS_FOUND);
		}
	}

	/**
	 * Get the projections for a cinema
	 */
	@GetMapping("/projections/all/cinema/{id}")
	public List<Projection> getProjectionsByCinemaId(@PathVariable Long id) throws KinoArenaException, SQLException {
		List<Projection> projections = projectionDao.getProjectionsByCinemaId(id);
		if (!projections.isEmpty()) {
			return projections;
		} else {
			throw new KinoArenaException("There is no projection for this cinema!");
		}
	}
	
	@GetMapping("/projections/movie/{id}")
	public List<Projection> getAllProjectionsByMovieId(@PathVariable Long id) throws SQLException, KinoArenaException {
		List<Projection> projections = projectionDao.getAllProjectionsForAMovie(id);
		if (!projections.isEmpty()) {
			return projections;
		} else {
			throw new KinoArenaException(NO_PROJECTIONS_FOUND);
		}
	}

	@DeleteMapping("/projections/delete/{id}")
	public void deleteProjection(@PathVariable Long id, HttpServletRequest request, HttpSession session)
			throws KinoArenaException {
		validateLoginAdmin(session);
		if (projectionRepository.existsById(id)) {
			projectionRepository.deleteById(id);
		} else {
			throw new ProjectionNotFoundException(PROJECTION_NOT_FOUND);
		}
	}

	@PutMapping("/projections/dateTimeChange")
	public void changeDateTime(@RequestBody ProjectionDateTimeDto dto, HttpSession session) throws KinoArenaException {
		validateLoginAdmin(session);
		try {
			if (!projectionRepository.existsById(dto.getProjectionId())) {
				throw new ProjectionNotFoundException(PROJECTION_NOT_FOUND);
			}
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			format.parse(dto.getStartTime());
			format.parse(dto.getEndTime());
			projectionDao.changeProjectionDateTime(dto.getProjectionId(), dto.getStartTime(), dto.getEndTime());

		} catch (Exception e) {
			throw new InvalidInputDataException();
		}
	}
}

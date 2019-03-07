package com.example.kinoarena.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.ProjectionDao;
import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.dto.ProjectionDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.exceptions.NotAdminException;
import com.example.kinoarena.exceptions.NotLoggedInException;
import com.example.kinoarena.exceptions.ProjectionNotFoundException;
import com.example.kinoarena.model.Projection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@RestController
public class ProjectionController extends BaseController{

	@Autowired
	private ProjectionRepository projectionRepository;
	
	@Autowired
	private ProjectionDao projectionDao;

	@GetMapping("/projections")
	public List<Projection> getAllProjections() {
		return projectionRepository.findAll();
	}

	@GetMapping("/projections/{id}")
	public Projection getProjectionById(@PathVariable long id) throws KinoArenaException {
		Projection projection = projectionRepository.findByProjectionId(id);
		if(projection != null) {
			return projection;
		} else {
			throw new ProjectionNotFoundException("Wrong projection id!");
		}
	}

	// TODO
	// CHECH JSON IN POSTMAN -> DATETIME 
	// validacii	
	@PostMapping("/projection/add")
	public void addProjection(@RequestBody ProjectionDto projectionDto,HttpSession session,HttpServletRequest request ) throws KinoArenaException{
		validateLoginAdmin(request);
		Projection projection = new Projection();
		try {
			//WHY DO YOU SET PROJECTION ID ?
			//projection.setProjectionId((long) 0);
			projection.setStartTime(projectionDto.getStartTime());
			projection.setEndTime(projectionDto.getEndTime());
			projection.setMovieId(projectionDto.getMovieId());
			projectionRepository.save(projection);
		} catch(Exception e) {
			throw new InvalidInputDataException();
		}
	}
	
	@GetMapping("/projections/cinema/{id}")
	public List<Long> getProjectionIdsByCinemaId(@PathVariable int id) throws ProjectionNotFoundException, InvalidInputDataException, SQLException {
		return projectionDao.getProjectionIdsByCinemaId(id);
	}
	
	@GetMapping("/projections/all/cinema/{id}")
	public List<Projection> getProjectionsByCinemaId(@PathVariable int id) throws KinoArenaException, SQLException {
		List<Projection> projections = projectionDao.getProjectionsByCinemaId(id);
		if(projections != null) {
			return projections;
		} else {
			throw new InvalidInputDataException();
		}
	}
	
	@DeleteMapping("/projections/delete/{id}")
	public void deleteProjection(@PathVariable Long id, HttpServletRequest request) throws KinoArenaException {
		validateLoginAdmin(request);
		projectionRepository.deleteById(id);
	}
	
	

}

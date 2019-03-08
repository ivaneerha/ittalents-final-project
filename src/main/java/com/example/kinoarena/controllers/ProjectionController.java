package com.example.kinoarena.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.example.kinoarena.dto.ProjectionWithMoviesDto;
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

	//Works!
	// Get all projections (with the movie ids)
	@GetMapping("/projections/movie_ids")
	public List<Projection> getAllProjections() {
		return projectionRepository.findAll();
	}

	//Works!
	//Get all projections (with the movie titles)
		@GetMapping("/projections/movie_titles")
		public List<ProjectionWithMoviesDto> getAllProjectionsWithMovieTitles() throws KinoArenaException, SQLException {
			return projectionDao.getAllProjectionWithMovieTitles();
		}
	
	//Works!
	//Get projection by Id
	@GetMapping("/projection/{id}")
	public Projection getProjectionById(@PathVariable Long id) throws KinoArenaException {
		Projection projection = projectionRepository.findByProjectionId(id);
		if(projection != null) {
			return projection;
		} else {
			throw new ProjectionNotFoundException("Projection with id = " + id + " not found!");
		}
	}

	
	// works!
	// TODO validations
	@PostMapping("/projection/add")
	public String addProjection(@RequestBody ProjectionDto projectionDto,HttpSession session,HttpServletRequest request ) throws KinoArenaException{
		validateLoginAdmin(request);
		Projection projection = new Projection();
		try {
			    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			    format.parse(projectionDto.getStartTime());
			    format.parse(projectionDto.getEndTime());
			projection.setStartTime(projectionDto.getStartTime());
			projection.setEndTime(projectionDto.getEndTime());
			projection.setMovieId(projectionDto.getMovieId());
			
			if(projectionDao.checkIfProjectionExists(projectionDto.getStartTime(), projectionDto.getMovieId())) {
				return "Projection already exists!";
			}
			
//			if(projectionRepository.findByMovieIdAndStartTime(projectionDto.getMovieId(), (projectionDto.getStartTime()))) {
//				return "Projection already exists!";
//			}
//			
			projectionRepository.save(projection);
			return "Projection was added successfully!";
			
//		} catch(KinoArenaException e) {
//			return e.getMessage();
		} catch(Exception e) {
			throw new InvalidInputDataException();
		}
	}
	
	
//	@PostMapping("/projection/find")
//	public Projection findProjection(@RequestBody ProjectionDto projectionDto,HttpSession session,HttpServletRequest request ) throws KinoArenaException, ParseException{
//		Projection projection = new Projection();
////		try {
//			    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//			    format.parse(projectionDto.getStartTime());
//			    format.parse(projectionDto.getEndTime());
//			projection.setStartTime(projectionDto.getStartTime());
//			projection.setEndTime(projectionDto.getEndTime());
//			projection.setMovieId(projectionDto.getMovieId());
//			return projectionRepository.findByMovieIdAndStartTime(projectionDto.getMovieId(), (projectionDto.getStartTime()));
////		} catch(Exception e) {
////			throw new InvalidInputDataException();
////		}
//	}
	
	
	@GetMapping("/projections/cinema/{id}")
	public List<Long> getProjectionIdsByCinemaId(@PathVariable Long id) throws ProjectionNotFoundException, InvalidInputDataException, SQLException {
		return projectionDao.getProjectionIdsByCinemaId(id);
	}
	
	@GetMapping("/projections/all/cinema/{id}")
	public List<Projection> getProjectionsByCinemaId(@PathVariable Long id) throws KinoArenaException, SQLException {
		List<Projection> projections = projectionDao.getProjectionsByCinemaId(id);
		if(!projections.isEmpty()) {
			return projections;
		} else {
			throw new KinoArenaException("There is no projection for this cinema!");
		}
	}
	
	//DOESN'T WORK!
	@DeleteMapping("/projections/delete/{id}")
	public void deleteProjection(@PathVariable Long id, HttpServletRequest request) throws KinoArenaException {
		validateLoginAdmin(request);
		projectionRepository.deleteById(id);
	}
	
	

}

package com.example.kinoarena.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.exceptions.NotAdminException;
import com.example.kinoarena.exceptions.NotLoggedInException;
import com.example.kinoarena.exceptions.ProjectionNotFoundException;
import com.example.kinoarena.model.Projection;

@RestController
public class ProjectionController extends BaseController{

	@Autowired
	private ProjectionRepository projectionRepository;

	@GetMapping("/projections")
	public List<Projection> getAllProjections() {
		return projectionRepository.findAll();
	}

	@GetMapping("/projections/{id}")
	public Projection getProjectionById(@PathVariable long id) throws ProjectionNotFoundException {
		Optional<Projection> pro = projectionRepository.findById(id);
		if (pro.isPresent()) {
			return pro.get();
		} else {
			throw new ProjectionNotFoundException();
		}
	}

	// TODO
	// CHECH JSON IN POSTMAN -> DATETIME
	@PostMapping("projection")
	public Projection addProjection(@RequestBody Projection projection,HttpSession session) throws KinoArenaException{
		validateLoginAdmin(session);
	return	projectionRepository.save(projection);
	}

}
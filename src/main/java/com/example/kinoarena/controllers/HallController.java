package com.example.kinoarena.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.HallDao;
import com.example.kinoarena.dto.AddHallDto;
import com.example.kinoarena.exceptions.CinemaNotFoundException;
import com.example.kinoarena.exceptions.HallNotFoundException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.exceptions.NotAdminException;
import com.example.kinoarena.model.Hall;

@RestController
public class HallController extends BaseController {

	@Autowired
	private CinemaRepository cinemaRepository;

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private HallDao hallDao;

	// Working!
	@PostMapping("/addhall")
	public void addHall(@RequestBody AddHallDto hall, HttpServletRequest request)
			throws KinoArenaException, SQLException {
		validateLoginAdmin(request);
		Hall h = new Hall();
		h.setCinemaId(hall.getCinemaId());
		h.setType(hall.getType());
		if(cinemaRepository.existsById(hall.getCinemaId())) {
		hallRepository.save(h);
		} else {
			throw new CinemaNotFoundException("No such cinema!");
		}
	}

	// Working!
	@DeleteMapping("/deletehall/{id}")
	public void deleteHall(@PathVariable Long id, HttpServletRequest request) throws KinoArenaException,SQLException{
		validateLoginAdmin(request);
		if (hallRepository.existsById(id)) {
			hallRepository.deleteById(id);
		} else {
			throw new HallNotFoundException("The hall does not Exists");
		}
	}

	// Working!
	@GetMapping("/halls/{id}")
	public List<Hall> getAll(@PathVariable Long id, HttpServletRequest request) throws KinoArenaException,SQLException {
		validateLoginAdmin(request);
		if (cinemaRepository.existsById(id)) {
			return hallRepository.findAllByCinemaId(id);
		} else {
			throw new CinemaNotFoundException("There is no such cinema!");
		}
	}
}
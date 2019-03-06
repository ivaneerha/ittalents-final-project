package com.example.kinoarena.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.CinemaDao;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Cinema;

@RestController
public class CinemaController extends BaseController {

	@Autowired
	private CinemaDao cinemaDao;

	@Autowired
	private CinemaRepository cinemaRepository;

	@GetMapping("/cinema/{id}")
	public void chooseCinema(@PathVariable("id") long id, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws KinoArenaException {
		validateLogin(session);
		Cinema cinema = cinemaRepository.findById(id);
		if (cinema != null) {
			session.setAttribute("cinema_id", id);
		} else {
			response.setStatus(404);
//			throw new InvalidInputDataException("Invalid cinema id!");
			throw new InvalidInputDataException();
		}
	}
	
	@GetMapping("/cinema")
	public Cinema getCinema(HttpSession session) {
		if(session.getAttribute("cinema_id") != null) {
			long id = (Long) session.getAttribute("cinema_id");
			Cinema cinema = cinemaRepository.findById(id);
			return cinema;
		} else {
			return null;
		}
	}
}

package com.example.kinoarena.controllers;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.CinemaDao;
import com.example.kinoarena.dto.AddCinemaDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.model.Cinema;

@RestController
public class CinemaController extends BaseController {

	@Autowired
	private CinemaDao cinemaDao;

	@Autowired
	private CinemaRepository cinemaRepository;

	//IVANA CODE
	@GetMapping("/cinema/{id}")
	public Cinema chooseCinema(@PathVariable("id") long id, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws KinoArenaException {
		validateLogin(session);
		Optional<Cinema> cin = cinemaRepository.findById(id);
		Cinema cinema = null;
		if(cin.isPresent()) {
			cinema = cin.get();
		}
//		Cinema cinema = cinemaRepository.findById(id).get();
		if (cinema != null) {
			session.setAttribute("cinema_id", id);
			return cinema;
		} else {
			response.setStatus(404);
//			throw new InvalidInputDataException("Invalid cinema id!");
			throw new InvalidInputDataException();
		}
	}
	
	//IVANA CODE
	@GetMapping("/cinema")
	public Cinema getCinema(HttpSession session) {
		if(session.getAttribute("cinema_id") != null) {
			long id = (Long) session.getAttribute("cinema_id");
			Cinema cinema = cinemaRepository.findById(id).get();
			return cinema;
		} else {
			return null;
		}
	}
	
	//NOT WORKING CHECH LOCATIONID
	@PostMapping("/addcinema")
	public void addCinema(@RequestBody AddCinemaDto cinema, HttpServletRequest request) throws KinoArenaException, SQLException {
	validateLoginAdmin(request);
	Cinema c = new Cinema();
	c.setContact(cinema.getContact());
	c.setLocationId(cinema.getLocatonId());
	c.setName(cinema.getName());
	cinemaRepository.save(c);
	}
	
	//NOT WORKING - Cannot delete or update a parent row: a foreign key constraint fails
	@DeleteMapping("/deletecinema/{id}")
	public void deleteCinema(@PathVariable Long id, HttpServletRequest request) throws KinoArenaException {
		validateLoginAdmin(request);
		cinemaRepository.deleteById(id);
	}
	
	//WORKING
	@GetMapping("/cinemas")
	public List<Cinema> getAll(HttpServletRequest request) throws KinoArenaException, SQLException {
		validateLoginAdmin(request);
		return cinemaRepository.findAll();
		}
	
	
	
}

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

import com.example.kinoarena.dto.AddCinemaDto;
import com.example.kinoarena.exceptions.CinemaNotFoundException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.helper.UserValidation;
import com.example.kinoarena.model.Cinema;
import com.example.kinoarena.model.Location;

@RestController
public class CinemaController extends BaseController {

	private static final String CINEMA_ID = "cinema_id";
	private static final String NO_CINEMA_SELECTED_YET = "There is no cinema selected!";
	private static final String CINEMA_NOT_FOUND = "There is no such cinema!";

	UserValidation validation = new UserValidation();

	@Autowired
	private CinemaRepository cinemaRepository;

	@Autowired
	private LocationRepository locationRepository;

	// IVANA CODE
	@GetMapping("/cinema/{id}")
	public Cinema chooseCinema(@PathVariable("id") Long id, HttpSession session, HttpServletResponse response,
			HttpServletRequest request) throws KinoArenaException {
		validateLogin(session);
		Optional<Cinema> cin = cinemaRepository.findById(id);
		Cinema cinema = null;
		if (cin.isPresent()) {
			cinema = cin.get();
		}
		if (cinema != null) {
			session.setAttribute(CINEMA_ID, id);
			return cinema;
		} else {
			response.setStatus(404);
			throw new KinoArenaException(CINEMA_NOT_FOUND);
		}
	}

	// IVANA CODE
	@GetMapping("/cinema/session")
	public Cinema getCinema(HttpSession session) throws KinoArenaException {
		validateLogin(session);
		if (session.getAttribute(CINEMA_ID) != null) {
			long id = (Long) session.getAttribute(CINEMA_ID);
			Cinema cinema = cinemaRepository.findById(id).get();
			return cinema;
		} else {
			throw new KinoArenaException(NO_CINEMA_SELECTED_YET);
		}
	}

	// !WORKING
	/**
	 * Method for adding new cinema to a new location
	 */
	@PostMapping("/addcinema")
	public void addCinema(@RequestBody AddCinemaDto cinema, HttpServletRequest request,HttpSession session)
			throws KinoArenaException, SQLException {
		validateLoginAdmin(session);

		Location location = new Location();
	
			validation.validateCityOrAddress(cinema.getCity());
			location.setCity(cinema.getCity());
			validation.validateCityOrAddress(cinema.getAddress());
			location.setAddress(cinema.getAddress());
			locationRepository.save(location);

			Cinema kino = new Cinema();
			kino.setLocationId(location.getLocationId());
			validation.validateGsm(cinema.getContact());
			kino.setContact(cinema.getContact());
			validation.validateString(cinema.getName());
			kino.setName(cinema.getName());
			cinemaRepository.save(kino);
	}

	// Working!
	@DeleteMapping("/deletecinema/{id}")
	public void deleteCinema(@PathVariable Long id, HttpServletRequest request,HttpSession session) throws KinoArenaException {
		validateLoginAdmin(session);
		if (cinemaRepository.existsById(id)) {
			cinemaRepository.deleteById(id);
		} else {
			throw new CinemaNotFoundException(CINEMA_NOT_FOUND);
		}
	}

	// WORKING
	@GetMapping("/cinemas")
	public List<Cinema> getAll(HttpSession session, HttpServletRequest request)
			throws KinoArenaException, SQLException {
		validateLogin(session);
		return cinemaRepository.findAll();
	}

}

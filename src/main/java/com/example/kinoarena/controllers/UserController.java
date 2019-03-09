package com.example.kinoarena.controllers;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dto.ChangePasswordDto;
import com.example.kinoarena.dto.FavouritesDto;
import com.example.kinoarena.dto.ProfileDto;

import com.example.kinoarena.exceptions.KinoArenaException;

import com.example.kinoarena.helper.UserValidation;
import com.example.kinoarena.model.Location;
import com.example.kinoarena.model.User;
import com.example.kinoarena.passwordcrypt.PasswordCrypt;

@RestController
public class UserController extends BaseController {

	private static final String NOT_LOGGED_IN_MSG = "You are not logged in!";
	private static final String PASSWORDS_DONT_MATCH_MSG = "Passwords do not match!";
	private static final String LOG_IN_TO_CHANGE_PASS_MSG = "Please log in to change your password!";
	
	UserValidation validation = new UserValidation();
	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	@GetMapping("/profile")
	public User getUserProfileById(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws SQLException, KinoArenaException {
		if (BaseController.isLogged(request)) {
			User user = (User) session.getAttribute(LOGGED);
			Long id = user.getUserId();
			return userRepository.findById(id).get();
		} else {
			throw new KinoArenaException(NOT_LOGGED_IN_MSG);
		}
	}


	@PutMapping("/favourites")
	public void addToFavourites(@RequestBody FavouritesDto fav, HttpSession session, HttpServletRequest request)
			throws SQLException, KinoArenaException {
		if (BaseController.isLogged(request)) {
			User user = (User) session.getAttribute(LOGGED);
			validation.validateString(fav.getFavouriteActor());
			user.setFavouriteActor(fav.getFavouriteActor());
			validation.validateString(fav.getFavouriteMovie());
			user.setFavouriteMovie(fav.getFavouriteMovie());
			userRepository.save(user);
		} else {
			throw new KinoArenaException(NOT_LOGGED_IN_MSG);
		}
	}

	@PutMapping("/profile")
	public void updateProfile(@RequestBody ProfileDto fav, HttpSession session, HttpServletRequest request)
		throws SQLException, KinoArenaException {
		if(BaseController.isLogged(request)) {
			User user = (User) session.getAttribute(LOGGED);
			
			Location location = new Location();
			validation.validateName(fav.getAddress());
			location.setAddress(fav.getAddress());
			validation.validateName(fav.getCity());
			location.setCity(fav.getCity());
			locationRepository.save(location);
			
			user.setLocationId(location.getLocationId());
			validation.validateGsm(fav.getGsm());
			user.setGsm(fav.getGsm());
			validation.validateName(fav.getName());
			user.setFirstName(fav.getName());
			validation.validateName(fav.getLastName());
			user.setLastName(fav.getName());
			userRepository.save(user);
			
		} else {
			throw new KinoArenaException(NOT_LOGGED_IN_MSG);
		}
	}
	
	
	
	@DeleteMapping("/delete")
	public void deleteAccount(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws SQLException, KinoArenaException {
		if (BaseController.isLogged(request)) {
			User user = (User) session.getAttribute(LOGGED);
			Long id = user.getUserId();
			userRepository.deleteById(id);
			session.invalidate();
		} else {
			throw new KinoArenaException(NOT_LOGGED_IN_MSG);
		}
	}

	
	@PutMapping("/changepassword")
	public void changePassword(@RequestBody ChangePasswordDto pass, HttpSession session, HttpServletRequest request)
			throws KinoArenaException, NoSuchAlgorithmException {
		if (BaseController.isLogged(request)) {
			validation.validatePassword(pass.getOldPass());
			validation.validatePassword(pass.getNewPass());
			User user = (User) session.getAttribute(LOGGED);
			String oldPass = user.getPassword();
			if (oldPass.equals(PasswordCrypt.cryptPassword(pass.getOldPass()))) {
				user.setPassword(PasswordCrypt.cryptPassword(pass.getNewPass()));
				userRepository.save(user);
			} else {
				throw new KinoArenaException(PASSWORDS_DONT_MATCH_MSG);
			}
		} else {
			throw new KinoArenaException(LOG_IN_TO_CHANGE_PASS_MSG);
		}
	}

	@PostMapping("/signout")
	public void logout(HttpServletRequest request) throws KinoArenaException {
		if (BaseController.isLogged(request)) {
			HttpSession session = request.getSession();
			session.invalidate();
		} else {
			throw new KinoArenaException(NOT_LOGGED_IN_MSG);
		}
	}
}

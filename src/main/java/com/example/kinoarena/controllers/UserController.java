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

import com.example.kinoarena.dto.ProfileDto;

import com.example.kinoarena.exceptions.KinoArenaException;

import com.example.kinoarena.helper.UserValidation;
import com.example.kinoarena.model.User;
import com.example.kinoarena.passwordcrypt.PasswordCrypt;


@RestController
public class UserController extends BaseController {


	UserValidation validation = new UserValidation();

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/profile")
	public User getUserProfileById(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws SQLException, KinoArenaException {
		if (BaseController.isLogged(request)) {
			User user = (User) session.getAttribute(LOGGED);
			Long id = user.getUserId();
			return userRepository.findById(id).get();
		} else {
			throw new KinoArenaException("You are not logged in!");
		}
	}

	// DO VALIDATION FOR GSM
	// Working!
	@PutMapping("/favourites")
	public void addToFavourites(@RequestBody ProfileDto fav, HttpSession session, HttpServletRequest request)
			throws SQLException, KinoArenaException {
		if (BaseController.isLogged(request)) {
			User user = (User) session.getAttribute(LOGGED);
			validation.validateGsm(fav.getGsm());
			user.setGsm(fav.getGsm());
			validation.validateString(fav.getFavouriteActor());
			user.setFavouriteActor(fav.getFavouriteActor());
			validation.validateString(fav.getFavouriteMovie());
			user.setFavouriteMovie(fav.getFavouriteMovie());
			userRepository.save(user);
		} else {
			throw new KinoArenaException("You are not logged in!");
		}
	}

//		DONT DELETE
//	@PutMapping("/money{id}")
//	public void insertMoney(HttpSession session, HttpServletRequest request) throws SQLException, KinoArenaException {
//		if(SessionManager.isLogged(request)) {
//			User user = (User) session.getAttribute(LOGGED);
//			Long id = user.get
//		}
//	}

	@DeleteMapping("/delete")
	public void deleteAccount(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws SQLException, KinoArenaException {
		if (BaseController.isLogged(request)) {
			User user = (User) session.getAttribute(LOGGED);
			Long id = user.getUserId();
			userRepository.deleteById(id);
			session.invalidate();
		} else {
			throw new KinoArenaException("You are not logged in!");
		}
	}

	// NOT WORKING!!!
	@PutMapping("/changepassword")
	public void changePassword(@RequestBody ChangePasswordDto pass, HttpSession session, HttpServletRequest request)
			throws KinoArenaException, NoSuchAlgorithmException {
		validateLogin(session);
		validation.validatePassword(pass.getOldPass());
		validation.validatePassword(pass.getNewPass());
		User user = (User) session.getAttribute(LOGGED);
		String oldPass = user.getPassword();
		if (oldPass.equals(PasswordCrypt.cryptPassword(pass.getOldPass()))) {
			user.setPassword(PasswordCrypt.cryptPassword(pass.getNewPass()));
			userRepository.save(user);
		} else {
			throw new KinoArenaException("Passwords do not match!");
		}
	}

	@PostMapping("/signout")
	public void logout(HttpServletRequest request) throws KinoArenaException {
		if (BaseController.isLogged(request)) {
			HttpSession session = request.getSession();
			session.invalidate();
		} else {
			throw new KinoArenaException("You are already logged out!");
		}
	}
}

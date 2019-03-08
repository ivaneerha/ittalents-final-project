package com.example.kinoarena.controllers;

import java.security.CryptoPrimitive;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.dto.ChangePasswordDto;
import com.example.kinoarena.dto.LoginDto;
import com.example.kinoarena.dto.ProfileDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.exceptions.NotLoggedInException;
import com.example.kinoarena.helper.UserValidation;
import com.example.kinoarena.model.User;
import com.example.kinoarena.passwordcrypt.PasswordCrypt;
import com.example.kinoarena.service.SessionManager;

@RestController
public class UserController extends BaseController {

	private static final int SESSION_TIMEOUT = 10000;
	UserValidation validation = new UserValidation();

	@Autowired
	private UserDao userDao;

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

			user.setGsm(fav.getGsm());
			user.setFavouriteActor(fav.getFavouriteActor());
			user.setFavouriteMovie(fav.getFavouriteMovie());
			user.setLocationId(fav.getLocationId());
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
			System.out.println("You have deleted your account!");
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

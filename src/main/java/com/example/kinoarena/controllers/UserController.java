package com.example.kinoarena.controllers;

import java.sql.SQLException;

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

import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.dto.LoginDto;
import com.example.kinoarena.dto.ProfileDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.exceptions.KinoArenaException;
import com.example.kinoarena.exceptions.NotLoggedInException;
import com.example.kinoarena.model.User;
import com.example.kinoarena.service.SessionManager;

@RestController
public class UserController extends BaseController{
	
	private static final int SESSION_TIMEOUT = 10000;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRepository userRepository;
	
//DONT DELETE -> MOVED TO lOGINCONTROLLER
//		User u = userDao.login(user);
//		HttpSession session = request.getSession();
//		session.setMaxInactiveInterval(SESSION_TIMEOUT);
//		session.setAttribute("userId", u.getUser_id());
	
	
//	@GetMapping("/profile/username")
//	public User getUserProfile(@RequestBody ProfileDto user, HttpServletRequest request, HttpServletResponse response,HttpSession session) throws SQLException, KinoArenaException {
//		validateLogin(session);
//		if(SessionManager.isLogged(request))	{
//		User u = userDao.getUserByUsername(user.getUsername());
//			return u;
//		} else {
//			response.setStatus(418);
//			return null;
//		}
//	}
	
	@GetMapping("/profile")
	public User getUserProfileById(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws SQLException, KinoArenaException {
		if(SessionManager.isLogged(request)) {
			User user = (User) session.getAttribute(LOGGED);
			Long id = user.getUserId();
			return userRepository.findById(id).get();
		}
		throw new KinoArenaException("You are not logged in!");
	}
	

	@DeleteMapping("/delete")
	public void deleteAccount(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws SQLException, KinoArenaException {
		if(SessionManager.isLogged(request)) {
			User user = (User) session.getAttribute(LOGGED);
			Long id = user.getUserId();
			userRepository.deleteById(id);
			System.out.println("You have deleted your account!");
		} else {
			throw new NotLoggedInException();
		}
	}

	//DONT DELETE THIS
//	@GetMapping("/profile")
//	public User getUserProfile(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvalidInputDataException {
//		SessionManager.isLogged(request);
//		HttpSession session = request.getSession();
//		if (session.getAttribute("userId") == null) {
//			response.setStatus(401);
//			return null;
//		}
//		long id = (Long) session.getAttribute("userId");
//		return userDao.getUserById(id);
//	}
	
	@PostMapping("/signout")
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	
	
}

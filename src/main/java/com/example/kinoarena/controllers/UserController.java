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
import com.example.kinoarena.model.User;
import com.example.kinoarena.service.SessionManager;

@RestController
public class UserController {
	
	private static final int SESSION_TIMEOUT = 10000;
	
	@Autowired
	private UserDao userDao;
	
	@PostMapping("/login")
	public void login(@RequestBody LoginDto user, HttpServletRequest request, HttpServletResponse response) throws SQLException, InvalidInputDataException {
		userDao.login(user);
		SessionManager.logUser(request, user);
		
//		User u = userDao.login(user);
//		HttpSession session = request.getSession();
//		session.setMaxInactiveInterval(SESSION_TIMEOUT);
//		session.setAttribute("userId", u.getUser_id());
	}
	
	@GetMapping("/profile/username")
	public User getUserProfile(@RequestBody ProfileDto user, HttpServletRequest request, HttpServletResponse response) throws SQLException, InvalidInputDataException {
		if(SessionManager.isLogged(request))	{
		User u = userDao.getUserByUsername(user.getUsername());
			return u;
		} else {
			response.setStatus(418);
			return null;
		}
	}
	
	//check
	@GetMapping("/profile/id")
	public User getUserProfileById(@RequestBody ProfileDto user, HttpServletRequest request, HttpServletResponse response) throws SQLException, InvalidInputDataException {
		if(SessionManager.isLogged(request)) {	
		User u = userDao.getUserById(user.getUser_id());
			return u;
		} else {
			response.setStatus(418);
			System.out.println("I am a teapot!");
			return null;
		}
	}
	

	@DeleteMapping("/delete")
	public void deleteAccount(@RequestBody ProfileDto user, HttpServletRequest request, HttpServletResponse response) throws SQLException, InvalidInputDataException {
		if(SessionManager.isLogged(request)) {	
		userDao.deleteUserByID(user.getUser_id());
		} else {
			response.setStatus(418);
			System.out.println("I am a teapot!");
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
//		return userDao.getUser(id);
//	}
	
	@PostMapping("/signout")
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	
	
}

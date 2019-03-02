package com.example.kinoarena.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kinoarena.dao.UserDao;
import com.example.kinoarena.dto.LoginDto;
import com.example.kinoarena.exceptions.InvalidInputDataException;
import com.example.kinoarena.model.User;

@RestController
public class UserController {
	
	private static final int SESSION_TIMEOUT = 10000;
	
	@Autowired
	private UserDao userDao;
	
	@PostMapping("/login")
	public void login(@RequestBody LoginDto user, HttpServletRequest request, HttpServletResponse response) throws SQLException, InvalidInputDataException {
		User u = userDao.login(user);
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(SESSION_TIMEOUT);
		session.setAttribute("userId", u.getUser_id());
	}
	
	@GetMapping("/profile")
	public User getUserProfile(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvalidInputDataException {
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") == null) {
			response.setStatus(401);
			return null;
		}
		long id = (Long) session.getAttribute("userId");
		return userDao.getUser(id);
	}
	
	@PostMapping("/signout")
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
}
